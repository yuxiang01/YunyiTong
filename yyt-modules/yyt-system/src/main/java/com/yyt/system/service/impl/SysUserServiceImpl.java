package com.yyt.system.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yyt.common.core.constant.UserConstants;
import com.yyt.common.core.exception.ServiceException;
import com.yyt.common.core.utils.SpringUtils;
import com.yyt.common.core.utils.StringUtils;
import com.yyt.common.core.utils.bean.BeanValidators;
import com.yyt.common.datascope.annotation.DataScope;
import com.yyt.common.security.utils.SecurityUtils;
import com.yyt.system.api.domain.SysRole;
import com.yyt.system.api.domain.SysUser;
import com.yyt.system.api.model.RegisterWxUser;
import com.yyt.system.domain.SysPost;
import com.yyt.system.domain.SysUserPost;
import com.yyt.system.domain.SysUserRole;
import com.yyt.system.mapper.*;
import com.yyt.system.service.ISysConfigService;
import com.yyt.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
  private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

  @Autowired
  private SysUserMapper userMapper;

  @Autowired
  private SysRoleMapper roleMapper;

  @Autowired
  private SysPostMapper postMapper;

  @Autowired
  private SysUserRoleMapper userRoleMapper;

  @Autowired
  private SysUserPostMapper userPostMapper;

  @Autowired
  private ISysConfigService configService;

  @Autowired
  protected Validator validator;

  @Value("${wx.app-id}")
  private String appId;

  @Value("${wx.app-secret}")
  private String appSecret;

  @Resource
  private StringRedisTemplate stringRedisTemplate;

  /**
   * 根据条件分页查询用户列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  @Override
  @DataScope(deptAlias = "d", userAlias = "u")
  public List<SysUser> selectUserList(SysUser user) {
    return userMapper.selectUserList(user);
  }

  /**
   * 根据条件分页查询已分配用户角色列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  @Override
  @DataScope(deptAlias = "d", userAlias = "u")
  public List<SysUser> selectAllocatedList(SysUser user) {
    return userMapper.selectAllocatedList(user);
  }

  /**
   * 根据条件分页查询未分配用户角色列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  @Override
  @DataScope(deptAlias = "d", userAlias = "u")
  public List<SysUser> selectUnallocatedList(SysUser user) {
    return userMapper.selectUnallocatedList(user);
  }

  /**
   * 通过用户名查询用户
   *
   * @param userName 用户名
   * @return 用户对象信息
   */
  @Override
  public SysUser selectUserByUserName(String userName) {
    return userMapper.selectUserByUserName(userName);
  }

  /**
   * 通过用户ID查询用户
   *
   * @param userId 用户ID
   * @return 用户对象信息
   */
  @Override
  public SysUser selectUserById(Long userId) {
    return userMapper.selectUserById(userId);
  }

  /**
   * 查询用户所属角色组
   *
   * @param userName 用户名
   * @return 结果
   */
  @Override
  public String selectUserRoleGroup(String userName) {
    List<SysRole> list = roleMapper.selectRolesByUserName(userName);
    if (CollectionUtils.isEmpty(list)) {
      return StringUtils.EMPTY;
    }
    return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
  }

  /**
   * 查询用户所属岗位组
   *
   * @param userName 用户名
   * @return 结果
   */
  @Override
  public String selectUserPostGroup(String userName) {
    List<SysPost> list = postMapper.selectPostsByUserName(userName);
    if (CollectionUtils.isEmpty(list)) {
      return StringUtils.EMPTY;
    }
    return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
  }

  /**
   * 校验用户名称是否唯一
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public boolean checkUserNameUnique(SysUser user) {
    Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
    SysUser info = userMapper.checkUserNameUnique(user.getUserName());
    if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  /**
   * 校验手机号码是否唯一
   *
   * @param user 用户信息
   * @return
   */
  @Override
  public boolean checkPhoneUnique(SysUser user) {
    Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
    SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
    if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  /**
   * 校验email是否唯一
   *
   * @param user 用户信息
   * @return
   */
  @Override
  public boolean checkEmailUnique(SysUser user) {
    Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
    SysUser info = userMapper.checkEmailUnique(user.getEmail());
    if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  /**
   * 校验用户是否允许操作
   *
   * @param user 用户信息
   */
  @Override
  public void checkUserAllowed(SysUser user) {
    if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
      throw new ServiceException("不允许操作超级管理员用户");
    }
  }

  /**
   * 校验用户是否有数据权限
   *
   * @param userId 用户id
   */
  @Override
  public void checkUserDataScope(Long userId) {
    if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
      SysUser user = new SysUser();
      user.setUserId(userId);
      List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
      if (StringUtils.isEmpty(users)) {
        throw new ServiceException("没有权限访问用户数据！");
      }
    }
  }

  /**
   * 新增保存用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int insertUser(SysUser user) {
    // 新增用户信息
    int rows = userMapper.insertUser(user);
    // 新增用户岗位关联
    insertUserPost(user);
    // 新增用户与角色管理
    insertUserRole(user);
    return rows;
  }

  /**
   * 注册用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public boolean registerUser(SysUser user) {
    return userMapper.insertUser(user) > 0;
  }

  /**
   * 修改保存用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateUser(SysUser user) {
    Long userId = user.getUserId();
    // 删除用户与角色关联
    userRoleMapper.deleteUserRoleByUserId(userId);
    // 新增用户与角色管理
    insertUserRole(user);
    // 删除用户与岗位关联
    userPostMapper.deleteUserPostByUserId(userId);
    // 新增用户与岗位管理
    insertUserPost(user);
    return userMapper.updateUser(user);
  }

  /**
   * 用户授权角色
   *
   * @param userId  用户ID
   * @param roleIds 角色组
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void insertUserAuth(Long userId, Long[] roleIds) {
    userRoleMapper.deleteUserRoleByUserId(userId);
    insertUserRole(userId, roleIds);
  }

  /**
   * 修改用户状态
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public int updateUserStatus(SysUser user) {
    return userMapper.updateUser(user);
  }

  /**
   * 修改用户基本信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public int updateUserProfile(SysUser user) {
    return userMapper.updateUser(user);
  }

  /**
   * 修改用户头像
   *
   * @param userName 用户名
   * @param avatar   头像地址
   * @return 结果
   */
  @Override
  public boolean updateUserAvatar(String userName, String avatar) {
    return userMapper.updateUserAvatar(userName, avatar) > 0;
  }

  /**
   * 重置用户密码
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public int resetPwd(SysUser user) {
    return userMapper.updateUser(user);
  }

  /**
   * 重置用户密码
   *
   * @param userName 用户名
   * @param password 密码
   * @return 结果
   */
  @Override
  public int resetUserPwd(String userName, String password) {
    return userMapper.resetUserPwd(userName, password);
  }

  /**
   * 新增用户角色信息
   *
   * @param user 用户对象
   */
  public void insertUserRole(SysUser user) {
    this.insertUserRole(user.getUserId(), user.getRoleIds());
  }

  /**
   * 新增用户岗位信息
   *
   * @param user 用户对象
   */
  public void insertUserPost(SysUser user) {
    Long[] posts = user.getPostIds();
    if (StringUtils.isNotEmpty(posts)) {
      // 新增用户与岗位管理
      List<SysUserPost> list = new ArrayList<SysUserPost>();
      for (Long postId : posts) {
        SysUserPost up = new SysUserPost();
        up.setUserId(user.getUserId());
        up.setPostId(postId);
        list.add(up);
      }
      userPostMapper.batchUserPost(list);
    }
  }

  /**
   * 新增用户角色信息
   *
   * @param userId  用户ID
   * @param roleIds 角色组
   */
  public void insertUserRole(Long userId, Long[] roleIds) {
    if (StringUtils.isNotEmpty(roleIds)) {
      // 新增用户与角色管理
      List<SysUserRole> list = new ArrayList<SysUserRole>();
      for (Long roleId : roleIds) {
        SysUserRole ur = new SysUserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        list.add(ur);
      }
      userRoleMapper.batchUserRole(list);
    }
  }

  /**
   * 通过用户ID删除用户
   *
   * @param userId 用户ID
   * @return 结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteUserById(Long userId) {
    // 删除用户与角色关联
    userRoleMapper.deleteUserRoleByUserId(userId);
    // 删除用户与岗位表
    userPostMapper.deleteUserPostByUserId(userId);
    return userMapper.deleteUserById(userId);
  }

  /**
   * 批量删除用户信息
   *
   * @param userIds 需要删除的用户ID
   * @return 结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteUserByIds(Long[] userIds) {
    for (Long userId : userIds) {
      checkUserAllowed(new SysUser(userId));
      checkUserDataScope(userId);
    }
    // 删除用户与角色关联
    userRoleMapper.deleteUserRole(userIds);
    // 删除用户与岗位关联
    userPostMapper.deleteUserPost(userIds);
    return userMapper.deleteUserByIds(userIds);
  }

  /**
   * 导入用户数据
   *
   * @param userList        用户数据列表
   * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
   * @param operName        操作用户
   * @return 结果
   */
  @Override
  public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
    if (StringUtils.isNull(userList) || userList.size() == 0) {
      throw new ServiceException("导入用户数据不能为空！");
    }
    int successNum = 0;
    int failureNum = 0;
    StringBuilder successMsg = new StringBuilder();
    StringBuilder failureMsg = new StringBuilder();
    String password = configService.selectConfigByKey("sys.user.initPassword");
    for (SysUser user : userList) {
      try {
        // 验证是否存在这个用户
        SysUser u = userMapper.selectUserByUserName(user.getUserName());
        if (StringUtils.isNull(u)) {
          BeanValidators.validateWithException(validator, user);
          user.setPassword(SecurityUtils.encryptPassword(password));
          user.setCreateBy(operName);
          userMapper.insertUser(user);
          successNum++;
          successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
        } else if (isUpdateSupport) {
          BeanValidators.validateWithException(validator, user);
          checkUserAllowed(u);
          checkUserDataScope(u.getUserId());
          user.setUserId(u.getUserId());
          user.setUpdateBy(operName);
          userMapper.updateUser(user);
          successNum++;
          successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
        } else {
          failureNum++;
          failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
        }
      } catch (Exception e) {
        failureNum++;
        String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
        failureMsg.append(msg + e.getMessage());
        log.error(msg, e);
      }
    }
    if (failureNum > 0) {
      failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
      throw new ServiceException(failureMsg.toString());
    } else {
      successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
    }
    return successMsg.toString();
  }

  @Override
  public SysUser selectUserByOpenId(String code) {
    return userMapper.selectUserByOpenId(getOpenId(code));
  }

  // 拿到openid
  private String getOpenId(String code) {
    String url = "https://api.weixin.qq.com/sns/jscode2session";
    HashMap<String, Object> map = new HashMap<>();
    map.put("appid", appId);
    map.put("secret", appSecret);
    map.put("js_code", code);
    map.put("grant_type", "authorization_code");
    String response = HttpUtil.post(url, map);
    JSONObject json = JSONUtil.parseObj(response);
    String openId = json.getStr("openid");
    if (openId == null || openId.length() == 0) {
      throw new RuntimeException("临时登录凭证错误");
    }
    return openId;
  }

  // 注册
  @Override
  public SysUser register(RegisterWxUser user) {
    String openId = getOpenId(user.getCode());
    // 手机号不存在，允许注册
    if (!userMapper.checkPhone(user.getPhone())) {
      // 判断验证码是否正确
      String cacheCode = stringRedisTemplate.opsForValue().get("user:phone:");
      if (user.getYzm().equals(cacheCode)) {
        String sj = "微信用户" + user.getPhone().substring(user.getPhone().length() - 4);
        SysUser user1 = new SysUser();
        user1.setUserName(sj);
        user1.setOpenId(openId);
        user1.setNickName(sj);
        user1.setPhonenumber(user.getPhone());
        // 构建用户
        return userMapper.insertUser(user1) > 0 ? user1 : null;
      }
    }
    return null;
  }

  @Override
  public String sendCode(String phone) {
    if (!userMapper.checkPhone(phone)) {
      try {
//        String sms = WebUtils.sms(phone);
        String sms = RandomUtil.randomNumbers(4);
        stringRedisTemplate.opsForValue()
            .set("user:phone:", sms, 4, TimeUnit.MINUTES);
        return sms;
      } catch (Exception e) {
        System.out.println(e.getMessage() + ", " + e.getCause());
        return "验证码发送失败";
      }
    }
    return "该用户已存在";
  }
}



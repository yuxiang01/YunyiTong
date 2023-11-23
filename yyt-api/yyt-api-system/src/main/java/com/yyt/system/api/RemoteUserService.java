package com.yyt.system.api;

import com.yyt.system.api.model.RegisterWxUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import com.yyt.common.core.constant.SecurityConstants;
import com.yyt.common.core.constant.ServiceNameConstants;
import com.yyt.common.core.domain.R;
import com.yyt.system.api.domain.SysUser;
import com.yyt.system.api.factory.RemoteUserFallbackFactory;
import com.yyt.system.api.model.LoginUser;

/**
 * 用户服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {
  /**
   * 通过用户名查询用户信息
   *
   * @param username 用户名
   * @param source   请求来源
   * @return 结果
   */
  @GetMapping("/user/info/{username}")
  public R<LoginUser> getUserInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

  /**
   * 注册用户信息
   *
   * @param sysUser 用户信息
   * @param source  请求来源
   * @return 结果
   */
  @PostMapping("/user/register")
  public R<Boolean> registerUserInfo(@RequestBody SysUser sysUser, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

  /**
   * 查询是否存在该用户
   *
   * @param code   临时授权码
   * @param source 请求来源
   * @return user
   */
  @PostMapping("/user/code/{code}")
  R<LoginUser> selectUserByOpenId(@PathVariable("code") String code, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

  /**
   * 微信用户注册
   *
   * @param wxUser 微信用户注册form
   * @param source 来源
   * @return true/false
   */
  @PostMapping("/user/wx/register")
  R<LoginUser> wxRegister(RegisterWxUser wxUser, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}

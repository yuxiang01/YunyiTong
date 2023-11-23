package com.yyt.auth.controller;

import javax.servlet.http.HttpServletRequest;

import com.yyt.system.api.model.RegisterWxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.yyt.auth.form.LoginBody;
import com.yyt.auth.form.RegisterBody;
import com.yyt.auth.service.SysLoginService;
import com.yyt.common.core.domain.R;
import com.yyt.common.core.utils.JwtUtils;
import com.yyt.common.core.utils.StringUtils;
import com.yyt.common.security.auth.AuthUtil;
import com.yyt.common.security.service.TokenService;
import com.yyt.common.security.utils.SecurityUtils;
import com.yyt.system.api.model.LoginUser;

/**
 * token 控制
 *
 * @author ruoyi
 */
@RestController
public class TokenController {
  @Autowired
  private TokenService tokenService;

  @Autowired
  private SysLoginService sysLoginService;

  @PostMapping("login")
  public R<?> login(@RequestBody LoginBody form) {
    // 用户登录
    LoginUser userInfo = sysLoginService.login(form.getUsername(), form.getPassword());
    // 获取登录token
    return R.ok(tokenService.createToken(userInfo));
  }

  @DeleteMapping("logout")
  public R<?> logout(HttpServletRequest request) {
    String token = SecurityUtils.getToken(request);
    if (StringUtils.isNotEmpty(token)) {
      String username = JwtUtils.getUserName(token);
      // 删除用户缓存记录
      AuthUtil.logoutByToken(token);
      // 记录用户退出日志
      sysLoginService.logout(username);
    }
    return R.ok();
  }

  @PostMapping("refresh")
  public R<?> refresh(HttpServletRequest request) {
    LoginUser loginUser = tokenService.getLoginUser(request);
    if (StringUtils.isNotNull(loginUser)) {
      // 刷新令牌有效期
      tokenService.refreshToken(loginUser);
      return R.ok();
    }
    return R.ok();
  }

  @PostMapping("register")
  public R<?> register(@RequestBody RegisterBody registerBody) {
    // 用户注册
    sysLoginService.register(registerBody.getUsername(), registerBody.getPassword());
    return R.ok();
  }

  @PostMapping("/wx/login")
  public R<?> wxLogin(@RequestBody LoginBody form) {
    if (!form.getCode().isEmpty()) {
      // 先判断openId存不存在
      LoginUser userInfo = sysLoginService.wxLogin(form.getCode());
      // 获取登录token
      return R.ok(tokenService.createToken(userInfo));
    }
    return R.fail("登录失败");
  }

  @PostMapping("/wx/register")
  public R<?> wxRegister(@RequestBody RegisterWxUser wxUser) {
    System.out.println("wxUser = " + wxUser);
    // 用户注册
    LoginUser loginUser = sysLoginService.wxRegister(wxUser);
    return R.ok(tokenService.createToken(loginUser));
  }
}

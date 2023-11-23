package com.yyt.system.api.factory;

import com.yyt.system.api.model.RegisterWxUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import com.yyt.common.core.domain.R;
import com.yyt.system.api.RemoteUserService;
import com.yyt.system.api.domain.SysUser;
import com.yyt.system.api.model.LoginUser;

/**
 * 用户服务降级处理
 *
 * @author ruoyi
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {
  private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

  @Override
  public RemoteUserService create(Throwable throwable) {
    log.error("用户服务调用失败:{}", throwable.getMessage());
    return new RemoteUserService() {
      @Override
      public R<LoginUser> getUserInfo(String username, String source) {
        return R.fail("获取用户失败:" + throwable.getMessage());
      }

      @Override
      public R<Boolean> registerUserInfo(SysUser sysUser, String source) {
        return R.fail("注册用户失败:" + throwable.getMessage());
      }

      @Override
      public R<LoginUser> selectUserByOpenId(String code, String source) {
        return R.fail("该用户不存在: " + throwable.getMessage());
      }

      @Override
      public R<LoginUser> wxRegister(RegisterWxUser wxUser, String source) {
        return R.fail("注册用户失败:" + throwable.getMessage());
      }
    };
  }
}

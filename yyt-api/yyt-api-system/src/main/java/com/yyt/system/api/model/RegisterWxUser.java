package com.yyt.system.api.model;

/**
 * @author 26908
 * @version 1.0
 * @date 2023/11/23 9:54
 */
public class RegisterWxUser {
  private String phone;
  private String code;
  private String yzm;

  public RegisterWxUser() {
  }

  public RegisterWxUser(String phone, String code, String yzm) {
    this.phone = phone;
    this.code = code;
    this.yzm = yzm;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getYzm() {
    return yzm;
  }

  public void setYzm(String yzm) {
    this.yzm = yzm;
  }
}

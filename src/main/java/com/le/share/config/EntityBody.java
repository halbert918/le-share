package com.le.share.config;

/**
 * Created by yinbohe.
 * Date 2019/8/20
 * Description.
 */
public class EntityBody<T> {

  /**
   * code.
   */
  private String code;
  /**
   * 返回信息.
   */
  private String message;
  /**
   * 返回数据.
   */
  private T data;

  public EntityBody(String code, String message) {
    this(code, message, null);
  }

  /**
   * .
   * @param code code
   * @param message message
   * @param data data
   */
  public EntityBody(String code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}

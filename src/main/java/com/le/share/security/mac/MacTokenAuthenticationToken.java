package com.le.share.security.mac;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.security.auth.Subject;

/**
 * Created by yinbohe.
 * Date 2020/5/4
 * Description
 */
public class MacTokenAuthenticationToken extends AbstractAuthenticationToken {

  /**
   * 访问令牌token
   */
  private String id;
  /**
   * 时间戳
   */
  private String timestamp;
  /**
   * 随机数.
   */
  private String nonce;
  /**
   * 请求参数签名字符串.
   */
  private String sign;

  public MacTokenAuthenticationToken(String id, String timestamp, String nonce, String sign) {
    super(null);
    this.id = id;
    this.timestamp = timestamp;
    this.nonce = nonce;
    this.sign = sign;
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }

  @Override
  public boolean implies(Subject subject) {
    if (subject == null)
      return false;
    return subject.getPrincipals().contains(this);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getNonce() {
    return nonce;
  }

  public void setNonce(String nonce) {
    this.nonce = nonce;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }
}

package com.le.share.security.mac;

import com.google.common.base.Splitter;
import com.le.share.security.exception.AuthenticationSecurityException;
import com.le.share.security.filter.AuthenticationFilter;
import com.le.share.security.support.AuthenticationExtractor;
import com.le.share.util.Md5Util;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by yinbohe.
 * Date 2020/5/4
 * Description Authorization: MAC id="h480djs93hd8" ts="1336363200" nonce="dj83hs9s" sign="bhCQXTVyfj5cmA9uKkPFx1zeOXM="
 */
@Component
public class MacTokenAuthenticationExtractor implements AuthenticationExtractor {

  private Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

  private String AUTHENTICATION_PREFIX = "Mac ";

  @Value("auth.secret-key")
  private String secretKey;

  @Override
  public Authentication extractAuthentication(String authentication) throws AuthenticationException {
    logger.debug("Parse Authentication mac header:{}", authentication);

    String mac = authentication.substring(AUTHENTICATION_PREFIX.length());

    Map<String, String> map = splitMacToMap(mac);
    // 签名校验
    String sign = map.get("sign");

    String curSign = Md5Util.generateMD5String(secretKey + mac);

    if (curSign.equals(sign)) {
      throw new AuthenticationSecurityException("verify sign failed.");
    }
    // 校验时间戳是否在合法范围内
    String timestamp = map.get("timestamp");

    // 校验随机数是否被使用过
    String nonce = map.get("nonce");

    String openid = map.get("openid");

    return new MacTokenAuthenticationToken(openid, timestamp, nonce, sign);
  }

  @Override
  public boolean supports(String authentication) {
    return authentication.startsWith(AUTHENTICATION_PREFIX);
  }

  /**
   * 解析mac参数.
   *
   * @param mac Authorization中的Mac值
   * @return
   */
  private Map<String, String> splitMacToMap(String mac) {
    if (mac == null) {
      return null;
    }
    mac = mac.replace("\"", "");
    return new CaseInsensitiveMap<>(Splitter.on(" ").trimResults().withKeyValueSeparator(Splitter.on("=")).split(mac));
  }

  /**
   * .
   *
   * @param name key
   * @param map  map
   * @return value
   */
  private String getValue(String name, Map<String, String> map) {
    if (map == null) {
      return null;
    }
    return map.get(name);
  }

}

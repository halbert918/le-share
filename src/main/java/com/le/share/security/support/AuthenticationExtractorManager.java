package com.le.share.security.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/5/4
 * Description
 */
public class AuthenticationExtractorManager {

  private List<AuthenticationExtractor> extractors = new ArrayList<>();

  @Autowired
  public void addExtractors(List<AuthenticationExtractor> extractors) {
    this.extractors.addAll(extractors);
  }

  public Authentication extractAuthentication(String authentication) throws AuthenticationException {
    for (AuthenticationExtractor extractor : extractors) {
      if (extractor.supports(authentication)) {
        return extractor.extractAuthentication(authentication);
      }
    }
    return null;
  }

}

package com.le.share.exception;

import com.le.share.common.enums.LeResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yinbohe.
 * Date 2019/8/20
 * Description exp异常.
 */
public class LeShareException extends RuntimeException {

  private Logger logger = LoggerFactory.getLogger(LeShareException.class);

  private LeResultEnum resultEnum = LeResultEnum.UN_KNOWN_EXCEPTION;

  public LeShareException(String message) {
    super(message);
  }

  public LeShareException(Throwable cause) {
    super(cause);
  }

  public LeShareException(String message, Throwable cause) {
    super(message, cause);
  }

  public LeShareException(LeResultEnum resultEnum, Throwable cause) {
    super(cause);
    this.resultEnum = resultEnum;
  }

  public LeShareException(LeResultEnum resultEnum) {
    super(resultEnum.getMessage());
    this.resultEnum = resultEnum;
  }

  public LeShareException(LeResultEnum resultEnum, String message) {
    super(null == message ? resultEnum.getMessage() : message);
    this.resultEnum = resultEnum;
  }

  public LeResultEnum getResultEnum() {
    return resultEnum;
  }

  public void setResultEnum(LeResultEnum resultEnum) {
    this.resultEnum = resultEnum;
  }
}

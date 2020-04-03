package com.le.share.exception;

import com.le.share.common.enums.ExpResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yinbohe.
 * Date 2019/8/20
 * Description exp异常.
 */
public class LeShareException extends RuntimeException {

  private Logger logger = LoggerFactory.getLogger(LeShareException.class);

  private ExpResultEnum resultEnum = ExpResultEnum.UN_KNOWN_EXCEPTION;

  public LeShareException(String message) {
    super(message);
  }

  public LeShareException(Throwable cause) {
    super(cause);
  }

  public LeShareException(String message, Throwable cause) {
    super(message, cause);
  }

  public LeShareException(ExpResultEnum resultEnum, Throwable cause) {
    super(cause);
    this.resultEnum = resultEnum;
  }

  public LeShareException(ExpResultEnum resultEnum) {
    super(resultEnum.getMessage());
    this.resultEnum = resultEnum;
  }

  public LeShareException(ExpResultEnum resultEnum, String message) {
    super(null == message ? resultEnum.getMessage() : message);
    this.resultEnum = resultEnum;
  }

  public ExpResultEnum getResultEnum() {
    return resultEnum;
  }

  public void setResultEnum(ExpResultEnum resultEnum) {
    this.resultEnum = resultEnum;
  }
}

package com.le.share.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinbohe.
 * Date 2019/8/20
 * Description ext enum.
 */
public enum LeResultEnum {

  //====================通用start==========================
  /**
   * 执行成功.
   */
  EXECUTE_SUCCESS("EXECUTE_SUCCESS", "执行成功", "200"),
  // biz_model_code
  // 100_10_01,
  /**
   * 数据库错误.
   **/
  DB_EXCEPTION("DB_EXCEPTION", "数据库错误", "500100"),
  /**
   * IO异常.
   **/
  IO_EXCEPTION("IO_EXCEPTION", "IO异常", "500200"),
  /**
   * 网络异常.
   **/
  NETWORK_EXCEPTION("NETWORK_EXCEPTION", "网络异常", "500300"),
  /**
   * 程序错误.
   */
  PROGRAM_ERROR("PROGRAM_ERROR", "程序错误", "500400"),
  /**
   * 系统异常.
   */
  SYSTEM_EXCEPTION("SYSTEM_EXCEPTION", "系统异常", "500410"),
  /**
   * 未知异常.
   */
  UN_KNOWN_EXCEPTION("UN_KNOWN_EXCEPTION", "未知异常", "500500"),
  /**
   * 数据不存在.
   */
  REQUEST_DATA_NOT_EXSIT("REQUEST_DATA_NOT_EXSIT", "请求数据不存在", "500600"),
  /**
   * 请求参数非法.
   */
  ILLEGAL_ARGUMENTS("ILLEGAL_ARGUMENTS", "请求参数非法", "400400"),

  ILLEGAL_ARGUMENTS_REPEAT("ILLEGAL_ARGUMENTS_REPEAT", "重复请求", "400410"),
  /**
   * http请求header参数非法.
   */
  AUTHORIZATION_IS_NULL("AUTHORIZATION_IS_NULL", "请求Authorization不能为空", "401400"),
  /**
   * Authorization格式不正确.
   */
  AUTHORIZATION_FORMAT_WRONG("AUTHORIZATION_FORMAT_WRONG", "Authorization格式不正确", "401401"),

  //====================共同end==========================
  HAS_NO_PERMISSON("HAS_NO_PERMISSON", "没有对应的操作权限", "401403"),

  REQUEST_WCI_SHORTID_FAILED("REQUEST_WCI_SHORTID_FAILED", "请求shortId失败", "500605"),

  REQUEST_TAF_REQUEST_FAILED("REQUEST_TAF_REQUEST_FAILED", "请求pipeline数据失败", "500606"),


  //====================业务异常==========================
  ARTICLE_LIKE_HAD_POST("ARTICLE_LIKE_HAD_POST", "不能重复点赞", "600400"),

  REPLY_LIKE_HAD_POST("REPLY_LIKE_HAD_POST", "不能对当前回复重复点赞", "600404"),

  REMOTE_CALL_WX_FAIL("REMOTE_CALL_WX_FAIL", "远程调用微信接口失败", "600500"),

  COMMENT_HAS_DELETED("COMMENT_HAS_DELETED", "评论已被删除", "600404"),
  ;

  /**
   * 枚举值.
   */
  private final String code;

  /**
   * 描述.
   */
  private final String message;

  /**
   * 错误码.
   */
  private final String errorCode;

  private LeResultEnum(String code, String message, String errorCode) {
    this.code = code;
    this.message = message;
    this.errorCode = errorCode;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String code() {
    return code;
  }

  public String message() {
    return message;
  }

  public String errorCode() {
    return errorCode;
  }

  /**
   * 根据code获取枚举信息.
   *
   * @param code code
   * @return ExpResultEnum
   */
  public static LeResultEnum getByCode(String code) {
    for (LeResultEnum resultEnum : values()) {
      if (resultEnum.getCode().equals(code)) {
        return resultEnum;
      }
    }
    return null;
  }

  /**
   * 获取枚举信息.
   *
   * @param errorCode errorCode
   * @return ExpResultEnum
   */
  public static LeResultEnum getByErrorCode(String errorCode) {
    for (LeResultEnum resultEnum : values()) {
      if (resultEnum.getErrorCode().equals(errorCode)) {
        return resultEnum;
      }
    }
    return null;
  }

  /**
   * 获取全部枚举.
   *
   * @return List
   */
  public List<LeResultEnum> getAllEnum() {
    List<LeResultEnum> list = new ArrayList<>();
    for (LeResultEnum resultEnum : values()) {
      list.add(resultEnum);
    }
    return list;
  }

  /**
   * 获取全部枚举值.
   *
   * @return List
   */
  public List<String> getAllEnumCode() {
    List<String> list = new ArrayList<String>();
    for (LeResultEnum resultEnum : values()) {
      list.add(resultEnum.code());
    }
    return list;
  }
}

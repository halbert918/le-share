package com.le.share.common.enums;

/**
 * Created by yinbohe.
 * Date 2020/4/2
 * Description 分享类型.
 */
public enum ShareType {

  ARTICLE(0, "文章"),

  DYNAMICS(1, "动态"),

  ;

  private int type;

  private String desc;

  ShareType(int type, String desc) {
    this.type = type;
    this.desc = desc;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}

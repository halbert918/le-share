package com.le.share.common.enums;

public enum LikeType {

    ARTICLE(0, "文章类点赞"),

    COMMENT(1, "评论类点赞"),

    REPLY(2, "回复类点赞");

    private int type;

    private String desc;

    LikeType(int type, String desc) {
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

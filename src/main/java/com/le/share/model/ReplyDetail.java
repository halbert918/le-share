package com.le.share.model;

public class ReplyDetail extends Reply {

    /**
     * 回复者别名.
     */
    private String nickName;
    /**
     * 回复者头像地址.
     */
    private String avatarUrl;
    /**
     * 回复对象别名.
     */
    private String toUserNickName;
    /**
     * 回复对象头像.
     */
    private String toUserAvatarUrl;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getToUserNickName() {
        return toUserNickName;
    }

    public void setToUserNickName(String toUserNickName) {
        this.toUserNickName = toUserNickName;
    }

    public String getToUserAvatarUrl() {
        return toUserAvatarUrl;
    }

    public void setToUserAvatarUrl(String toUserAvatarUrl) {
        this.toUserAvatarUrl = toUserAvatarUrl;
    }
}

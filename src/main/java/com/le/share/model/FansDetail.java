package com.le.share.model;

public class FansDetail extends Fans {
    /**
     * .
     */
    private String fansNickName;
    /**
     * 被粉丝头像地址.
     */
    private String fansAvatarUrl;
    /**
     * .
     */
    private String nickName;
    /**
     * .
     */
    private String avatarUrl;

    public String getFansNickName() {
        return fansNickName;
    }

    public void setFansNickName(String fansNickName) {
        this.fansNickName = fansNickName;
    }

    public String getFansAvatarUrl() {
        return fansAvatarUrl;
    }

    public void setFansAvatarUrl(String fansAvatarUrl) {
        this.fansAvatarUrl = fansAvatarUrl;
    }

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
}
package com.le.share.model;

public class FollowDetail extends Follow {
    /**
     * .
     */
    private String followdNickName;
    /**
     * 被关注者头像地址.
     */
    private String followdAvatarUrl;
    /**
     * .
     */
    private String nickName;
    /**
     * 关注者头像地址.
     */
    private String avatarUrl;

    public String getFollowdNickName() {
        return followdNickName;
    }

    public void setFollowdNickName(String followdNickName) {
        this.followdNickName = followdNickName;
    }

    public String getFollowdAvatarUrl() {
        return followdAvatarUrl;
    }

    public void setFollowdAvatarUrl(String followdAvatarUrl) {
        this.followdAvatarUrl = followdAvatarUrl;
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

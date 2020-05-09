package com.le.share.model;

public class CommentDetail extends Comment {
    /**
     * 评论者用户名称.
     */
    private String nickName;
    /**
     * 评论者用户头像地址.
     */
    private String avatarUrl;
    /**
     * 当前登陆用户是否对这条评论点过赞.
     */
    private boolean isLike;

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

    public boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(boolean like) {
        isLike = like;
    }
}

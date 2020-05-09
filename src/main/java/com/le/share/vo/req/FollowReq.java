package com.le.share.vo.req;

public class FollowReq {
    /**
     * 被关注用户.
     */
    private Long followby;
    /**
     * 是否关注 0:关注 1:取消.
     */
    private int isFollow;

    public Long getFollowby() {
        return followby;
    }

    public void setFollowby(Long followby) {
        this.followby = followby;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }
}

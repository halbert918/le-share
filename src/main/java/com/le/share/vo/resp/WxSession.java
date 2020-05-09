package com.le.share.vo.resp;

public class WxSession {

    private String openid;

    private String unionid;

    private Long userId;
    /**
     * 关注者数量.
     */
    private int followCount;
    /**
     * 粉丝数量.
     */
    private int fansCount;

    public WxSession() {
        this(null, null, null);
    }

    public WxSession(Long userId, String openid, String unionid) {
        this.userId = userId;
        this.openid = openid;
        this.unionid = unionid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    @Override
    public String toString() {
        return "WxSession{" +
                "openid='" + openid + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }
}

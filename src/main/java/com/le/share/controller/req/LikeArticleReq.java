package com.le.share.controller.req;

/**
 * Created by yinbohe.
 * Date 2020/4/3
 * Description 点赞/取消点赞.
 */
public class LikeArticleReq {
  /**
   * 文章ID.
   */
  private Long id;
  /**
   * 点赞用户.
   */
  private String userId;
  /**
   * 点赞/取消
   */
  private boolean like;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public boolean isLike() {
    return like;
  }

  public void setLike(boolean like) {
    this.like = like;
  }
}

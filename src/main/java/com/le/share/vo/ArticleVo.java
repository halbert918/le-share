package com.le.share.vo;

import com.le.share.model.Article;
import com.le.share.model.Comment;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/3/30
 * Description
 */
@ApiModel(value = "文章内容", description = "文章内容")
public class ArticleVo {
  /**
   * 文章内容.
   */
  private Article article;
  /**
   * 文章评论.
   */
  private List<Comment> comments;
  /**
   * 文章当前状态.
   */
  private Integer status;

  public Article getArticle() {
    return article;
  }

  public void setArticle(Article article) {
    if (article == null) {
      return;
    }
    setStatus(article.getStatus());
    this.article = article;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}

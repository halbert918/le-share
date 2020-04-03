package com.le.share.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by yinbohe.
 * Date 2020/3/30
 * Description
 */
@Document(collection = "t_article")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@ApiModel(value = "文章主题", description = "Article")
public class Article {

  @Id
  @ApiModelProperty(value = "ID", dataType = "long")
  private Long id;

  @ApiModelProperty(value = "作者", dataType = "string")
  @Indexed
  private String author;

  @ApiModelProperty(value = "标题", dataType = "string")
  private String title;

  @ApiModelProperty(value = "类型", dataType = "int")
  private Integer type;

  @ApiModelProperty(value = "内容", dataType = "int")
  private String content;

  @ApiModelProperty(value = "浏览数量", dataType = "int")
  private Integer viewNum = 0;

  @ApiModelProperty(value = "点赞数量", dataType = "int")
  private Integer likeNum = 0;

  @ApiModelProperty(value = "状态", dataType = "int")
  private Integer status = 1;

  @ApiModelProperty(value = "创建时间", dataType = "date")
  private Date createTime;

  @ApiModelProperty(value = "更新时间", dataType = "date")
  private Date updateTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getViewNum() {
    return viewNum;
  }

  public void setViewNum(Integer viewNum) {
    this.viewNum = viewNum;
  }

  public Integer getLikeNum() {
    return likeNum;
  }

  public void setLikeNum(Integer likeNum) {
    this.likeNum = likeNum;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}

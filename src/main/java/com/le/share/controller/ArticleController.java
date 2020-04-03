package com.le.share.controller;

import com.google.common.collect.Lists;
import com.le.share.controller.req.LikeArticleReq;
import com.le.share.model.Article;
import com.le.share.service.ArticleService;
import com.le.share.vo.ArticleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/3/30
 * Description
 */
@RestController
@Api(value = "文章信息")
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  @ApiOperation(value = "获取文章信息列表", notes = "获取文章信息列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @GetMapping("/{shareType}/articles")
  public List<Article> getArticles(@PathVariable Integer shareType,
                                   @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {
    return articleService.getArticles(1, offset, limit);
  }

  @ApiOperation(value = "获取文章详情", notes = "获取文章详情", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @GetMapping("/article/{id}")
  public ArticleVo getArticle(@PathVariable Long id) {
    return articleService.getArticle(id);
  }

  @ApiOperation(value = "发表文章", notes = "发表文章", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PostMapping("/article")
  public void publishArticle(@RequestBody Article article) {
    articleService.addArticle(article);
  }

  @ApiOperation(value = "删除文章", notes = "删除文章", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @DeleteMapping("/article/{id}")
  public void delArticle(@PathVariable Long id) {

  }

  @ApiOperation(value = "重新编辑文章", notes = "重新编辑文章", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PutMapping("/article/{id}")
  public void updateArticle(@PathVariable Long id, @RequestBody Article article) {

  }

  @ApiOperation(value = "点赞/取消点赞", notes = "点赞/取消点赞", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PostMapping("/article/{id}")
  public void likeArticle(@PathVariable Long id, @RequestBody LikeArticleReq req) {

  }

}

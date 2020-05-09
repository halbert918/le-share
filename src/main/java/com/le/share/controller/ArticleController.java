package com.le.share.controller;

import com.le.share.common.enums.LeResultEnum;
import com.le.share.exception.LeShareException;
import com.le.share.model.Article;
import com.le.share.model.ArticleDetail;
import com.le.share.model.UserInfo;
import com.le.share.security.domain.User;
import com.le.share.service.ArticleService;
import com.le.share.vo.req.ArticleReq;
import com.le.share.vo.resp.ArticleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/4/30
 * Description
 */
@RestController
@Api(value = "文章信息")
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  @ApiOperation(value = "获取首页滑动图片的文章", notes = "获取首页滑动图片的文章", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @GetMapping("/api/swiper/articles")
  public List<ArticleDetail> getSwiperArticles(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "5") int limit) {
    return articleService.getSwiperArticles(offset, limit);
  }


  @ApiOperation(value = "新增文章信息", notes = "新增文章信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PostMapping("/api/article")
  public Long addArticle(@AuthenticationPrincipal User user, @Validated @RequestBody ArticleReq req) {
    if (user == null || user.getUserId() == null) {
      throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
    }
    Article article = new Article();
    article.setTitle(req.getTitle());
    article.setContent(req.getContent());
    article.setType(req.getType());
    article.setAuthorUserId(user.getUserId());
    return articleService.addArticle(article, req.getImages());
  }

  @ApiOperation(value = "更新文章信息", notes = "更新文章信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PutMapping("/api/article/{id}")
  public void updateArticle(@AuthenticationPrincipal User user, @PathVariable Long id, @RequestBody ArticleReq req) {
    if (user == null || user.getUserId() == null) {
      throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
    }
    Article article = new Article();
    article.setId(id);
    article.setTitle(req.getTitle());
    article.setContent(req.getContent());
    article.setType(req.getType());
    article.setAuthorUserId(user.getUserId());
    article.setUpdator(String.valueOf(user.getUserId()));
    articleService.updateArticle(article, req.getImages());
  }

  @ApiOperation(value = "删除文章信息", notes = "删除文章信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @DeleteMapping("/api/article/{id}")
  public void deleteArticles(@AuthenticationPrincipal User user, @PathVariable Long id) {
    if (user == null || user.getUserId() == null) {
      throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
    }
    articleService.deleteArticle(user.getUserId(), id);
  }

  @ApiOperation(value = "获取文章信息列表", notes = "获取文章信息列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @GetMapping("/api/articles")
  public List<ArticleDetail> getArticles(@RequestParam(defaultValue = "0") Integer type, @RequestParam(required = false) String keyword,
                                         @RequestParam(required = false) Long prevId,
                                         @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {
    return articleService.getArticles(type, prevId, keyword, offset, limit);
  }

  @ApiOperation(value = "获取文章信息详情", notes = "获取文章信息详情", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @GetMapping("/api/article/{id}")
  public ArticleVo getArticle(@AuthenticationPrincipal User user, @PathVariable Long id) {
    return articleService.getArticle(id, user);
  }

  @ApiOperation(value = "文章点赞", notes = "文章点赞", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PutMapping("/api/article/like/{id}")
  public void likeArticle(@AuthenticationPrincipal User user, @PathVariable Long id) {
    if (user == null || user.getUserId() == null) {
      throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
    }
    articleService.likeArticle(id, user.getUserId());
  }

  @ApiOperation(value = "取消文章点赞", notes = "取消文章点赞", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PutMapping("/api/article/unlike/{id}")
  public void unLikeArticle(@AuthenticationPrincipal User user, @PathVariable Long id) {
    if (user == null || user.getUserId() == null) {
      throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
    }
    articleService.unLikeArticle(id, user.getUserId());
  }

  @ApiOperation(value = "获取当前文章的点赞用户", notes = "获取当前文章的点赞用户", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @GetMapping("/api/article/{id}/likes")
  public List<UserInfo> getArticle(@PathVariable Long id,
                                   @RequestParam(defaultValue = "1") int offset,
                                   @RequestParam(defaultValue = "20") int limit) {
    return articleService.getLikeUsers(id, offset, limit);
  }

  @ApiOperation(value = "获取我的文章信息详情", notes = "获取我的文章信息详情", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @GetMapping("/my/article/{id}")
  public ArticleDetail getMyArticle(@AuthenticationPrincipal User user, @PathVariable Long id) {
    if (user == null || user.getUserId() == null) {
      throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
    }
    return articleService.getMyArticle(id);
  }

  @ApiOperation(value = "获取文章信息列表", notes = "获取文章信息列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @GetMapping("/my/articles")
  public List<ArticleDetail> getUserArticles(@AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") Integer type,
                                         @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {
    if (user == null || user.getUserId() == null) {
      throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
    }
    return articleService.getUserArticles(user.getUserId(), offset, limit);
  }

  @ApiOperation(value = "获取当前用户的点赞列表", notes = "获取当前用户的点赞列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @GetMapping("/my/likes")
  public List<ArticleDetail> getLikes(@AuthenticationPrincipal User user,
                                      @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {
    if (user == null || user.getUserId() == null) {
      throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在查询");
    }
    return articleService.getLikes(user.getUserId(), offset, limit);
  }

}

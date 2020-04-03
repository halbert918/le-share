package com.le.share.service;

import com.le.share.model.Article;
import com.le.share.vo.ArticleVo;

import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/3/30
 * Description
 */
public interface ArticleService {

  void addArticle(Article article);

  void updateArticle(Article article);

  void deleteArticle(Long id);

  List<Article> getArticles(Integer type, Integer offset, Integer limit);

  ArticleVo getArticle(Long id);
}

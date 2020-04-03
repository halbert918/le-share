package com.le.share.service.impl;

import com.le.share.common.enums.ExpResultEnum;
import com.le.share.common.enums.ShareType;
import com.le.share.exception.LeShareException;
import com.le.share.model.Article;
import com.le.share.model.Comment;
import com.le.share.repository.ArticleRepository;
import com.le.share.repository.CommentRepository;
import com.le.share.service.ArticleService;
import com.le.share.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by yinbohe.
 * Date 2020/3/30
 * Description
 */
@Service
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Override
  public void addArticle(Article article) {
    articleRepository.insert(article);
  }

  @Override
  public void updateArticle(Article article) {
    articleRepository.save(article);
  }

  @Override
  public void deleteArticle(Long id) {
    Article article = articleRepository.findByIdAndStatus(id, 1);
    if (article == null) {
      throw new LeShareException(ExpResultEnum.REQUEST_DATA_NOT_EXSIT);
    }
    article.setStatus(0);
    articleRepository.save(article);
  }

  @Override
  public List<Article> getArticles(Integer type, Integer offset, Integer limit) {
    return articleRepository.findByTypeAndStatus(
        ShareType.LIFE.getType(),
        1,
        PageRequest.of(offset, limit, Sort.by("createTime").descending())
    );
  }

  @Override
  public ArticleVo getArticle(Long id) {
    // 1.获取文章信息
    Optional<Article> optional = articleRepository.findById(id);
    if (!optional.isPresent()) {
      throw new LeShareException(ExpResultEnum.REQUEST_DATA_NOT_EXSIT);
    }
    ArticleVo articleVo = new ArticleVo();
    Article article = optional.get();
    // 若文章已经被删除，则直接返回删除状态
    if (article.getStatus() == 0) {
      articleVo.setStatus(0);
      return articleVo;
    }
    articleVo.setArticle(article);

    // 2.获取一级评论列表
    List<Comment> comments = commentRepository.findByArtId(id, PageRequest.of(0, 20));
    articleVo.setComments(comments);
    return articleVo;
  }
}

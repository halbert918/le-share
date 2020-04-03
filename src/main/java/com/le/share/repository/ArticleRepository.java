package com.le.share.repository;

import com.le.share.model.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/3/30
 * Description
 */
@Repository
public interface ArticleRepository extends MongoRepository<Article, Long> {

  List<Article> findByTypeAndStatus(Integer type, Integer status, Pageable pageable);

  Article findByIdAndStatus(Long id, Integer status);

}

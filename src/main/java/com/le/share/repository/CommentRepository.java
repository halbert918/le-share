package com.le.share.repository;

import com.le.share.model.Comment;
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
public interface CommentRepository extends MongoRepository<Comment, Long> {

  // 根据文章ID获取评论列表
  List<Comment> findByArtId(Long artId, Pageable pageable);

}

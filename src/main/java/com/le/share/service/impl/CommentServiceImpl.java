package com.le.share.service.impl;

import com.le.share.model.Comment;
import com.le.share.repository.CommentRepository;
import com.le.share.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yinbohe.
 * Date 2020/4/3
 * Description
 */
@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentRepository commentRepository;

  @Override
  public void addComment(Comment comment) {
    commentRepository.insert(comment);
  }
}

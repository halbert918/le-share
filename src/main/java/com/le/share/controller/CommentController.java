package com.le.share.controller;

import com.le.share.model.Comment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/3/30
 * Description 评论入口
 */
@RestController
@Api(value = "一级/二级评论接口")
public class CommentController {


  @ApiOperation(value = "评论", notes = "对文章进行评论", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PostMapping("/article/{artId}/comment")
  public void publishComment(@PathVariable Long artId, @RequestBody Comment comment) {

  }

  @ApiOperation(value = "删除评论", notes = "删除文章评论", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @DeleteMapping("/article/{artId}/comment/{commentId}")
  public void delComment(@PathVariable Long artId, @PathVariable Long commentId) {

  }

  @ApiOperation(value = "评论列表", notes = "获取文章的评论列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @GetMapping("/article/{artId}/comment")
  public List<Comment> getComments(@PathVariable Long artId,
                                   @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {
    return null;
  }

}

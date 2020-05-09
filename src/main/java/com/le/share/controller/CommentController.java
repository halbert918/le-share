package com.le.share.controller;

import com.le.share.common.enums.LeResultEnum;
import com.le.share.exception.LeShareException;
import com.le.share.model.Comment;
import com.le.share.model.CommentDetail;
import com.le.share.security.domain.User;
import com.le.share.service.CommentService;
import com.le.share.vo.req.CommentReq;
import com.le.share.vo.resp.CommentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/4/30
 * Description
 */
@RestController
@Api(value = "评论")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "发表评论", notes = "发表评论", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/api/comment")
    public Long postComment(@AuthenticationPrincipal User user, @RequestBody CommentReq req) {
        if (user == null || user.getUserId() == null) {
            throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在评论");
        }
        Comment comment = new Comment();
        comment.setArtId(req.getArtId());
        comment.setContent(req.getContent());
        comment.setCommentUserId(user.getUserId());
        return commentService.addComment(comment);
    }

    @ApiOperation(value = "获取评论列表", notes = "获取评论列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/api/{artId}/comments")
    public List<CommentDetail> getComments(@AuthenticationPrincipal User user, @PathVariable(name = "artId") Long artId,
                                           @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {
        return commentService.getComments(user, artId, offset, limit);
    }

    @ApiOperation(value = "获取评论列表", notes = "获取评论列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/api/comment/{id}")
    public CommentDetail getComment(@AuthenticationPrincipal User user, @PathVariable(name = "id") Long id) {
        return commentService.getComment(user, id);
    }

    @ApiOperation(value = "评论点赞", notes = "评论点赞", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PutMapping("/api/comment/like/{id}")
    public void likeArticle(@AuthenticationPrincipal User user, @PathVariable Long id) {
        if (user == null || user.getUserId() == null) {
            throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在评论");
        }
        commentService.likeArticle(id, user.getUserId());
    }

    @ApiOperation(value = "取消评论点赞", notes = "取消评论点赞", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PutMapping("/api/comment/unlike/{id}")
    public void unLikeArticle(@AuthenticationPrincipal User user, @PathVariable Long id) {
        if (user == null || user.getUserId() == null) {
            throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在评论");
        }
        commentService.unLikeArticle(id, user.getUserId());
    }

    @ApiOperation(value = "获取当前用户的评论列表", notes = "获取当前用户的评论列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/my/comments")
    public List<CommentVo> getComments(@AuthenticationPrincipal User user,
                                       @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {
        if (user == null || user.getUserId() == null) {
            throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在查询");
        }
        return commentService.getComments(user.getUserId(), offset, limit);
    }

}

































































































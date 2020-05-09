package com.le.share.controller;

import com.le.share.common.enums.LeResultEnum;
import com.le.share.exception.LeShareException;
import com.le.share.model.CommentDetail;
import com.le.share.model.Reply;
import com.le.share.model.ReplyDetail;
import com.le.share.security.domain.User;
import com.le.share.service.ReplyService;
import com.le.share.vo.req.ReplyReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/5/3
 * Description
 */
@RestController
@Api(value = "二级回复")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @ApiOperation(value = "回复评论/回复", notes = "回复评论/回复", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/api/reply")
    public Long postReply(@AuthenticationPrincipal User user, @RequestBody ReplyReq req) {
        if (user == null || user.getUserId() == null) {
            throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在回复");
        }
        Reply reply = new Reply();
        BeanUtils.copyProperties(req, reply);
        reply.setFromUserId(user.getUserId());
        return replyService.addReply(reply);
    }

    @ApiOperation(value = "删除回复", notes = "删除回复", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @DeleteMapping("/api/reply/{id}")
    public void deleteReply(@AuthenticationPrincipal User user, @PathVariable Long id) {
        if (user == null || user.getUserId() == null) {
            throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
        }
        replyService.deleteReply(id);
    }

    @ApiOperation(value = "获取评论回复列表", notes = "获取评论回复列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/api/{commentId}/replys")
    public List<ReplyDetail> getReplys(@PathVariable(name = "commentId") Long commentId,
                                         @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {
        return replyService.getReplys(commentId, offset, limit);
    }

    @ApiOperation(value = "获取评论列表", notes = "获取评论列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/api/reply/{id}")
    public ReplyDetail getReply(@PathVariable(name = "id") Long id) {
        return replyService.getReply(id);
    }

    @ApiOperation(value = "对回复点赞", notes = "对回复点赞", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PutMapping("/api/reply/like/{id}")
    public void likeArticle(@AuthenticationPrincipal User user, @PathVariable Long id) {
        if (user == null || user.getUserId() == null) {
            throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
        }
        replyService.likeArticle(id, user.getUserId());
    }

    @ApiOperation(value = "取消对回复点赞", notes = "取消对回复点赞", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PutMapping("/api/reply/unlike/{id}")
    public void unLikeArticle(@AuthenticationPrincipal User user, @PathVariable Long id) {
        if (user == null || user.getUserId() == null) {
            throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
        }
        replyService.unLikeArticle(id, user.getUserId());
    }
}

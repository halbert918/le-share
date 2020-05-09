package com.le.share.controller;

import com.le.share.common.enums.LeResultEnum;
import com.le.share.exception.LeShareException;
import com.le.share.model.FansDetail;
import com.le.share.model.FollowDetail;
import com.le.share.model.Reply;
import com.le.share.security.domain.User;
import com.le.share.service.FollowService;
import com.le.share.vo.req.FollowReq;
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
@Api(value = "关注/取消关注")
public class FollowByController {

    @Autowired
    private FollowService followService;

    @ApiOperation(value = "关注", notes = "关注", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/api/follow")
    public void follow(@AuthenticationPrincipal User user, @RequestBody FollowReq req) {
        if (user == null || user.getUserId() == null) {
            throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
        }
        followService.follow(user.getUserId(), req.getFollowby());
    }

    @ApiOperation(value = "取消关注", notes = "取消关注", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/api/un-follow")
    public void unFollow(@AuthenticationPrincipal User user, @RequestBody FollowReq req) {
        if (user == null || user.getUserId() == null) {
            throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
        }
        followService.unFollow(user.getUserId(), req.getFollowby());
    }

    @ApiOperation(value = "我的关注", notes = "我的关注", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/my/follows")
    public List<FollowDetail> myFollows(@AuthenticationPrincipal User user,
                                        @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {

        if (user == null || user.getUserId() == null) {
            throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
        }
        return followService.getFollows(user.getUserId(), offset, limit);
    }

    @ApiOperation(value = "我的关注", notes = "我的关注", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/my/fans")
    public List<FansDetail> myFans(@AuthenticationPrincipal User user,
                                   @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {

        if (user == null || user.getUserId() == null) {
            throw new LeShareException(LeResultEnum.ILLEGAL_ARGUMENTS, "请先登陆后在操作");
        }
        return followService.getFans(user.getUserId(), offset, limit);
    }

}

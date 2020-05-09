package com.le.share.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.le.share.common.enums.LeResultEnum;
import com.le.share.common.enums.LikeType;
import com.le.share.exception.LeShareException;
import com.le.share.mapper.CommentMapper;
import com.le.share.mapper.LikeUserMapper;
import com.le.share.mapper.ReplyMapper;
import com.le.share.mapper.UserInfoMapper;
import com.le.share.mapper.ext.ExtCommentMapper;
import com.le.share.mapper.ext.ExtLikeUserMapper;
import com.le.share.mapper.ext.ExtReplyMapper;
import com.le.share.mapper.ext.ExtUserInfoMapper;
import com.le.share.model.*;
import com.le.share.service.ReplyService;
import com.le.share.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by yinbohe.
 * Date 2020/5/3
 * Description
 */
@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private ExtReplyMapper extReplyMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private ExtUserInfoMapper extUserInfoMapper;

    @Autowired
    private LikeUserMapper likeUserMapper;

    @Autowired
    private ExtLikeUserMapper extLikeUserMapper;

    @Autowired
    private ExtCommentMapper extCommentMapper;

    @Transactional
    @Override
    public Long addReply(Reply reply) {
        if (reply.getReplyType() == 0) { // 回复评论，判断评论是否存在.
            Comment comment = commentMapper.selectByPrimaryKey(reply.getCommentId());
            if (comment == null) {
                throw new LeShareException(LeResultEnum.REQUEST_DATA_NOT_EXSIT, "回复的评论不存在.");
            }
        } else { // 回复某条回复，判断某条回复是否存在.
            Reply parentReply = replyMapper.selectByPrimaryKey(reply.getReplyId());
            if (parentReply == null) {
                throw new LeShareException(LeResultEnum.REQUEST_DATA_NOT_EXSIT, "原回复不存在.");
            }
        }
        reply.setId(IdWorker.genNextId());
        reply.setCreateTime(new Date());
        replyMapper.insertSelective(reply);
        extCommentMapper.incrReplyCount(reply.getCommentId());
        return reply.getId();
    }

    @Override
    public void deleteReply(Long id) {
        replyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ReplyDetail> getReplys(Long commentId, int offset, int limit) {
        Page<ReplyDetail> page = PageHelper.offsetPage(offset, limit, false)
                .doSelectPage(() -> extReplyMapper.selectList(commentId));
        List<ReplyDetail> details = page.getResult();
        if (CollectionUtils.isEmpty(details)) {
            return details;
        }

        // 获取回复对象别名信息（以后可用缓存）
        List<Long> toUserIds = details.stream()
                .filter(e -> e.getReplyType() == 1)
                .map(e -> e.getToUserId())
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(toUserIds)) {
            return details;
        }

        // 填充回复对象别名信息.
        List<UserInfo> userInfos = extUserInfoMapper.exsitUserInfo(toUserIds);
        if (!CollectionUtils.isEmpty(userInfos)) {
            Map<Long, UserInfo> map = userInfos.stream()
                    .collect(Collectors.toMap(UserInfo::getId, userInfo -> userInfo));
            details.stream().forEach(detail -> {
                UserInfo userInfo = map.get(detail.getToUserId());
                if (userInfo != null) {
                    detail.setToUserNickName(userInfo.getNickName());
                    detail.setToUserAvatarUrl(userInfo.getAvatarUrl());
                }
            });
        }

        return details;
    }

    @Override
    public ReplyDetail getReply(Long id) {
        Optional<ReplyDetail> optional = extReplyMapper.selectOne(id);
        ReplyDetail detail = optional.orElseThrow(
                () -> new LeShareException(LeResultEnum.REQUEST_DATA_NOT_EXSIT, "查询的回复数据不存在.")
        );
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(detail.getToUserId());
        if (userInfo == null) {
            throw new LeShareException(LeResultEnum.REQUEST_DATA_NOT_EXSIT);
        }
        detail.setToUserNickName(userInfo.getNickName());
        detail.setToUserAvatarUrl(userInfo.getAvatarUrl());
        return detail;
    }

    @Transactional
    @Override
    public void likeArticle(Long id, Long likeUserId) {
        // 判断是否点赞过
        Optional<LikeUser> optional = extLikeUserMapper.selectOne(id, likeUserId, 0);
        optional.ifPresent(e -> {
            throw new LeShareException(LeResultEnum.ARTICLE_LIKE_HAD_POST);
        });
        // 新增点赞记录
        LikeUser likeUser = new LikeUser();
        likeUser.setId(IdWorker.genNextId());
        likeUser.setTargetId(id);
        likeUser.setLikeUserId(likeUserId);
        likeUser.setLikeType(LikeType.REPLY.getType());
        likeUserMapper.insert(likeUser);
        // 文章点赞数+1
        extReplyMapper.incrLikeCount(id, 1);
    }

    @Transactional
    @Override
    public void unLikeArticle(Long id, Long unLikeUserId) {
        // 判断是否点赞过
        Optional<LikeUser> optional = extLikeUserMapper.selectOne(id, unLikeUserId, 0);
        LikeUser likeUser = optional.orElseThrow(() -> new LeShareException(LeResultEnum.REPLY_LIKE_HAD_POST));
        // 取消点赞
        likeUser.setStatus(1);
        likeUserMapper.updateByPrimaryKey(likeUser);
        // 文章点赞数-1
        extReplyMapper.incrLikeCount(id, -1);
    }
}

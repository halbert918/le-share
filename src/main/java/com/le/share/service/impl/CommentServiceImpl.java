package com.le.share.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.le.share.common.enums.LeResultEnum;
import com.le.share.common.enums.LikeType;
import com.le.share.exception.LeShareException;
import com.le.share.mapper.CommentMapper;
import com.le.share.mapper.LikeUserMapper;
import com.le.share.mapper.ext.ExtArticleMapper;
import com.le.share.mapper.ext.ExtCommentMapper;
import com.le.share.mapper.ext.ExtLikeUserMapper;
import com.le.share.model.ArticleDetail;
import com.le.share.model.Comment;
import com.le.share.model.CommentDetail;
import com.le.share.model.LikeUser;
import com.le.share.security.domain.User;
import com.le.share.service.CommentService;
import com.le.share.util.IdWorker;
import com.le.share.vo.resp.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by yinbohe.
 * Date 2020/5/3
 * Description
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ExtCommentMapper extCommentMapper;

    @Autowired
    private ExtLikeUserMapper extLikeUserMapper;

    @Autowired
    private LikeUserMapper likeUserMapper;

    @Autowired
    private ExtArticleMapper extArticleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addComment(Comment comment) {
        comment.setId(IdWorker.genNextId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        comment.setLikeCount(0);
        commentMapper.insertSelective(comment);
        extArticleMapper.incrCommentCount(comment.getArtId(), 1);
        return comment.getId();
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setStatus(1);
        commentMapper.updateByPrimaryKey(comment);
    }

    @Override
    public List<CommentDetail> getComments(User user, Long artId, int offset, int limit) {
        Page<CommentDetail> page = PageHelper.offsetPage(offset, limit, false)
                .doSelectPage(() -> extCommentMapper.selectList(artId));

        List<CommentDetail> comments = page.getResult();
        // 获取当前用户所有点赞评论
        if (user != null && user.getUserId() != null && !CollectionUtils.isEmpty(comments)) {
            List<Long> ids = comments.stream().map(e -> e.getId())
                    .collect(Collectors.toList());
            List<Long> targetIds = extLikeUserMapper.exsitTargetIds(user.getUserId(), ids);
            if (!CollectionUtils.isEmpty(targetIds)) {
                comments.stream().forEach(comment -> {
                    Optional<Long> optional = targetIds.stream()
                            .filter(id -> id.longValue() == comment.getId().longValue())
                            .findFirst();
                    optional.ifPresent(e -> comment.setIsLike(true));
                });
            }
        }

        return comments;
    }

    @Override
    public CommentDetail getComment(User user, Long id) {
        Optional<CommentDetail> optional = extCommentMapper.selectOne(id);
        CommentDetail detail = optional.orElseThrow(
                () -> new LeShareException(LeResultEnum.COMMENT_HAS_DELETED)
        );
        if (detail.getStatus() == 1) {
            throw new LeShareException(LeResultEnum.COMMENT_HAS_DELETED);
        }
        // 判断当前用户是否点赞
        if (user != null && user.getUserId() != null) {
            Optional<LikeUser> likeUser = extLikeUserMapper.selectOne(detail.getId(), user.getUserId(), 0);
            likeUser.ifPresent(e -> {
                detail.setIsLike(true);
            });
        }
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
        likeUser.setLikeType(LikeType.COMMENT.getType());
        likeUser.setLikeUserId(likeUserId);
        likeUserMapper.insert(likeUser);
        // 文章点赞数+1
        extCommentMapper.incrLikeCount(id, 1);
    }

    @Transactional
    @Override
    public void unLikeArticle(Long id, Long unLikeUserId) {
        // 判断是否点赞过
        Optional<LikeUser> optional = extLikeUserMapper.selectOne(id, unLikeUserId, 0);
        LikeUser likeUser = optional.orElseThrow(() -> new LeShareException(LeResultEnum.ARTICLE_LIKE_HAD_POST));
        // 取消点赞
        likeUser.setStatus(1);
        likeUserMapper.updateByPrimaryKey(likeUser);
        // 文章点赞数-1
        extCommentMapper.incrLikeCount(id, -1);
    }

    @Override
    public List<CommentVo> getComments(Long userId, int offset, int limit) {
        List<CommentDetail> comments = extCommentMapper.selectListByUserId(userId, offset, limit);
        if (CollectionUtils.isEmpty(comments)) {
            return Lists.newArrayListWithCapacity(0);
        }
        // 获取评论对应的文章信息
        List<Long> artIds = comments.stream()
                .map(e -> e.getArtId())
                .distinct()
                .collect(Collectors.toList());
        List<ArticleDetail> articles = extArticleMapper.selectListByArtIds(artIds);
        // 构造返回值
        return comments.stream().map(comment -> {
            Optional<ArticleDetail> optional = articles.stream()
                    .filter(article -> comment.getArtId().longValue() == article.getId().longValue())
                    .findFirst();
            return new CommentVo(comment, optional.isPresent() ? optional.get() : null);
        }).collect(Collectors.toList());
    }
}

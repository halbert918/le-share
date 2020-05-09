package com.le.share.service;

import com.le.share.model.Comment;
import com.le.share.model.CommentDetail;
import com.le.share.security.domain.User;
import com.le.share.vo.resp.CommentVo;

import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/5/3
 * Description
 */
public interface CommentService {

    /**
     * 新增用户评论.
     * @param comment
     * @return
     */
    Long addComment(Comment comment);

    /**
     * 删除评论.
     * @param id
     */
    void deleteComment(Long id);
    /**
     * 获取评论列表.
     * @param artId
     * @param offset
     * @param limit
     * @return
     */
    List<CommentDetail> getComments(User user, Long artId, int offset, int limit);

    /**
     * 获取评论信息.
     * @param user
     * @param id
     * @return
     */
    CommentDetail getComment(User user, Long id);

    /**
     * 点赞.
     * @param id
     * @param likeUserId
     */
    void likeArticle(Long id, Long likeUserId);

    /**
     * 取消点赞.
     * @param id
     * @param unLikeUserId
     */
    void unLikeArticle(Long id, Long unLikeUserId);

    /**
     * 查询当前用户的评论列表.
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<CommentVo> getComments(Long userId, int offset, int limit);

}

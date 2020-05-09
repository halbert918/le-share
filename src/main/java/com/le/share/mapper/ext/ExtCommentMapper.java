package com.le.share.mapper.ext;

import com.le.share.model.CommentDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface ExtCommentMapper {
    /**
     * 获取评论详情.
     * @param id
     * @return
     */
    Optional<CommentDetail> selectOne(Long id);
    /**
     * 获取评论列表.
     * @param artId
     * @return
     */
    List<CommentDetail> selectList(Long artId);

    /**
     * 获取用户的评论列表.
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<CommentDetail> selectListByUserId(@Param("userId")Long userId, @Param("offset")int offset, @Param("limit")int limit);

    /**
     * 点赞数.
     * @param id
     * @param incr
     * @return
     */
    int incrLikeCount(@Param("id") long id, @Param("incr") int incr);

    /**
     * 增加回复数.
     * @param id
     * @return
     */
    int incrReplyCount(@Param("id") long id);

}
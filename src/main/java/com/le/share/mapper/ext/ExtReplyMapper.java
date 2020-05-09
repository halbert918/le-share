package com.le.share.mapper.ext;

import com.le.share.model.ReplyDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface ExtReplyMapper {
    /**
     * 获取回复.
     * @param id
     * @return
     */
    Optional<ReplyDetail> selectOne(Long id);

    /**
     * 获取评论下回复列表.
     * @param commentId
     * @return
     */
    List<ReplyDetail> selectList(Long commentId);

    /**
     * 点赞数.
     * @param id
     * @param incr
     * @return
     */
    int incrLikeCount(@Param("id") long id, @Param("incr") int incr);
}
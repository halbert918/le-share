package com.le.share.service;

import com.le.share.model.Reply;
import com.le.share.model.ReplyDetail;

import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/5/3
 * Description
 */
public interface ReplyService {
    /**
     * 新增回复.
     * @param reply
     * @return
     */
    Long addReply(Reply reply);

    /**
     * 删除某个回复.
     * @param id
     */
    void deleteReply(Long id);

    /**
     * 获取回复列表.
     * @param commentId
     * @param offset
     * @param limit
     * @return
     */
    List<ReplyDetail> getReplys(Long commentId, int offset, int limit);

    /**
     * 获取回复.
     * @param id
     * @return
     */
    ReplyDetail getReply(Long id);
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

}

package com.le.share.service;

import com.le.share.model.FansDetail;
import com.le.share.model.FollowDetail;

import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/5/3
 * Description
 */
public interface FollowService {
    /**
     * 关注.
     * @param userId
     * @param followBy
     */
    void follow(Long userId, Long followBy);

    /**
     * 取消关注.
     * @param userId
     * @param followBy
     */
    void unFollow(Long userId, Long followBy);

    /**
     * 获取我的关注列表.
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<FollowDetail> getFollows(Long userId, int offset, int limit);

    /**
     * 获取我的粉丝列表.
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<FansDetail> getFans(Long userId, int offset, int limit);
}

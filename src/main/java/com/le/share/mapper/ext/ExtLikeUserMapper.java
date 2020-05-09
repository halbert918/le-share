package com.le.share.mapper.ext;

import com.le.share.model.LikeUser;
import com.le.share.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface ExtLikeUserMapper {
    /**
     * 获取当前用户点赞信息.
     * @param targetId      文章ID、评论、回复
     * @param likeUserId    点赞用户
     * @param status        当前记录状态0 正常 1 已取消
     * @return
     */
    Optional<LikeUser> selectOne(long targetId, long likeUserId, int status);

    /**
     * 获取点赞用户.
     * @param targetId
     * @return
     */
    List<UserInfo> getLikeUsers(Long targetId);

    /**
     * 查找当前用户点赞的数据（判断用户对哪些记录点赞了）.
     * @param userId
     * @param targetIds
     * @return
     */
    List<Long> exsitTargetIds(@Param("userId") Long userId, @Param("targetIds") List<Long> targetIds);

    /**
     * 获取用户的点赞数据.
     * @param userId
     * @param type
     * @param offset
     * @param limit
     * @return
     */
    List<Long> getTargetIds(@Param("userId") Long userId, @Param("type") int type, int offset, int limit);

}
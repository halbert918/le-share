package com.le.share.mapper.ext;

import com.le.share.model.FollowDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface ExtFollowMapper {

    Optional<FollowDetail> selectOne(@Param("followBy") Long followBy, @Param("userId") Long userId);

    /**
     * 获取我的关注列表.
     * @param userId
     * @return
     */
    List<FollowDetail> selectList(@Param("userId") Long userId, @Param("offset") int offset, @Param("limit") int limit);
}
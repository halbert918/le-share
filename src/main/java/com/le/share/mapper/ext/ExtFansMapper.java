package com.le.share.mapper.ext;

import com.le.share.model.FansDetail;
import com.le.share.model.FollowDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface ExtFansMapper {

    Optional<FansDetail> selectOne(@Param("fansUserId") Long fansUserId, @Param("userId") Long userId);

    /**
     * 获取我的粉丝列表.
     * @param userId
     * @return
     */
    List<FansDetail> selectList(@Param("userId") Long userId, @Param("offset") int offset, @Param("limit") int limit);
}
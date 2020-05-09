package com.le.share.mapper.ext;

import com.le.share.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface ExtUserInfoMapper {
    /**
     * 根据openid获取用户信息.
     * @param openid
     * @return
     */
    Optional<UserInfo> selectByOpenId(String openid);

    /**
     * 获取用户基础信息.
     * @param userId
     * @return
     */
    Optional<UserInfo> selectBaseUserInfo(long userId);

    /**
     * 获取用户信息.
     * @param userIds
     * @return
     */
    List<UserInfo> exsitUserInfo(@Param("userIds") List<Long> userIds);
}
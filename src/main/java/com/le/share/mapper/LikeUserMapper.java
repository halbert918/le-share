package com.le.share.mapper;

import com.le.share.model.LikeUser;

public interface LikeUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LikeUser record);

    int insertSelective(LikeUser record);

    LikeUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LikeUser record);

    int updateByPrimaryKey(LikeUser record);
}
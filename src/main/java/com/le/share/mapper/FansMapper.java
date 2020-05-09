package com.le.share.mapper;

import com.le.share.model.Fans;

public interface FansMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Fans record);

    int insertSelective(Fans record);

    Fans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Fans record);

    int updateByPrimaryKey(Fans record);
}
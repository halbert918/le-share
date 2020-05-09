package com.le.share.service.impl;

import com.le.share.mapper.FansMapper;
import com.le.share.mapper.FollowMapper;
import com.le.share.mapper.ext.ExtFansMapper;
import com.le.share.mapper.ext.ExtFollowMapper;
import com.le.share.model.Fans;
import com.le.share.model.FansDetail;
import com.le.share.model.Follow;
import com.le.share.model.FollowDetail;
import com.le.share.service.FollowService;
import com.le.share.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by yinbohe.
 * Date 2020/5/3
 * Description
 */
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private ExtFollowMapper extFollowMapper;

    @Autowired
    private FansMapper fansMapper;

    @Autowired
    private ExtFansMapper extFansMapper;

    @Transactional
    @Override
    public void follow(Long userId, Long followBy) {
        // 判断用户已经关注过, 已经关注过，直接返回
        Optional<FollowDetail> optional = extFollowMapper.selectOne(followBy, userId);
        if (optional.isPresent()) {
            return;
        }
        // 关注
        Follow follow = new Follow();
        follow.setId(IdWorker.genNextId());
        follow.setFollowdUserId(followBy);
        follow.setUserId(userId);
        follow.setCreateTime(new Date());
        followMapper.insertSelective(follow);

        // 粉丝
        Optional<FansDetail> fansOptional = extFansMapper.selectOne(userId, followBy);
        if (fansOptional.isPresent()) {
            return;
        }
        Fans fans = new Fans();
        fans.setId(IdWorker.genNextId());
        fans.setFansUserId(userId);
        fans.setUserId(followBy);
        fans.setCreateTime(new Date());
        fansMapper.insertSelective(fans);
    }

    @Override
    public void unFollow(Long userId, Long followBy) {
        // 删除关注关系
        Optional<FollowDetail> optional = extFollowMapper.selectOne(followBy, userId);
        optional.ifPresent(
                // 先物理删除，防止数据过多
                e -> followMapper.deleteByPrimaryKey(e.getId())
        );

        // 删除粉丝关系
        Optional<FansDetail> fansOptional = extFansMapper.selectOne(userId, followBy);
        fansOptional.ifPresent(e -> {
            fansMapper.deleteByPrimaryKey(e.getId());
        });
    }

    @Override
    public List<FollowDetail> getFollows(Long userId, int offset, int limit) {
        return extFollowMapper.selectList(userId, offset, limit);
    }

    @Override
    public List<FansDetail> getFans(Long userId, int offset, int limit) {
        return extFansMapper.selectList(userId, offset, limit);
    }
}

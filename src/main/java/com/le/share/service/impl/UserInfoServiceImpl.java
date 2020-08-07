package com.le.share.service.impl;

import com.le.share.client.WxClient;
import com.le.share.client.po.CodeSession;
import com.le.share.mapper.UserInfoMapper;
import com.le.share.mapper.ext.ExtUserInfoMapper;
import com.le.share.model.UserInfo;
import com.le.share.service.UserInfoService;
import com.le.share.util.IdWorker;
import com.le.share.vo.resp.WxSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by yinbohe.
 * Date 2020/4/30
 * Description
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private WxClient wxClient;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private ExtUserInfoMapper extUserInfoMapper;

    @Override
    public WxSession login(String code, UserInfo userInfo) {
        // TODO mock
        CodeSession codeSession = wxClient.code2Session(code);
//        CodeSession codeSession = new CodeSession();
//        codeSession.setOpenid("fdaswewe2342fsafsdfas");
//        codeSession.setUnionid("failsp2342342sdfs982s");


        userInfo.setOpenid(codeSession.getOpenid());
        userInfo.setSessionKey(codeSession.getSessionKey());
        userInfo.setUnionid(codeSession.getUnionid());

        // 判断用户是否已经存在
        Optional<UserInfo> optional = extUserInfoMapper.selectByOpenId(codeSession.getOpenid());
        if (optional.isPresent()) {
            userInfo.setId(optional.get().getId());
            userInfoMapper.updateByPrimaryKey(userInfo);
        } else {
            userInfo.setId(IdWorker.genNextId());
            userInfoMapper.insertSelective(userInfo);
        }
        return new WxSession(userInfo.getId(), codeSession.getOpenid(), codeSession.getUnionid());
    }

    @Override
    public void update(UserInfo userInfo) {
        // 每次用户打开小程序后重新更新用户信息（openid登陆有存储在小程序本地）
        Optional<UserInfo> optional = extUserInfoMapper.selectByOpenId(userInfo.getOpenid());
        optional.ifPresent(e -> {
            userInfo.setId(e.getId());
            userInfoMapper.updateByPrimaryKey(userInfo);
        });
    }

}

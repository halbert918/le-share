package com.le.share.service;

import com.le.share.model.UserInfo;
import com.le.share.vo.resp.WxSession;

/**
 * Created by yinbohe.
 * Date 2020/5/3
 * Description
 */
public interface UserInfoService {
    /**
     * 微信登陆.
     * @param code
     * @param userInfo
     * @return
     */
    WxSession login(String code, UserInfo userInfo);

    /**
     * 更新用户.
     * @param userInfo
     */
    void update(UserInfo userInfo);

}

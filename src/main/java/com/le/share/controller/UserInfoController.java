package com.le.share.controller;

import com.le.share.model.UserInfo;
import com.le.share.service.UserInfoService;
import com.le.share.vo.req.LoginReq;
import com.le.share.vo.resp.WxSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yinbohe.
 * Date 2020/4/18
 * Description
 */
@RestController
@Api(value = "用户信息")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "用户登陆", notes = "用户登陆", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/user/login")
    public WxSession login(@RequestBody LoginReq req) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(req, userInfo);
        return userInfoService.login(req.getCode(), userInfo);
    }

    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PutMapping("/user/{openid}")
    public void update(@PathVariable String openid, @RequestBody LoginReq req) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(req, userInfo);
        userInfo.setOpenid(openid);
        userInfoService.update(userInfo);
    }

}

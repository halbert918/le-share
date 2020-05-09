package com.le.share.client.impl;

import com.le.share.client.WxClient;
import com.le.share.client.po.CodeSession;
import com.le.share.common.enums.LeResultEnum;
import com.le.share.exception.LeShareException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WxClientImpl implements WxClient {

    @Value("wx.jscode2session-url")
    private String code2SessionUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CodeSession code2Session(String jsCode) {
        CodeSession codeSession = restTemplate.getForObject(
                String.format(code2SessionUrl, jsCode), CodeSession.class
        );
        if (codeSession == null || codeSession.getErrcode() != 0) {
            throw new LeShareException(LeResultEnum.REMOTE_CALL_WX_FAIL, codeSession.getErrmsg());
        }
        return codeSession;
    }
}

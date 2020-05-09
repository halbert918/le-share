package com.le.share.client;

import com.le.share.client.po.CodeSession;

public interface WxClient {
    /**
     * 登录凭证校验.
     * @param jsCode
     * @return
     */
    CodeSession code2Session(String jsCode);

}

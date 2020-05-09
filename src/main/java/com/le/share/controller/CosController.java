package com.le.share.controller;

import com.tencent.cloud.CosStsClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yinbohe.
 * Date 2020/5/5
 * Description
 */
@RestController
@Api(value = "cos")
public class CosController {

    @Value("${cos.api.secret-id}")
    private String secretId;

    @Value("${cos.api.secret-key}")
    private String secretKey;

    @Value("${cos.api.duration-seconds: 1800}")
    private int durationSeconds;

    @Value("${cos.api.bucket}")
    private String bucket;

    @Value("${cos.api.region}")
    private String region;

    @ApiOperation(value = "获取cos临时密钥", notes = "获取cos临时密钥", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/api/tmp-secret")
    public Map<String, Object> getTmpSecret() {
        TreeMap<String, Object> config = new TreeMap<>();

        try {
            // 替换为您的 SecretId
            config.put("SecretId",  secretId);
            // 替换为您的 SecretKey
            config.put("SecretKey", secretKey);

            // 临时密钥有效时长，单位是秒，默认1800秒，最长可设定有效期为7200秒
            config.put("durationSeconds", 1800);

            // 换成您的 bucket
            config.put("bucket", bucket);
            // 换成 bucket 所在地区
            config.put("region", region);

            // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径，例子：a.jpg 或者 a/* 或者 * 。
            // 如果填写了“*”，将允许用户访问所有资源；除非业务需要，否则请按照最小权限原则授予用户相应的访问权限范围。
            config.put("allowPrefix", "*");

            // 密钥的权限列表。简单上传、表单上传和分片上传需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[] {
                    // 简单上传
                    "name/cos:PutObject",
                    // 表单上传、小程序上传
                    "name/cos:PostObject",
                    // 分片上传
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload"
            };
            config.put("allowActions", allowActions);

            JSONObject credential = CosStsClient.getCredential(config);
            credential.put("bucket", bucket);
            credential.put("region", region);

            Long currentTimeSeconds = System.currentTimeMillis() / 1000;
            credential.put("startTime", currentTimeSeconds);
            credential.put("expiredTime", currentTimeSeconds + 900);
            //成功返回临时密钥信息，如下打印密钥信息
            System.out.println(credential);
            return credential.toMap();
        } catch (Exception e) {
            //失败抛出异常
            throw new IllegalArgumentException("no valid secret !");
        }
    }


}

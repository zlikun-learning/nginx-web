package com.zlikun.kong;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * API测试：在Kong 0.13.0.及以后的版本不再推荐使用，推荐迁移到service部分
 * https://getkong.org/docs/0.13.x/admin-api/#api-object
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/4/19 20:50
 */
@Slf4j
public class ApiTest extends KongBase {

    /**
     * 添加API
     * https://getkong.org/docs/0.13.x/admin-api/#add-api
     */
    @Test
    public void add() throws IOException {

        // 请求体
        RequestBody body = new FormBody.Builder()
                // API名称，可选
                .add("name", "api_user_details")
                // hosts / uris / methods，三个参数至少有一个不能为空
                .add("hosts", "api.zlikun.com")
                .add("uris", "/details")
                .add("methods", "GET")
                // The base target URL that points to your API server.
                .add("upstream_url", "http://api.zlikun.com")
                // 请求重试次数，默认：5
                .add("retries", "3")
                .add("https_only", "false")
                .build();

        // POST
        Request request = new Request.Builder()
                .url(admin + "/apis/")
                .post(body)
                .build();

        // 执行请求，如果重复执行，返回：HTTP_1_1 409 Conflict 状态码
        Response response = client.newCall(request).execute();

        // 打印响应
        // HTTP_1_1 201 Created
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "created_at": 1524171389976,
            "strip_uri": true,
            "id": "05950102-affb-4b4b-ab7b-22497c99ee58",
            "hosts": [
                "api.zlikun.com"
            ],
            "name": "api_user_details",
            "methods": [
                "GET"
            ],
            "http_if_terminated": false,
            "preserve_host": false,
            "upstream_url": "http:\/\/api.zlikun.com",
            "uris": [
                "\/details"
            ],
            "upstream_send_timeout": 60000,
            "upstream_connect_timeout": 60000,
            "upstream_read_timeout": 60000,
            "retries": 3,
            "https_only": false
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

}

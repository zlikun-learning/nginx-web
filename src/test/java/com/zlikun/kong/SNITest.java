package com.zlikun.kong;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * SNI后台API测试，暂未研究其实际使用价值
 * https://getkong.org/docs/0.13.x/admin-api/#sni-objects
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/4/20 13:59
 */
@Slf4j
public class SNITest extends KongBase {

    /**
     * https://getkong.org/docs/0.13.x/admin-api/#add-sni
     */
    @Test
    public void add() throws IOException {

        // 尚未测试
        RequestBody body = new FormBody.Builder()
                .add("name", "")
                .add("ssl_certificate_id", "")
                .build();

        Request request = new Request.Builder()
                .url(admin + "/snis/")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 201 Created
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------

        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    // 略 。。。

}

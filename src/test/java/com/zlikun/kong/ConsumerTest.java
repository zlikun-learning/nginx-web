package com.zlikun.kong;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * Consumer后台API测试
 * https://getkong.org/docs/0.13.x/admin-api/#consumer-object
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/4/19 21:00
 */
@Slf4j
public class ConsumerTest extends KongBase {

    /**
     * 增加Consumer
     * https://getkong.org/docs/0.13.x/admin-api/#create-consumer
     */
    @Test
    public void add() throws IOException {

        RequestBody body = new FormBody.Builder()
                // Consumer名称，与custom_id至少有一个不能为空
                .add("username", "consumer_zlikun")
//                .add("custom_id", "")
                .build();

        Request request = new Request.Builder()
                .url(admin + "/consumers/")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 201 Created
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {"created_at":1524143018000,"username":"consumer_zlikun","id":"2d3100c1-91c7-438d-9937-51c2365ef35a"}
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * 查询Consumer
     * https://getkong.org/docs/0.13.x/admin-api/#retrieve-consumer
     */
    @Test
    public void retrieve() throws IOException {

        String username = "consumer_zlikun";
        Request request = new Request.Builder()
                .url(admin + "/consumers/" + username)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 200 OK
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {"created_at":1524143018000,"username":"consumer_zlikun","id":"2d3100c1-91c7-438d-9937-51c2365ef35a"}
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * 遍历Consumer
     * https://getkong.org/docs/0.13.x/admin-api/#list-consumers
     */
    @Test
    public void list() throws IOException {

        Request request = new Request.Builder()
                .url(admin + "/consumers/" + "?size=3")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 200 OK
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {"total":1,"data":[{"created_at":1524143018000,"username":"consumer_zlikun","id":"2d3100c1-91c7-438d-9937-51c2365ef35a"}]}
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * 更新Consumer
     * https://getkong.org/docs/0.13.x/admin-api/#update-consumer
     */
    @Test
    public void update() throws IOException {

        RequestBody body = new FormBody.Builder()
                .add("custom_id", "consumer_10000")
                .build();

        String username = "consumer_zlikun";
        Request request = new Request.Builder()
                .url(admin + "/consumers/" + username)
                .patch(body)
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 200 OK
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {"custom_id":"consumer_10000","created_at":1524143018000,"username":"consumer_zlikun","id":"2d3100c1-91c7-438d-9937-51c2365ef35a"}
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * 更新或创建Consumer
     * https://getkong.org/docs/0.13.x/admin-api/#update-or-create-consumer
     */
    @Test
    public void update_or_create() throws IOException {

        RequestBody body = new FormBody.Builder()
                .add("username", "consumer_zlikun")
                .add("custom_id", "consumer.10000.0")
                .build();

        Request request = new Request.Builder()
                .url(admin + "/consumers/")
                .put(body)
                .build();

        Response response = client.newCall(request).execute();

        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * 删除Consumer
     * https://getkong.org/docs/0.13.x/admin-api/#delete-consumer
     */
    @Test
    public void delete() throws IOException {

        String username = "consumer_zlikun";
        Request request = new Request.Builder()
                .url(admin + "/consumers/" + username)
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 204 No Content
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        log.info(response.body().string());

    }

}

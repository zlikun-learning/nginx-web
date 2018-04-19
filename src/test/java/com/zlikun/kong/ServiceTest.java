package com.zlikun.kong;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * 服务API测试
 * https://getkong.org/docs/0.13.x/admin-api/#service-object
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/4/19 17:33
 */
@Slf4j
public class ServiceTest extends KongBase {

    /**
     * 添加服务
     * https://getkong.org/docs/0.13.x/admin-api/#add-service
     */
    @Test
    public void add() throws IOException {

        // 请求体
        RequestBody body = new FormBody.Builder()
                // 服务名称，可选
                .add("name", "api_users")
                // 由：protocol + host + port + path 组成，这四个参数与url至少有一方必填
                .add("url", api + "/users")
                // 请求重试次数，默认：5
                .add("retries", "3")
                // 可选，默认：60000
                .add("connect_timeout", "60000")
                // 可选，默认：60000
                .add("write_timeout", "60000")
                // 可选，默认：60000
                .add("read_timeout", "60000")
                .build();

        // POST
        Request request = new Request.Builder()
                .url(admin + "/services/")
                .post(body)
                .build();

        // 执行请求，如果重复执行，返回：HTTP_1_1 409 Conflict 状态码
        Response response = client.newCall(request).execute();

        // 打印响应
        // HTTP_1_1 201 Created
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "host": "api.zlikun.com",
            "created_at": 1524103610,
            "connect_timeout": 60000,
            "id": "a5e98b77-5d26-4175-b1a5-7ba55834b1d3",
            "protocol": "http",
            "name": "api_users",
            "read_timeout": 60000,
            "port": 80,
            "path": "\/users",
            "updated_at": 1524103610,
            "retries": 3,
            "write_timeout": 60000
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * 查询服务
     * https://getkong.org/docs/0.13.x/admin-api/#retrieve-service
     * @throws IOException
     */
    @Test
    public void retrieve() throws IOException {

        // 通过服务名或服务ID查询
        String serviceName = "service_users";
        // 还支持通过路由查询服务：/routes/{route_id}/service，route_id非空
        Request request = new Request.Builder()
                .url(admin + "/services/" + serviceName)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 200 OK
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "host": "api.zlikun.com",
            "created_at": 1524103610,
            "connect_timeout": 60000,
            "id": "a5e98b77-5d26-4175-b1a5-7ba55834b1d3",
            "protocol": "http",
            "name": "service_users",
            "read_timeout": 60000,
            "port": 80,
            "path": "\/users",
            "updated_at": 1524103610,
            "retries": 3,
            "write_timeout": 60000
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());
    }

    /**
     * 列表查询
     * https://getkong.org/docs/0.13.x/admin-api/#list-services
     * @throws IOException
     */
    @Test
    public void list() throws IOException {

        // 需要指定分页参数：offset和size，
        // offset表示分页偏移量，可选，目前尚不清楚用法(使用一个数值会报500错误)
        // size默认值：100，最大：1000
        Request request = new Request.Builder()
                .url(admin + "/services/" + "?size=3")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 200 OK
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "next": null,
            "data": [
                {
                    "host": "api.zlikun.com",
                    "created_at": 1524100547,
                    "connect_timeout": 60000,
                    "id": "84af8853-5151-4a5e-8078-ad061fbfd157",
                    "protocol": "http",
                    "name": "zlikun",
                    "read_timeout": 60000,
                    "port": 80,
                    "path": "\/",
                    "updated_at": 1524100547,
                    "retries": 5,
                    "write_timeout": 60000
                },
                {
                    "host": "api.zlikun.com",
                    "created_at": 1524103610,
                    "connect_timeout": 60000,
                    "id": "a5e98b77-5d26-4175-b1a5-7ba55834b1d3",
                    "protocol": "http",
                    "name": "service_users",
                    "read_timeout": 60000,
                    "port": 80,
                    "path": "\/users",
                    "updated_at": 1524103610,
                    "retries": 3,
                    "write_timeout": 60000
                }
            ]
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());
    }

    /**
     * 更新服务
     * https://getkong.org/docs/0.13.x/admin-api/#update-service
     * @throws IOException
     */
    @Test
    public void update() throws IOException {

        // 本例中仅修改重试次数，其它参数参考：#add()方法中示例用法
        RequestBody body = new FormBody.Builder()
                .add("retries", "2")
                .build();

        // 通过服务名或服务ID更新，注意请求方法是：PATCH
        String serviceName = "service_users";
        // 也可以通过路由ID更新：/routes/{route_id}/service，实际上路由与服务是多对一的关系，所以多端可以惟一确定一端
        Request request = new Request.Builder()
                .url(admin + "/services/" + serviceName)
                .patch(body)
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 200 OK
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "host": "api.zlikun.com",
            "created_at": 1524103610,
            "connect_timeout": 60000,
            "id": "a5e98b77-5d26-4175-b1a5-7ba55834b1d3",
            "protocol": "http",
            "name": "service_users",
            "read_timeout": 60000,
            "port": 80,
            "path": "\/users",
            "updated_at": 1524104926,
            "retries": 2,
            "write_timeout": 60000
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());
    }

    /**
     * 删除服务，如果服务已被路由引用，则无法删除，需要先删除路由
     * https://getkong.org/docs/0.13.x/admin-api/#delete-service
     * @throws IOException
     */
    @Test
    public void delete() throws IOException {

        // 通过服务名或服务ID更新，注意请求方法是：DELETE
        String serviceName = "service_users";
        Request request = new Request.Builder()
                .url(admin + "/services/" + serviceName)
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 204 No Content
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        无响应
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());
    }

}

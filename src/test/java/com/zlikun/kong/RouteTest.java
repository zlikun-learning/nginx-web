package com.zlikun.kong;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * 路由API测试
 * https://getkong.org/docs/0.13.x/admin-api/#route-object
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/4/19 18:33
 */
@Slf4j
public class RouteTest extends KongBase {

    /**
     * 添加路由
     * https://getkong.org/docs/0.13.x/admin-api/#add-route
     */
    @Test
    public void add() throws IOException {

        // 请求体
        RequestBody body = new FormBody.Builder()
                .add("protocols[]", "http")
                .add("protocols[]", "https")
                .add("methods[]", "GET")
                .add("methods[]", "POST")
                .add("hosts[]", "api.zlikun.com")
                .add("paths[]", "/users")
                .add("strip_path", "true")
                .add("preserve_host", "false")
                // 关联的服务ID
                .add("service.id", "5cf0494f-ded9-470c-82ef-07059b1d5c8e")
                .build();

        // POST
        Request request = new Request.Builder()
                .url(admin + "/routes/")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 201 Created
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "created_at": 1524112343,
            "strip_path": true,
            "hosts": [
                "api.zlikun.com"
            ],
            "preserve_host": false,
            "regex_priority": 0,
            "updated_at": 1524112343,
            "paths": [
                "\/users"
            ],
            "service": {
                "id": "5cf0494f-ded9-470c-82ef-07059b1d5c8e"
            },
            "methods": [
                "GET",
                "POST"
            ],
            "protocols": [
                "http",
                "https"
            ],
            "id": "8358b021-6900-474c-8e88-83411b2fa7ae"
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * 查询路由
     * https://getkong.org/docs/0.13.x/admin-api/#retrieve-route
     */
    @Test
    public void retrieve() throws IOException {

        String routeId = "8358b021-6900-474c-8e88-83411b2fa7ae";
        Request request = new Request.Builder()
                .url(admin + "/routes/" + routeId)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 200 OK
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "created_at": 1524112343,
            "strip_path": true,
            "hosts": [
                "api.zlikun.com"
            ],
            "preserve_host": false,
            "regex_priority": 0,
            "updated_at": 1524112343,
            "paths": [
                "\/users"
            ],
            "service": {
                "id": "5cf0494f-ded9-470c-82ef-07059b1d5c8e"
            },
            "methods": [
                "GET",
                "POST"
            ],
            "protocols": [
                "http",
                "https"
            ],
            "id": "8358b021-6900-474c-8e88-83411b2fa7ae"
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * 遍历路由
     * https://getkong.org/docs/0.13.x/admin-api/#list-routes
     */
    @Test
    public void list() throws IOException {

        Request request = new Request.Builder()
                .url(admin + "/routes" + "?size=3")
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
                    "created_at": 1524101388,
                    "strip_path": true,
                    "hosts": [
                        "api.zlikun.com"
                    ],
                    "preserve_host": false,
                    "regex_priority": 0,
                    "updated_at": 1524101388,
                    "paths": null,
                    "service": {
                        "id": "84af8853-5151-4a5e-8078-ad061fbfd157"
                    },
                    "methods": null,
                    "protocols": [
                        "http",
                        "https"
                    ],
                    "id": "2fef1a5d-eb75-414f-9c96-aca3990897d3"
                },
                {
                    "created_at": 1524112343,
                    "strip_path": true,
                    "hosts": [
                        "api.zlikun.com"
                    ],
                    "preserve_host": false,
                    "regex_priority": 0,
                    "updated_at": 1524112343,
                    "paths": [
                        "\/users"
                    ],
                    "service": {
                        "id": "5cf0494f-ded9-470c-82ef-07059b1d5c8e"
                    },
                    "methods": [
                        "GET",
                        "POST"
                    ],
                    "protocols": [
                        "http",
                        "https"
                    ],
                    "id": "8358b021-6900-474c-8e88-83411b2fa7ae"
                }
            ]
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * 遍历路由
     * https://getkong.org/docs/0.13.x/admin-api/#list-routes-associated-to-a-service
     */
    @Test
    public void list_to_service() throws IOException {

        String serviceName = "service_users";
        Request request = new Request.Builder()
                .url(admin + "/services/" + serviceName + "/routes")
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
                    "created_at": 1524112343,
                    "strip_path": true,
                    "hosts": [
                        "api.zlikun.com"
                    ],
                    "preserve_host": false,
                    "regex_priority": 0,
                    "updated_at": 1524112343,
                    "paths": [
                        "\/users"
                    ],
                    "service": {
                        "id": "5cf0494f-ded9-470c-82ef-07059b1d5c8e"
                    },
                    "methods": [
                        "GET",
                        "POST"
                    ],
                    "protocols": [
                        "http",
                        "https"
                    ],
                    "id": "8358b021-6900-474c-8e88-83411b2fa7ae"
                }
            ]
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * 更新路由
     * https://getkong.org/docs/0.13.x/admin-api/#update-route
     */
    @Test
    public void update() throws IOException {

        // 请求体
        RequestBody body = new FormBody.Builder()
                .add("paths[]", "/users")
                .add("paths[]", "/v1/users")
                .add("methods[]", "GET")
                .add("hosts[]", "api.zlikun.com")
                .build();

        String routeId = "8358b021-6900-474c-8e88-83411b2fa7ae";
        Request request = new Request.Builder()
                .url(admin + "/routes/" + routeId)
                .patch(body)
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 200 OK
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "created_at": 1524112343,
            "strip_path": true,
            "hosts": [
                "api.zlikun.com"
            ],
            "preserve_host": false,
            "regex_priority": 0,
            "updated_at": 1524113035,
            "paths": [
                "\/users",
                "\/v1\/users"
            ],
            "service": {
                "id": "5cf0494f-ded9-470c-82ef-07059b1d5c8e"
            },
            "methods": [
                "GET"
            ],
            "protocols": [
                "http",
                "https"
            ],
            "id": "8358b021-6900-474c-8e88-83411b2fa7ae"
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * 删除路由
     * https://getkong.org/docs/0.13.x/admin-api/#delete-route
     */
    @Test
    public void delete() throws IOException {

        String routeId = "8358b021-6900-474c-8e88-83411b2fa7ae";
        Request request = new Request.Builder()
                .url(admin + "/routes/" + routeId)
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 204 No Content
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        删除时无响应消息休
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

}

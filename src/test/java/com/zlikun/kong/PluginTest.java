package com.zlikun.kong;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * Plugin后台API测试
 * https://getkong.org/docs/0.13.x/admin-api/#plugin-object
 * https://konghq.com/plugins/
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/4/20 13:30
 */
@Slf4j
public class PluginTest extends KongBase {

    /**
     * https://getkong.org/docs/0.13.x/admin-api/#add-plugin
     */
    @Test
    public void add() throws IOException {

        // 可选插件： https://konghq.com/plugins/
        RequestBody body = new FormBody.Builder()
                // 插件名称，本例使用访问频率控制插件
                // https://getkong.org/plugins/rate-limiting/
                .add("name", "rate-limiting")
                // consumer
                .add("consumer_id", "fb344495-6aee-4909-8213-821d1dbd04bd")
                // 插件属性，以config.为前缀
                .add("config.second", "2")  // 每秒最多2次，仅测试
                .add("config.minute", "5")  // 每分钟最多5次，仅测试
                .build();

        Request request = new Request.Builder()
                .url(admin + "/plugins/")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 201 Created
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "created_at": 1524202675000,
            "config": {
                "minute": 5,
                "policy": "cluster",
                "redis_timeout": 2000,
                "hide_client_headers": false,
                "second": 2,
                "limit_by": "consumer",
                "redis_port": 6379,
                "redis_database": 0,
                "fault_tolerant": true
            },
            "id": "0d0cba26-26b3-47b7-b37b-7334d8da4c2e",
            "name": "rate-limiting",
            "enabled": true,
            "consumer_id": "fb344495-6aee-4909-8213-821d1dbd04bd"
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * https://getkong.org/docs/0.13.x/admin-api/#retrieve-plugin
     */
    @Test
    public void retrieve() throws IOException {

        String pluginId = "0d0cba26-26b3-47b7-b37b-7334d8da4c2e";
        Request request = new Request.Builder()
                .url(admin + "/plugins/" + pluginId)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 200 OK
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "created_at": 1524202675000,
            "config": {
                "minute": 5,
                "policy": "cluster",
                "redis_timeout": 2000,
                "hide_client_headers": false,
                "second": 2,
                "limit_by": "consumer",
                "redis_port": 6379,
                "redis_database": 0,
                "fault_tolerant": true
            },
            "id": "0d0cba26-26b3-47b7-b37b-7334d8da4c2e",
            "name": "rate-limiting",
            "enabled": true,
            "consumer_id": "fb344495-6aee-4909-8213-821d1dbd04bd"
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * https://getkong.org/docs/0.13.x/admin-api/#list-all-plugins
     */
    @Test
    public void list() throws IOException {

        String pluginId = "0d0cba26-26b3-47b7-b37b-7334d8da4c2e";
        Request request = new Request.Builder()
                // offset 是一个对象，实际使用的是分页数据的ID
                .url(admin + "/plugins/" + "?size=3&offset=" + pluginId)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 200 OK
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "total": 1,
            "data": [
                {
                    "created_at": 1524202675000,
                    "config": {
                        "minute": 5,
                        "policy": "cluster",
                        "redis_timeout": 2000,
                        "hide_client_headers": false,
                        "second": 2,
                        "limit_by": "consumer",
                        "redis_port": 6379,
                        "redis_database": 0,
                        "fault_tolerant": true
                    },
                    "id": "0d0cba26-26b3-47b7-b37b-7334d8da4c2e",
                    "name": "rate-limiting",
                    "enabled": true,
                    "consumer_id": "fb344495-6aee-4909-8213-821d1dbd04bd"
                }
            ]
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * https://getkong.org/docs/0.13.x/admin-api/#update-plugin
     */
    @Test
    public void update() throws IOException {

        RequestBody body = new FormBody.Builder()
                .add("config.second", "3")
                .add("config.redis_host", "localhost")
                .build();

        String pluginId = "0d0cba26-26b3-47b7-b37b-7334d8da4c2e";
        Request request = new Request.Builder()
                .url(admin + "/plugins/" + pluginId)
                .patch(body)
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 200 OK
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "created_at": 1524202675000,
            "config": {
                "redis_database": 0,
                "policy": "cluster",
                "redis_host": "localhost",
                "redis_timeout": 2000,
                "limit_by": "consumer",
                "second": 3,
                "minute": 5,
                "redis_port": 6379,
                "hide_client_headers": false,
                "fault_tolerant": true
            },
            "id": "0d0cba26-26b3-47b7-b37b-7334d8da4c2e",
            "name": "rate-limiting",
            "enabled": true,
            "consumer_id": "fb344495-6aee-4909-8213-821d1dbd04bd"
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * https://getkong.org/docs/0.13.x/admin-api/#update-or-add-plugin
     */
    @Test
    public void update_or_create() throws IOException {

        RequestBody body = new FormBody.Builder()
                .add("name", "rate-limiting")
                .add("name", "rate-limiting")
                .add("consumer_id", "fb344495-6aee-4909-8213-821d1dbd04bd")
                .add("config.redis_host", "127.0.0.1")
                .add("config.minute", "7")
                .build();

        String pluginId = "0d0cba26-26b3-47b7-b37b-7334d8da4c2e";
        Request request = new Request.Builder()
                .url(admin + "/plugins/")
                .put(body)
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 409 Conflict
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {"name":"already exists with value 'rate-limiting'"}
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * https://getkong.org/docs/0.13.x/admin-api/#delete-plugin
     */
    @Test
    public void delete() throws IOException {

        String pluginId = "0d0cba26-26b3-47b7-b37b-7334d8da4c2e";
        Request request = new Request.Builder()
                .url(admin + "/plugins/" + pluginId)
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 204 No Content
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        log.info(response.body().string());

    }

    /**
     * https://getkong.org/docs/0.13.x/admin-api/#retrieve-enabled-plugins
     */
    @Test
    public void retrieve_enabled() throws IOException {

        Request request = new Request.Builder()
                .url(admin + "/plugins/enabled")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 200 OK
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "enabled_plugins": [
                "response-transformer",
                "correlation-id",
                "statsd",
                "jwt",
                "cors",
                "basic-auth",
                "key-auth",
                "ldap-auth",
                "http-log",
                "oauth2",
                "hmac-auth",
                "acl",
                "datadog",
                "tcp-log",
                "ip-restriction",
                "request-transformer",
                "file-log",
                "bot-detection",
                "loggly",
                "request-size-limiting",
                "syslog",
                "udp-log",
                "response-ratelimiting",
                "aws-lambda",
                "runscope",
                "rate-limiting",
                "request-termination"
            ]
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

    /**
     * https://getkong.org/docs/0.13.x/admin-api/#retrieve-plugin-schema
     */
    @Test
    public void retrieve_schema() throws IOException {

        String pluginName = "rate-limiting";
        Request request = new Request.Builder()
                .url(admin + "/plugins/schema/" + pluginName)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        // HTTP_1_1 200 OK
        log.info("{} {} {}", response.protocol().name(), response.code(), response.message());
        /* -----------------------------------------------------------------------------------
        {
            "self_check": "function",
            "fields": {
                "minute": {
                    "type": "number"
                },
                "policy": {
                    "type": "string",
                    "default": "cluster",
                    "enum": [
                        "local",
                        "cluster",
                        "redis"
                    ]
                },
                "month": {
                    "type": "number"
                },
                "redis_timeout": {
                    "default": 2000,
                    "type": "number"
                },
                "limit_by": {
                    "type": "string",
                    "default": "consumer",
                    "enum": [
                        "consumer",
                        "credential",
                        "ip"
                    ]
                },
                "hide_client_headers": {
                    "default": false,
                    "type": "boolean"
                },
                "second": {
                    "type": "number"
                },
                "day": {
                    "type": "number"
                },
                "redis_database": {
                    "default": 0,
                    "type": "number"
                },
                "year": {
                    "type": "number"
                },
                "redis_password": {
                    "type": "string"
                },
                "redis_host": {
                    "type": "string"
                },
                "redis_port": {
                    "default": 6379,
                    "type": "number"
                },
                "hour": {
                    "type": "number"
                },
                "fault_tolerant": {
                    "default": true,
                    "type": "boolean"
                }
            }
        }
        ----------------------------------------------------------------------------------- */
        log.info(response.body().string());

    }

}

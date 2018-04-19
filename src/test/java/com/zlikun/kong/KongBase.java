package com.zlikun.kong;

import okhttp3.OkHttpClient;

/**
 * 配合测试Kong网关
 * https://getkong.org/docs/
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/4/19 17:33
 */
public abstract class KongBase {

    protected OkHttpClient client = new OkHttpClient.Builder()
            .build();

    /**
     * Admin请求前缀
     */
    protected String admin = "http://admin.zlikun.com";

    /**
     * 代理网关请求前缀
     */
    protected String proxy = "http://proxy.zlikun.com";

    /**
     * Api请求前缀
     */
    protected String api = "http://api.zlikun.com";

}

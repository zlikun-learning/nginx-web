package com.zlikun.nginx;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * 配合测试防盗链
 * http://nginx.org/en/docs/http/ngx_http_secure_link_module.html
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/4/14 13:15
 */
@Slf4j
public class SecureLinkTest {

    @Test
    public void test() {

        // 注意这里取秒数，+ 60 表示效期为当前之后60秒内
        long expire = System.currentTimeMillis() / 1000 + 60;
        // 出于安全考虑应加盐，MD5后需要Base64加密
        String salt = "salt";
        // 这里MD5、Base64由commons-codec提供，这里的加密规则与Nginx中的规则必须一致
        String cipherText = Base64.encodeBase64URLSafeString(DigestUtils.md5(salt + "/nginx.tar.gz" + expire));

        // 生成下载链接地址
        // http://k.zlikun.com/nginx.tar.gz?st=fRKSuqWCR0lJQwFwlK2Yow&e=1523686533
        log.info("http://k.zlikun.com/nginx.tar.gz?st={}&e={}", cipherText, expire);

    }

}

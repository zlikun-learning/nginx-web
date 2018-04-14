package com.zlikun.nginx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/4/13 15:24
 */
@Slf4j
@RestController
public class DefaultController {

    @GetMapping("/")
    public ResponseEntity<String> index(HttpServletRequest request) {
        log.info("Hello, Nginx!");

        // 打印请求消息头
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            log.info("header - name = {}, value = {}", name, request.getHeader(name));
        }

        return ResponseEntity.ok("Hello, Nginx !");
    }

}

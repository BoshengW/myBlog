package com.springboot.restfulcrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class HelloController {


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/success")
    public String success(Map<String, Object> map) {
        // classpath:/templates/success.html
        map.put("hello", "你好");
        return "success";
    }
}

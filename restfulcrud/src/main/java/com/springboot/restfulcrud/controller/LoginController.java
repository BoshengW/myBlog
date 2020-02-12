package com.springboot.restfulcrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    //从参数上提交两个参数 username+password
    @PostMapping(value = "/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map, HttpSession session) {
        if(!StringUtils.isEmpty(username) && "123456".equals(password)) {
            //if login successfully, to avoid POST form resubmission, we can redirect to main page
            // put attribute in session for login success user
            session.setAttribute("loginUser", username);
            return "redirect:/main.html";
        } else {
            map.put("msg", "登陆失败");
            return "login";
        }

    }
}

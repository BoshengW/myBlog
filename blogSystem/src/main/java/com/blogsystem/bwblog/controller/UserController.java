package com.blogsystem.bwblog.controller;

import com.blogsystem.bwblog.model.User;
import com.blogsystem.bwblog.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class UserController {
    // 日志 log ?
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/add")
    public String addNewUser(@RequestParam String name, @RequestParam String password, User user) {
        // @ResponseBody return a String response not a view name
        long total = userRepository.count();
        user.setId((int) total);
        user.setPassword(password);
        user.setName(name);
        userRepository.save(user);
        return "index";
    }


    @GetMapping(path = "/login")
    public String login(@RequestParam String name, @RequestParam String password, Model model) {
        List<User> users = userRepository.findByName(name); // crud 默认find 为list
        // 如果数据库中未查到该账号:
        if (users.size()==0) {
            model.addAttribute("name","User name not found");
        } else {
            User user = users.get(0);
            if (user.getPassword().equals(password)) {
                model.addAttribute("name", user.getName());
            } else {
                model.addAttribute("name", "logging failed");
            }
        }
        return "index";
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam String name, @RequestParam String password, Model model) {
        List<User> users = userRepository.findByName(name);
        if (users.size()==0) {
            model.addAttribute("name","User name not found");
        } else {
            User user = users.get(0);
            if(user.getPassword().equals(password)) {
                model.addAttribute("name","delete " + name);
            } else {
                model.addAttribute("name","Password not found");
            }
        }
        return "index";
    }

    // display all user info
    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    // display login table
    @GetMapping(path="/")
    public String welcomePage(@RequestParam(name="name", required=false, defaultValue="World") String namel){
        return "index";
    }

}

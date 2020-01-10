package com.blogsystem.bwblog.controller;

import com.blogsystem.bwblog.dao.UserDao;
import com.blogsystem.bwblog.model.User;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class UserController {
    // 日志 log ?
    @Autowired
    private UserDao userDao;

    private int rowCnt;

    @GetMapping("/user")
    public String LogInMain() {
        List<User> list = userDao.getAllUser();
        rowCnt = list.size();
        return "index";
    }

    @PostMapping("/user/add")
    @ResponseBody
    public String SignUpAccount(@RequestBody String NewUserJsonStr) {
        /*
        NewUSerJsonStr = "{ name='XXX', password='YYYY' }"
        * */
        try{
            JSONObject NewUserJson = new JSONObject(NewUserJsonStr);
            if(!userDao.userExist(NewUserJson.getString("name"))) {
                User newAccount = new User();
                newAccount.setId(rowCnt++);
                newAccount.setPassword(NewUserJson.getString("password"));
                newAccount.setName(NewUserJson.getString("name"));
                // add new account into database
                userDao.addAUser(newAccount);
                return "Add Success";

            } else {
                return "Username exist";
            }

        } catch(Exception Issue) {
            return "Process Error, try again";
        }
    }

    @PostMapping("/user/login")
    @ResponseBody
    public String LoginAccount(@RequestBody String LoginUserJsonStr) {
        JSONObject LoginUserJson = new JSONObject(LoginUserJsonStr);
        // check if username exist
        if(userDao.userExist(LoginUserJson.getString("name"))) {
            User userInfoInDB = userDao.getUserByName(LoginUserJson.getString("name"));
            String trurPW = userInfoInDB.getPassword();
            String yourPW = LoginUserJson.getString("password");
            if(trurPW.equals(yourPW)) {
              return "Success";
            }
            return "Username or Password not correct";

        } else {
            return "Username not found";
        }

    }


}

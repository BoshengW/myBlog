package com.benson.ajaxdataconnection.controller;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AjaxController {

//    JSONArray jsonList = new JSONArray();
    ArrayList jsonList = new ArrayList<>();
    private int init = 0;
    @GetMapping("/")
    public String mainWin() {
        return "Ajax";
    }

    @GetMapping("/orders")
    @ResponseBody //if you want to return a object by @Controller you need to @Responsebody also
    public List orders() {
        JSONObject json1 = new JSONObject();
        JSONObject json2 = new JSONObject();
        json1.put("id",1);
        json1.put("name","Coffee");
        json1.put("price","$25");

        json2.put("id",2);
        json2.put("name","Bolba");
        json2.put("price","$36");

        if(init==0) {
            jsonList.add(json1.toString());
            jsonList.add(json2.toString());
            init++;
        }
        System.out.println(jsonList);

        return jsonList;

    }

    @PostMapping("/orders")
    @ResponseBody
    public String addNewOrder(@RequestBody String newOrderJson){
        JSONObject json3 = new JSONObject(newOrderJson);
        json3.put("id",3);
        return json3.toString();
    }

}

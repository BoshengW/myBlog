package com.blogsystem.bwblog.controller;

/* This controller is used for controll "Tag" manage pages
* */

import com.blogsystem.bwblog.dao.TagDao;
import com.blogsystem.bwblog.model.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class TagController {
    @Autowired
    private TagDao tagDao;

    @GetMapping("/tag")
    public String MainWindowOfTag() {


        return "TagWindow";
    }

    @GetMapping("/tag/all")
    @ResponseBody
    public List<String> DisplayTags() {
        // they give you a list of json
        List<String> TagList = tagDao.getAllTag();

        return TagList;
    }
}

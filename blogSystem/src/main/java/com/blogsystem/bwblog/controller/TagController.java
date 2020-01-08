package com.blogsystem.bwblog.controller;

/* This controller is used for control "Tag" manage pages
* */
import com.blogsystem.bwblog.dao.TagDao;
import com.blogsystem.bwblog.model.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;

@Controller
public class TagController {

    private int rowcnt;

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
        this.rowcnt = TagList.size();

        return TagList;
    }

    @PostMapping("/tag/add")
    @ResponseBody
    public String AddNewtag(@RequestBody String TagJsonString) {
        // run the application
        try{
            JSONObject newTagJson = new JSONObject(TagJsonString);
            Tag newTag = new Tag();
            int curRow = this.rowcnt;

            newTag.setName((String) newTagJson.get("tag_name"));
            newTag.setDescription((String) newTagJson.get("description"));
            newTag.setId(curRow+1);
            // add a new tag
            // need to check SQL exception
            tagDao.addNewTag(newTag);
            // if no exception then increase row id
            this.rowcnt++;
            return TagJsonString;

        } catch(Exception Issue) {
            System.out.println("This Tag already exists");
            return "Nothing update";
        }

    }

}

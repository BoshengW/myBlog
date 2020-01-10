package com.blogsystem.bwblog.controller;

/* This controller is used for control "Tag" manage pages
* */
import com.blogsystem.bwblog.dao.TagDao;
import com.blogsystem.bwblog.model.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TagController {

    private int rowcnt;

    @Autowired
    private TagDao tagDao;

    @GetMapping("/")
    public String Main() {
        return "table";
    }

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

    @DeleteMapping("tag/delete")
    @ResponseBody
    public String DeleteTag(@RequestBody String DelTagJson) {
        // convert String json into json object
        JSONObject delTagJson = new JSONObject(DelTagJson);
        String delTagName = (String) delTagJson.get("tag_name");
        tagDao.deleteByName(delTagName);

        return "Delete Success!";
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

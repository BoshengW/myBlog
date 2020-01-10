package com.blogsystem.bwblog.controller;

/* This controller is used for control "Tag" manage pages
* */
import com.blogsystem.bwblog.dao.TagDao;
import com.blogsystem.bwblog.model.Tag;
import com.blogsystem.bwblog.toolfunction.JsonCvtOperaiton;
import org.json.JSONArray;
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
        try{
            JSONObject delTagJson = new JSONObject(DelTagJson);
            String delTagName = (String) delTagJson.get("tag_name");
            tagDao.deleteByName(delTagName);

            return "Delete Success";

        } catch(Exception Issue) {
            System.out.println("Cannot Delete tag");
            return "Delete Failed";
        }

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
    @PostMapping("/tag/update")
    @ResponseBody
    public String editTag(@RequestBody String TagJsonString) {
        try{

            // extract JSON Array string from forntend (by JSONArray())
            JSONArray jsonTag = new JSONArray(TagJsonString);
            JSONObject oldTagJson = (JSONObject) jsonTag.get(0);
            JSONObject newTagJson = (JSONObject) jsonTag.get(1);


            // check if tagname is changed
            if(oldTagJson.get("tag_name").equals(newTagJson.get("tag_name"))) {
                // in this case only description change
                System.out.println("same");
                Tag updateTag = tagDao.getTagByName((String) oldTagJson.get("tag_name"));

                updateTag.setDescription((String) newTagJson.get("description"));
                tagDao.updateTag(updateTag);
            } else {
                // if tag_name is different, then we need to remove old one and create a new row
                System.out.println("diff");
                Tag oldTag = tagDao.getTagByName((String) oldTagJson.get("tag_name"));
                // make a new tag object with old id
                Tag newTag = new Tag();
                newTag.setId(oldTag.getId());
                newTag.setName((String) newTagJson.get("tag_name"));
                newTag.setDescription((String) newTagJson.get("description"));

                // delete old one
                tagDao.deleteByName((String) oldTagJson.get("tag_name"));
                tagDao.addNewTag(newTag);

            }
            return "Update Success";

        } catch(Exception Issue) {
            System.out.println("Cannot Update tag or TagName conflict");
            return "Update Failed";
        }

    }

    @PostMapping("/tag/search")
    @ResponseBody
    public String searchTag(@RequestBody String TagJsonString) {
        try{
            JSONObject jsonTag = new JSONObject(TagJsonString);
            Tag searchTag = tagDao.getTagByName((String) jsonTag.get("tag_name"));
            return JsonCvtOperaiton.CvtTag2Json(searchTag);

        } catch(Exception Issue) {
            System.out.println("Cannot find this tag");
            return "Search Failed";
        }
    }

}

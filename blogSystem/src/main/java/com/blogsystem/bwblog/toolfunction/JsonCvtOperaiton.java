package com.blogsystem.bwblog.toolfunction;

import com.blogsystem.bwblog.model.Tag;
import org.json.JSONObject;

public class JsonCvtOperaiton {

    public static String CvtTag2Json(Tag tag) {
        JSONObject newTag = new JSONObject();
        newTag.put("id",tag.getId());
        newTag.put("tag_name", tag.getName());
        newTag.put("description", tag.getDescription());
        return newTag.toString();
    }
}

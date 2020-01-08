package com.blogsystem.bwblog.dao;

import com.blogsystem.bwblog.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagDao {

    // get all tag information from database
    public List<String> getAllTag(); // return list of tag with a json String
    public Tag getTagByName(String name);
    public String deleteByName(String name);
    public String addNewTag(Tag tag);
    public Tag updateTag(Tag tag);

}

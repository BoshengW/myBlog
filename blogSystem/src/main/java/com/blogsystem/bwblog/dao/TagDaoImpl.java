package com.blogsystem.bwblog.dao;

import com.blogsystem.bwblog.mapper.TagMapper;
import com.blogsystem.bwblog.model.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TagDaoImpl implements TagDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getAllTag() {
        String sql = "select * from tag;";
        // queryForList return maps in a list
        List<Tag> rsltList = jdbcTemplate.query(sql,new TagMapper());
        ArrayList jsonList = new ArrayList();
        for (int i=0;i<rsltList.size();i++) {
            JSONObject TagJsonEachRow = new JSONObject();
            TagJsonEachRow.put("id",rsltList.get(i).getId());
            TagJsonEachRow.put("name",rsltList.get(i).getName());
            TagJsonEachRow.put("description",rsltList.get(i).getDescription());
            System.out.println(TagJsonEachRow.toString());
            jsonList.add(TagJsonEachRow.toString());
        }
        return jsonList;
    }

    @Override
    public Tag getTagByName(String name) {
        String sql = "select * from tag where name=?;";
        // if you define object type in RowMapper then queryForObject can return the object type you define
        Tag rsltTag = jdbcTemplate.queryForObject(sql, new TagMapper(),name);
        return rsltTag;
    }

    @Override
    public Tag deleteByName(String name) {

        Tag deleteTag = getTagByName(name);
        String sql = "delete from tag where name=?;";
        int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, name);
            }
        });

        return deleteTag;
    }


    @Override
    public Tag addNewTag(Tag tag) {
        String sql = "insert into tag values(?,?,?);";
        int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1,tag.getId());
                preparedStatement.setString(2, tag.getName());
                preparedStatement.setString(3,tag.getDescription());
            }
        });
        return tag;
    }

    @Override
    public Tag updateTag(Tag tag) {
        Tag orgTag = getTagByName(tag.getName());
        String sql = "update tag set id=?, description=? where name=?;";
        int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1,tag.getId());
                preparedStatement.setString(2,tag.getDescription());
                preparedStatement.setString(3, tag.getName());

            }
        });
        return orgTag;
    }
}
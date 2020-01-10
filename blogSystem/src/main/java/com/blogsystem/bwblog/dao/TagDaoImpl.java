package com.blogsystem.bwblog.dao;

import com.blogsystem.bwblog.mapper.TagMapper;
import com.blogsystem.bwblog.model.Tag;
import com.blogsystem.bwblog.toolfunction.JsonCvtOperaiton;
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
            TagJsonEachRow.put("tag_name",rsltList.get(i).getName());
            TagJsonEachRow.put("description",rsltList.get(i).getDescription());
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
    public String deleteByName(String name) {

        Tag deleteTag = getTagByName(name);
        String sql = "delete from tag where name=?;";
        int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, name);
            }
        });

        return JsonCvtOperaiton.CvtTag2Json(deleteTag);
    }


    @Override
    public String addNewTag(Tag tag) {
        String sql = "insert into tag values(?,?,?);";
        int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1,tag.getId());
                preparedStatement.setString(2, tag.getName());
                preparedStatement.setString(3,tag.getDescription());
            }
        });

        return JsonCvtOperaiton.CvtTag2Json(tag);
    }

    @Override
    public Tag updateTag(Tag tag) {
        String sql = "update tag set description=? where name=?;";
        int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, tag.getDescription());
                preparedStatement.setString(2, tag.getName());
            }
        });
        return tag;
    }
}

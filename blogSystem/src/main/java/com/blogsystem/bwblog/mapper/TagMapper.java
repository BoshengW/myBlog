package com.blogsystem.bwblog.mapper;

import com.blogsystem.bwblog.model.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// return Tag Object
public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        Tag newTag = new Tag();
        newTag.setId(resultSet.getInt("id"));
        newTag.setName(resultSet.getString("name"));
        newTag.setDescription(resultSet.getString("description"));
        return newTag;
    }
}

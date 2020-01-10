package com.blogsystem.bwblog.mapper;

import com.blogsystem.bwblog.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User newUser = new User();
        newUser.setId(resultSet.getInt("id"));
        newUser.setName(resultSet.getString("name"));
        newUser.setPassword(resultSet.getString("password"));
        return newUser;
    }
}

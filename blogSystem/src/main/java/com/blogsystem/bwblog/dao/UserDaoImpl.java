package com.blogsystem.bwblog.dao;

import com.blogsystem.bwblog.mapper.UserMapper;
import com.blogsystem.bwblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAllUser() {
        String sql = "select * from user;";
        List<User> allUser = jdbcTemplate.query(sql,new UserMapper());
        return allUser;
    }

    @Override
    public User addAUser(User user) {
        String sql = "insert into user values(?,?,?);";
        int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1,user.getId());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getPassword());
            }
        });
        return user;
    }

    @Override
    public User deleteUserByName(String name) {
        String sql = "delete from user where name=?;";
        User delUser = getUserByName(name);
        jdbcTemplate.update(sql,name);
        return delUser;
    }

    @Override
    public User updateUser(User user) {
        String sql = "update user set id=?, password=? where name=?;";
        int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getName());
            }
        });
        return user;
    }

    @Override
    public User getUserByName(String name) {
        String sql = "select * from user where name=?;";
        User sltUser = jdbcTemplate.queryForObject(sql, new UserMapper(), name);
        return sltUser;
    }

    @Override
    public boolean userExist(String username) {
        String sql = "select count(*) from user where name=?;";
        int row = jdbcTemplate.queryForObject(sql, new Object[] {username},Integer.class);
        if(row>0) {
            return true;
        }
        return false;
    }
}

package com.blogsystem.bwblog.dao;

import com.blogsystem.bwblog.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    // display
    public List<User> getAllUser();

    // add
    public User addAUser(User user);

    // delete
    public User deleteUserByName(String name);

    // change
    public User updateUser(User user);

    // search
    public User getUserByName(String name);

    // check if user exist
    public boolean userExist(String username);


}

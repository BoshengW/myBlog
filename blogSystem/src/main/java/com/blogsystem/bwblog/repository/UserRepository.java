package com.blogsystem.bwblog.repository;

import com.blogsystem.bwblog.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer> {
    // CrudRepository<data object, ID type>
    // since it inherit CrudRepository it have basic dao operation "add,delete,change,inquire"
    // also you can add your own method
    List<User> findByName(String name); // Crud will convert this into sql: select * from table where email='email'

}

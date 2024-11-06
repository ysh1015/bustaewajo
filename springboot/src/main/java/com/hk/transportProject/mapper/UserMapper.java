package com.hk.transportProject.mapper;

import com.hk.transportProject.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User getUserByUsername(@Param("username") String username);
    void insertUser(User user);
}

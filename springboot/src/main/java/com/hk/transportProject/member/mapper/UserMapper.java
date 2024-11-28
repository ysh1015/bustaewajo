package com.hk.transportProject.member.mapper;

import com.hk.transportProject.member.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User getUserByUsername(@Param("username") String username);
    void insertUser(User user);
}

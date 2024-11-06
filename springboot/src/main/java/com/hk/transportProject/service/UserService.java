package com.hk.transportProject.service;

import com.hk.transportProject.mapper.UserMapper;
import com.hk.transportProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public void registerUser(User user) {
        userMapper.insertUser(user);
    }
}

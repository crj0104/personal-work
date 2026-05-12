package com.foodorder.service;

import com.foodorder.bean.User;
import com.foodorder.repository.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User loginOrRegister(String phone, String password) {
        User existingUser = userMapper.findByPhone(phone);
        if (existingUser != null) {
            if (!existingUser.getPassword().equals(password)) {
                return null;
            }
            return existingUser;
        }
        User newUser = new User();
        newUser.setPhone(phone);
        newUser.setPassword(password);
        newUser.setRole("user");
        userMapper.insert(newUser);
        return newUser;
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }

    public List<User> findAllUsers() {
        return userMapper.findAll();
    }
}

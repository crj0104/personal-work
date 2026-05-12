package com.foodorder.repository;

import org.apache.ibatis.annotations.*;

import com.foodorder.bean.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);

    @Select("SELECT * FROM users WHERE phone = #{phone}")
    User findByPhone(String phone);

    @Insert("INSERT INTO users(phone, password, role, created_at) VALUES(#{phone}, #{password}, #{role}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT COUNT(*) FROM users")
    long count();
}

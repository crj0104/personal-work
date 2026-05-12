package com.foodorder.repository;

import org.apache.ibatis.annotations.*;

import com.foodorder.bean.Order;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("SELECT * FROM orders")
    List<Order> findAll();

    @Select("SELECT * FROM orders WHERE id = #{id}")
    Order findById(Long id);

    @Select("SELECT * FROM orders WHERE user_id = #{userId} ORDER BY created_at DESC")
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Insert("INSERT INTO orders(user_id, total_price, created_at) VALUES(#{userId}, #{totalPrice}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);

    @Select("SELECT COUNT(*) FROM orders")
    long count();
}

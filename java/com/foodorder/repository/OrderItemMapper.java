package com.foodorder.repository;

import org.apache.ibatis.annotations.*;

import com.foodorder.bean.OrderItem;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    @Select("SELECT * FROM order_items WHERE order_id = #{orderId}")
    List<OrderItem> findByOrderId(Long orderId);

    @Insert("INSERT INTO order_items(order_id, product_id, quantity, price) VALUES(#{orderId}, #{productId}, #{quantity}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OrderItem orderItem);

    @Delete("DELETE FROM order_items WHERE order_id = #{orderId}")
    void deleteByOrderId(Long orderId);
}

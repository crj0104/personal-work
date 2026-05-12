package com.foodorder.repository;

import org.apache.ibatis.annotations.*;

import com.foodorder.bean.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM products")
    List<Product> findAll();

    @Select("SELECT * FROM products WHERE id = #{id}")
    Product findById(Long id);

    @Select("SELECT * FROM products WHERE category_id = #{categoryId}")
    List<Product> findByCategoryId(Long categoryId);

    @Insert("INSERT INTO products(category_id, name, image_url, price) VALUES(#{categoryId}, #{name}, #{imageUrl}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);
}

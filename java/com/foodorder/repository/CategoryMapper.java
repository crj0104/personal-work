package com.foodorder.repository;

import org.apache.ibatis.annotations.*;

import com.foodorder.bean.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM categories")
    List<Category> findAll();

    @Select("SELECT * FROM categories WHERE id = #{id}")
    Category findById(Long id);

    @Insert("INSERT INTO categories(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Category category);

    @Select("SELECT COUNT(*) FROM categories")
    long count();
}

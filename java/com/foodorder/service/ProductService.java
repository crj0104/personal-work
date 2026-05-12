package com.foodorder.service;

import com.foodorder.bean.Category;
import com.foodorder.bean.Product;
import com.foodorder.repository.CategoryMapper;
import com.foodorder.repository.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    public ProductService(CategoryMapper categoryMapper, ProductMapper productMapper) {
        this.categoryMapper = categoryMapper;
        this.productMapper = productMapper;
    }

    public Product findById(Long id) {
        return productMapper.findById(id);
    }

    public Map<Category, List<Product>> getGroupedProducts() {
        List<Category> categories = categoryMapper.findAll();
        Map<Category, List<Product>> result = new LinkedHashMap<>();
        for (Category cat : categories) {
            List<Product> products = productMapper.findByCategoryId(cat.getId());
            for (Product p : products) {
                p.setCategoryName(cat.getName());
            }
            result.put(cat, products);
        }
        return result;
    }
}

package com.foodorder.bean;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String name;
    private String imageUrl;
    private double price;
}

package com.foodorder.bean;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Long userId;
    private String userPhone;
    private double totalPrice;
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<OrderItem> items;
}

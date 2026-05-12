package com.foodorder.bean;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String phone;
    private String password;
    private String role = "user";
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
}

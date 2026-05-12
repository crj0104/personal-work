package com.foodorder.service;

import com.foodorder.bean.Order;
import com.foodorder.bean.OrderItem;
import com.foodorder.bean.Product;
import com.foodorder.repository.OrderItemMapper;
import com.foodorder.repository.OrderMapper;
import com.foodorder.repository.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductMapper productMapper;

    public OrderService(OrderMapper orderMapper,
                        OrderItemMapper orderItemMapper,
                        ProductMapper productMapper) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.productMapper = productMapper;
    }

    public List<Order> getUserOrders(Long userId) {
        List<Order> orders = orderMapper.findByUserIdOrderByCreatedAtDesc(userId);
        for (Order order : orders) {
            List<OrderItem> items = orderItemMapper.findByOrderId(order.getId());
            for (OrderItem item : items) {
                Product product = productMapper.findById(item.getProductId());
                if (product != null) {
                    item.setProductName(product.getName());
                    item.setProductImageUrl(product.getImageUrl());
                }
            }
            order.setItems(items);
        }
        return orders;
    }

    @Transactional
    public Order createOrder(Long userId, List<OrderItem> items, double totalPrice) {
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        orderMapper.insert(order);

        for (OrderItem item : items) {
            item.setOrderId(order.getId());
            orderItemMapper.insert(item);
        }

        return order;
    }

    public List<Order> findAllOrders() {
        List<Order> orders = orderMapper.findAll();
        for (Order order : orders) {
            List<OrderItem> items = orderItemMapper.findByOrderId(order.getId());
            for (OrderItem item : items) {
                Product product = productMapper.findById(item.getProductId());
                if (product != null) {
                    item.setProductName(product.getName());
                    item.setProductImageUrl(product.getImageUrl());
                }
            }
            order.setItems(items);
        }
        return orders;
    }

    public Map<String, Object> getSalesStats() {
        long ordersCount = orderMapper.count();
        Double totalSales = 0.0;
        List<Order> allOrders = orderMapper.findAll();
        for (Order o : allOrders) {
            totalSales += o.getTotalPrice();
        }
        Map<String, Object> stats = new HashMap<>();
        stats.put("ordersCount", ordersCount);
        stats.put("totalSales", totalSales);
        return stats;
    }
}

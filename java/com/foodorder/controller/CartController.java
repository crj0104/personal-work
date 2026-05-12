package com.foodorder.controller;

import com.foodorder.bean.OrderItem;
import com.foodorder.bean.Product;
import com.foodorder.bean.User;
import com.foodorder.service.OrderService;
import com.foodorder.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;
    private final OrderService orderService;

    public CartController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping
    public String cartPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        @SuppressWarnings("unchecked")
        List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        double total = 0;
        for (OrderItem item : cart) {
            total += item.getPrice() * item.getQuantity();
        }

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                            HttpSession session,
                            RedirectAttributes ra) {
        Product product = productService.findById(productId);
        if (product == null) {
            ra.addFlashAttribute("error", "商品不存在");
            return "redirect:/";
        }

        @SuppressWarnings("unchecked")
        List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        for (OrderItem item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + 1);
                session.setAttribute("cart", cart);
                ra.addFlashAttribute("success", "已添加到购物车");
                return "redirect:/";
            }
        }

        OrderItem newItem = new OrderItem();
        newItem.setProductId(product.getId());
        newItem.setProductName(product.getName());
        newItem.setProductImageUrl(product.getImageUrl());
        newItem.setPrice(product.getPrice());
        newItem.setQuantity(1);
        cart.add(newItem);
        session.setAttribute("cart", cart);
        ra.addFlashAttribute("success", "已添加到购物车");
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId,
                                 @RequestParam int quantity,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        @SuppressWarnings("unchecked")
        List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> {
                if (item.getProductId().equals(productId)) {
                    if (quantity <= 0) return true;
                    item.setQuantity(quantity);
                }
                return false;
            });
        }
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        @SuppressWarnings("unchecked")
        List<OrderItem> cart = (List<OrderItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            ra.addFlashAttribute("error", "购物车为空");
            return "redirect:/cart";
        }

        double total = 0;
        for (OrderItem item : cart) {
            total += item.getPrice() * item.getQuantity();
        }

        orderService.createOrder(user.getId(), cart, total);
        session.removeAttribute("cart");
        ra.addFlashAttribute("success", "订单已生成");
        return "redirect:/orders";
    }
}

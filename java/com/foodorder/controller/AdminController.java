package com.foodorder.controller;

import com.foodorder.service.ProductService;
import com.foodorder.service.UserService;
import com.foodorder.bean.User;
import com.foodorder.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    public AdminController(UserService userService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && "admin".equals(user.getRole());
    }

    @GetMapping
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        model.addAttribute("stats", orderService.getSalesStats());
        model.addAttribute("usersCount", userService.findAllUsers().size());
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        model.addAttribute("users", userService.findAllUsers());
        return "admin/users";
    }

    @GetMapping("/products")
    public String products(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        model.addAttribute("groupedProducts", productService.getGroupedProducts());
        return "admin/products";
    }

    @GetMapping("/orders")
    public String orders(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        model.addAttribute("orders", orderService.findAllOrders());
        return "admin/orders";
    }
}

package com.example.dune_data.controller;

import com.example.dune_data.model.Product;
import com.example.dune_data.model.User;
import com.example.dune_data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserInfo(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @PostMapping
    public void postUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PutMapping
    public void putUser(@RequestBody User user) {
        userService.putUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @DeleteMapping
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

    @GetMapping("/products/{userId}")
    public List<Product> getAllProducts(@PathVariable String userId) {
        return userService.getAllProducts(userId);
    }

    @GetMapping("/products/{userId}/{productId}")
    public Product getProduct(@PathVariable String userId, @PathVariable String productId) {
        return userService.getProduct(userId, productId);
    }

    @PostMapping("/products/{userId}")
    public void postProduct(@PathVariable String userId, @RequestBody Product product) {
        userService.postProduct(userId, product);
    }

    @DeleteMapping("/products/{userId}/{productId}")
    public void deleteProduct(@PathVariable String userId, @PathVariable String productId) {
        userService.deleteProduct(userId, productId);
    }
}
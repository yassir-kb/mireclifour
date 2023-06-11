package com.example.dune_data.service;

import com.example.dune_data.model.Product;
import com.example.dune_data.repo.UserRepo;
import com.example.dune_data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUser(String id) {
        return userRepo.findAllById(id);
    }

    public void addUser(User user) {
        List<User> user_list = userRepo.findAll();
        user_list.add(user);
        userRepo.save(user);
    }

    public void putUser(User body) {
        User user = getUser(body.getId());
        user = body;
        userRepo.save(user);
    }

    public void deleteUser(String id) {
        userRepo.deleteById(id);

    }

    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    public List<Product> getAllProducts(String userId) {
        return getUser(userId).getProducts();
    }

    public Product getProduct(String userId, String productId) {
        List<Product> products = getAllProducts(userId);
        Product product = new Product();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(productId)) {
                product = products.get(i);
            }
        }
        return product;
    }

    public void postProduct(String userId, Product product) {
        List<Product> products = getAllProducts(userId);
        products.add(product);
        User user = getUser(userId);
        user.setProducts(products);
        userRepo.save(user);
    }

    public void deleteProduct(String userId, String productId) {
        List<Product> products = getAllProducts(userId);
        User user = getUser(userId);
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(productId)) {
                products.remove(products.get(i));
                user.setProducts(products);
                userRepo.save(user);
            }
        }
    }
}

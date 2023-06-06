package com.example.mireclifour.model;

import java.util.List;

public class Product {
    String id;
    String product_name;
    String size;
    String color;
    String brand;
    String description;
    String base_image;
    String normal_price;
    String sale_price;
    String qty;
    List<ProductOptions> product_options;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBase_image() {
        return base_image;
    }

    public void setBase_image(String base_image) {
        this.base_image = base_image;
    }

    public String getNormal_price() {
        return normal_price;
    }

    public void setNormal_price(String normal_price) {
        this.normal_price = normal_price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public List<ProductOptions> getProduct_options() {
        return product_options;
    }

    public void setProduct_options(List<ProductOptions> product_options) {
        this.product_options = product_options;
    }

    public Product() {
    }

    public Product(String id, String product_name, String size, String color, String brand, String description, String base_image, String normal_price, String sale_price, String qty, List<ProductOptions> product_options) {
        this.id = id;
        this.product_name = product_name;
        this.size = size;
        this.color = color;
        this.brand = brand;
        this.description = description;
        this.base_image = base_image;
        this.normal_price = normal_price;
        this.sale_price = sale_price;
        this.qty = qty;
        this.product_options = product_options;
    }
}
package com.example.dune_data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String id;
    private String product_name;
    private String size;
    private String color;
    private String brand;
    private String description;
    private String base_image;
    private String normal_price;
    private String sale_price;
    private String qty;
    private List<ProductOption> product_options;

}

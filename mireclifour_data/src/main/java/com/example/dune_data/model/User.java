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
public class User {
    private String id;
    private String firstname;
    private String lastname;
    private String mail;
    private String number;
    private String dob;
    private String gender;
    private String type;
    private String logo;
    private String password;
    private List<Product> products;
}

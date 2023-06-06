package com.example.mireclifour.model;

public class MainItems {

    private String id, name, logo, contact;

    public MainItems(String id, String name, String logo, String contact) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public MainItems() {
    }
}
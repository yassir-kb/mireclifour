package com.example.mireclifour.model;

public class ProductOptions {
    public ProductOptions() {
    }

    public ProductOptions(String option_id, String option_name, Attribute attributes) {
        this.option_id = option_id;
        this.option_name = option_name;
        this.attributes = attributes;
    }

    String option_id, option_name;
    Attribute attributes;

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public Attribute getAttributes() {
        return attributes;
    }

    public void setAttributes(Attribute attributes) {
        this.attributes = attributes;
    }
}

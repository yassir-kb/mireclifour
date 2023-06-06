package com.example.mireclifour.model;

public class Attribute {
    String att_id, beden, renk;

    public Attribute(String att_id, String beden, String renk) {
        this.att_id = att_id;
        this.beden = beden;
        this.renk = renk;
    }

    public Attribute() {
    }

    public String getAtt_id() {
        return att_id;
    }

    public void setAtt_id(String att_id) {
        this.att_id = att_id;
    }

    public String getBeden() {
        return beden;
    }

    public void setBeden(String beden) {
        this.beden = beden;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }
}
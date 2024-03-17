package com.example.fa3_1_ex5.model;

import java.io.Serializable;

public class Dog implements Serializable {
    private int id;
    private String name;

    //pending, no, yes
    private String adoption_status;

    private String picture;
    private String description;

    private int owner;

    public Dog() {
    }

    public Dog(String name, String picture, String description, String adoption_status, int owner){
        this.name = name;
        this.adoption_status = adoption_status;
        this.picture = picture;
        this.description = description;
        this.owner = owner;
    }

    public Dog(String name, String picture, String description){
        this.name = name;
        this.picture = picture;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdoption_status() {
        return adoption_status;
    }

    public void setAdoption_status(String adoption_status) {
        this.adoption_status = adoption_status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}

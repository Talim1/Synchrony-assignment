package com.example.demo.model;

import java.util.List;

public class User {

    private String username;
    private String password;
    private String fullName;
    private String country;
    private int age;
    private String email;

    private List<Data> imageData;

    public List<Data> getImageData() {
        return imageData;
    }

    public void setImageData(List<Data> imageData) {
        this.imageData = imageData;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

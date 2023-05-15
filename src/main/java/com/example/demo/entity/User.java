package com.example.demo.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "APP_USER")
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column
    private String country;

    @Column
    private int age;

    @Column(nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private List<ImageMetadata> imageMetadata;

    private boolean authenticated;

    public boolean isAuthenticated() {
        return authenticated;
    }

    public List<ImageMetadata> getImageMetadata() {
        return imageMetadata;
    }

    public boolean addImageMetadata(ImageMetadata im) {
        if(this.imageMetadata == null) {
            this.imageMetadata = new ArrayList<>();
        }
        return imageMetadata.add(im);
    }

    public void setImageMetadata(List<ImageMetadata> imageMetadata) {
        this.imageMetadata = imageMetadata;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

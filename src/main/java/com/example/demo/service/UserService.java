package com.example.demo.service;

import com.example.demo.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> registerUser(com.example.demo.model.User user) throws Exception;

    boolean authenticateUser(String userName, String password) throws Exception;


}

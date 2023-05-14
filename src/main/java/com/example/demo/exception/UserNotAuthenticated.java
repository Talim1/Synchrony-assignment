package com.example.demo.exception;

public class UserNotAuthenticated extends Exception {

    public UserNotAuthenticated(String message) {
        super(message);
    }
}

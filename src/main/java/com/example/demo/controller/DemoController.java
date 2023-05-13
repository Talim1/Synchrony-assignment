package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


    @GetMapping("/ping")
    public String message() {
        return "pong";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user){

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}

package com.example.demo.controller;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/ping")
    public String message() {
        return "pong";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user){

        logger.info("User registration started {}", user.getUsername());
        try {
            userService.registerUser(user);
        } catch (Exception e) {
            logger.error("Error in processing request", e);
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){

        logger.info("User authentication started {}", user.getUsername());
        if(StringUtils.isEmpty(user.getUsername())) {
            return new ResponseEntity<>("Username is missing", HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isEmpty(user.getPassword())) {
            return new ResponseEntity<>("Password is missing", HttpStatus.BAD_REQUEST);
        }
        try {
            boolean status = userService.authenticateUser(user.getUsername(), user.getPassword());
            if(status) {
                return new ResponseEntity<>("User authenticated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity("Failed to authenticate User", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (UserNotFoundException userNotFoundException) {
            logger.error(userNotFoundException.getMessage(), userNotFoundException);
            return new ResponseEntity(userNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/user")
    public ResponseEntity<?> viewUser(@RequestParam("userName") String userName) {

        logger.info("Request received to retrieve user {}", userName);
        try {
            Optional<User> user = userService.retrieveUser(userName);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

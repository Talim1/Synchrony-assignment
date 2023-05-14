package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User registerUser(User user) throws Exception {

        com.example.demo.entity.User userEntity = userMapper.mapToUserEntity(user);
        com.example.demo.entity.User dbUser = userRepository.save(userEntity);
        if(null == dbUser) {
            logger.error("Error occurred in Registering the user: {}", user.getUsername());
            throw new Exception("Error occurred in Registering the user: "+ user.getUsername());
        }
        User userModel = userMapper.mapToUserModel(dbUser);
        logger.info("User {} registered successfully.", dbUser.getUsername());
        return userModel;
    }
}

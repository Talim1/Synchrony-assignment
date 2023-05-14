package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    com.example.demo.model.User mapToUserModel(User user);

    User mapToUserEntity(com.example.demo.model.User user);
}

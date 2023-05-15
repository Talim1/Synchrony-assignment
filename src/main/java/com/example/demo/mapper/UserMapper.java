package com.example.demo.mapper;

import com.example.demo.entity.ImageMetadata;
import com.example.demo.entity.User;
import com.example.demo.model.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = ImageDataMapper.class)
public interface UserMapper {

     // UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", constant = "****")
    com.example.demo.model.User mapToUserModel(User user);

    User mapToUserEntity(com.example.demo.model.User user);

}

package com.example.demo.mapper;


import com.example.demo.entity.ImageMetadata;
import com.example.demo.model.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageDataMapper {

    @Mapping(target = "id", source = "fileId")
    @Mapping(target = "type", source = "fileType")
    @Mapping(target = "deletehash", source = "deleteHash")
    @Mapping(target = "link", source = "previewLink")
    Data toImageData(ImageMetadata imageMetadata);
}

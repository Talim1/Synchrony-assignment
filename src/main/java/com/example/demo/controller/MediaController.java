package com.example.demo.controller;

import com.example.demo.model.FileMetadata;
import com.example.demo.service.MediaService;
import com.example.demo.util.FileConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class MediaController {

    @Autowired
    private MediaService mediaService;

    private Logger logger = LoggerFactory.getLogger(MediaController.class);

    @PostMapping("/image/upload")
    public ResponseEntity<?> uploadImage(@RequestPart("file") MultipartFile inputFile,
                                                    @RequestParam("userName") String userName) throws Exception{

        logger.info("Image upload started, fileName: {}, user: {}", inputFile.getName(), userName);
        File convertedFile = FileConverter.convertToFile(inputFile);
        mediaService.uploadFile(convertedFile, userName);

        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }
}

package com.example.demo.controller;

import com.example.demo.exception.FileNotFoundException;
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
                                                    @RequestParam("userName") String userName) {

        if(null == inputFile) {
            return new ResponseEntity<>("File is missing", HttpStatus.BAD_REQUEST);
        }
        try {
            logger.info("Image upload started, fileName: {}, user: {}", inputFile.getName(), userName);
            File convertedFile = FileConverter.convertToFile(inputFile);
            mediaService.uploadFile(convertedFile, userName);
        } catch(Exception e) {
            return new ResponseEntity("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }

    @DeleteMapping("/image/delete/{fileId}")
    public ResponseEntity<?> deleteImage(@RequestParam("userName") String userName,
                                         @PathVariable("fileId") String fileId) {

        try {
            mediaService.deleteFile(userName, fileId);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }
}

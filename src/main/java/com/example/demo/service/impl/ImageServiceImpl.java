package com.example.demo.service.impl;

import com.example.demo.entity.ImageMetadata;
import com.example.demo.entity.User;
import com.example.demo.exception.FileNotFoundException;
import com.example.demo.model.FileMetadata;
import com.example.demo.repository.ImageMetadataRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MediaService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;

@EnableConfigurationProperties
@Service
public class ImageServiceImpl implements MediaService {

    @Value("${imgur.clientId}")
    private String clientId;

    @Value("${imgur.imgurBaseUrl}")
    private String imgurBaseUrl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageMetadataRepository imageMetadataRepository;

    private RestTemplate restTemplate = new RestTemplate();

    private Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Override
    public FileMetadata uploadFile(File file, String userName) {

        logger.info("******************* " + imgurBaseUrl);
        logger.info("******************* " + clientId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Client-ID " + clientId);

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("image", new FileSystemResource(file));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parts, headers);
        ResponseEntity<FileMetadata> response = restTemplate.exchange(imgurBaseUrl,
                HttpMethod.POST, requestEntity, FileMetadata.class);

        saveImageMetadataInUserProfile(response.getBody(), userName);
        CompletableFuture.runAsync(() -> {
            try {
                FileUtils.forceDelete(file);
                //saveImageMetadataInUserProfile(response.getBody(), userName);
            } catch (IOException e) {
                logger.error("Failed to delete the temp file.");
            }
        });

//        CompletableFuture.runAsync(() -> {
//            saveImageMetadataInUserProfile(response.getBody(), userName);
//        });


        logger.info(response.getBody().toString());

        return response.getBody();
    }

    @Override
    public void deleteFile(String fileOwner, String fileId) throws FileNotFoundException {
        Optional<ImageMetadata> imageMetadata = imageMetadataRepository.findUserByUserNameAndFileId(fileOwner, fileId);

        logger.info("******************* " + imgurBaseUrl);
        logger.info("******************* " + clientId);

        if(imageMetadata.isEmpty()) {
            throw new FileNotFoundException("File not found");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Client-ID " + clientId);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<FileMetadata> response = restTemplate.exchange(imgurBaseUrl+"/"+imageMetadata.get().getDeleteHash(),
                HttpMethod.DELETE, requestEntity, FileMetadata.class);

        logger.info("**********" + response.getBody().toString());
        if(response.getBody().isSuccess()) {

                imageMetadataRepository.delete(imageMetadata.get());


        }

    }

    private void saveImageMetadataInUserProfile(FileMetadata fileMetadata, String userName) {
        Optional<User> user = userRepository.findUserByUserName(userName);

        ImageMetadata im = new ImageMetadata();
        im.setFileId(fileMetadata.getData().getId());
        im.setDeleteHash(fileMetadata.getData().getDeletehash());
        im.setPreviewLink(fileMetadata.getData().getLink());
        im.setFileType(fileMetadata.getData().getType());
        im.setUsername(userName);

        user.get().getImageMetadata().add(im);


        User savedUser = userRepository.save(user.get());

    }
}

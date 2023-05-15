package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.DemoApplicationTests;
import com.example.demo.entity.ImageMetadata;
import com.example.demo.mapper.ImageDataMapper;
import com.example.demo.mapper.ImageDataMapperImpl;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserMapperImpl;
import com.example.demo.model.Data;
import com.example.demo.model.FileMetadata;
import com.example.demo.model.User;
import com.example.demo.repository.ImageMetadataRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.ImageServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
public class ImageServiceImplTest {

    @Value("${imgur.clientId}")
    private String clientId;

    @Value("${imgur.imgurBaseUrl}")
    private String imgurBaseUrl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageMetadataRepository imageMetadataRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Before
    public void init() {
        ReflectionTestUtils.setField(imageService, "clientId", "abc123");
        ReflectionTestUtils.setField(imageService, "imgurBaseUrl", "www.google.com");
    }

    @Test
    public void testUploadFile() throws Exception {

        FileMetadata fm = new FileMetadata();
        fm.setStatus(200);
        fm.setSuccess(true);
        Data data = new Data();
        data.setDeletehash("abc123");
        data.setId("111");
        data.setLink("www.google.com");
        data.setType("image/jpg");
        fm.setData(data);

        com.example.demo.entity.User dbUser = new com.example.demo.entity.User();
        dbUser.setAge(50);
        dbUser.setEmail("abc@email.com");

        ResponseEntity<FileMetadata> response = new ResponseEntity<>(fm, HttpStatus.OK);
        File file = new File("./src/test/resources/sample_image.jpg");
        when(userRepository.save(any())).thenReturn(dbUser);
        when(userRepository.findUserByUserName(Mockito.anyString())).thenReturn(Optional.of(dbUser));
        when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.POST), Mockito.any(HttpEntity.class), Mockito.<Class<FileMetadata>>any())
        ).thenReturn(response);
        FileMetadata fileMetadata = imageService.uploadFile(file, "sislam028");
        assertNotNull(fileMetadata);
        assertTrue(fileMetadata.isSuccess());
        assertNotNull(fileMetadata.getData());
        assertEquals(200, fileMetadata.getStatus());
        assertEquals("abc123", fileMetadata.getData().getDeletehash());
    }
}

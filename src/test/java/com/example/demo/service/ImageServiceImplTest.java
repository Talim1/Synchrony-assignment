package com.example.demo.service;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("junit")
public class ImageServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageMetadataRepository imageMetadataRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Autowired
    private UserMapper userMapper;

    //private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    private ImageDataMapper imageDataMapper;

    @Before
    public void init() {

        ReflectionTestUtils.setField(imageService, "restTemplate", restTemplate);
    }

    @Test
    public void testUploadFile() throws Exception {

        User user = new User();

        FileMetadata fm = new FileMetadata();
        fm.setStatus(200);
        fm.setSuccess(true);
        Data data = new Data();
        data.setDeletehash("abc123");
        data.setId("111");
        data.setLink("www.google.com");
        data.setType("image/jpg");
        fm.setData(data);
        ResponseEntity<FileMetadata> response = new ResponseEntity<>(fm, HttpStatus.OK);
        File file = new File("/src/test/resources/sample_image.jpg");
        when(userRepository.save(any())).thenReturn(user);
       // when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), any(), Mockito.any(Class.class))).thenReturn(response);
//        when(restTemplate.exchange(
//                ArgumentMatchers.anyString(),
//                ArgumentMatchers.any(HttpMethod.class),
//                ArgumentMatchers.any(),
//                ArgumentMatchers.<Class<FileMetadata>>any()))
//                .thenReturn(response);
        when(restTemplate.exchange(Mockito.anyString()
                        , Mockito.eq(HttpMethod.POST)
                        , Mockito.any(HttpEntity.class)
                        , Mockito.<Class<FileMetadata>>any())
        ).thenReturn(response);
        imageService.uploadFile(file, null);

    }
}

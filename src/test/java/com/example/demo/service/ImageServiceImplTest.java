package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.entity.ImageMetadata;
import com.example.demo.exception.FileNotFoundException;
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
import static org.mockito.Mockito.*;

import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest()
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
    public void testUploadFile_Success() {

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

    @Test(expected = RestClientException.class)
    public void testUploadFile_Failure() {
        File file = new File("./");
        when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.POST), Mockito.any(HttpEntity.class), Mockito.<Class<FileMetadata>>any())
        ).thenThrow(RestClientException.class);

        imageService.uploadFile(file, "sislam028");

    }

    @Test(expected = FileNotFoundException.class)
    public void testDeleteFile_Failure() throws FileNotFoundException {

        when(imageMetadataRepository.findUserByUserNameAndFileId(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());
        imageService.deleteFile("sislam028", "abc123");
    }

    @Test
    public void testDeleteFile_Success() throws FileNotFoundException {
        ResponseEntity<FileMetadata> response = new ResponseEntity<>(HttpStatus.OK);
        when(imageMetadataRepository.findUserByUserNameAndFileId(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(createImageMetadata()));
        doNothing().when(imageMetadataRepository).delete(Mockito.any());
        when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.DELETE), Mockito.any(HttpEntity.class), Mockito.<Class<FileMetadata>>any())
        ).thenReturn(response);
        imageService.deleteFile("sislam028", "abc123");

        verify(imageMetadataRepository, times(1)).delete(Mockito.any());
    }

    private ImageMetadata createImageMetadata() {

        ImageMetadata imageMetadata = new ImageMetadata();
        imageMetadata.setUsername("sislam028");
        imageMetadata.setDeleteHash("sxw3w");

        return imageMetadata;
    }


}

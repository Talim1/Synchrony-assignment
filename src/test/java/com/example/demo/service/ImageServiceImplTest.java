package com.example.demo.service;

import com.example.demo.mapper.ImageDataMapper;
import com.example.demo.mapper.ImageDataMapperImpl;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserMapperImpl;
import com.example.demo.model.User;
import com.example.demo.repository.ImageMetadataRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.ImageServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;


@SpringBootTest()

public class ImageServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageMetadataRepository imageMetadataRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Autowired
    private UserMapper userMapper;

    //private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    private ImageDataMapper imageDataMapper;

//    @Before
//    public void init() {
//        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
//        ReflectionTestUtils.setField(imageService, "roleMapper", roleMapper);
//    }

    @Test
    public void testUploadFile() throws Exception {

        User user = new User();
        when(userRepository.save(any())).thenReturn(user);
        imageService.uploadFile(null, null);

    }
}

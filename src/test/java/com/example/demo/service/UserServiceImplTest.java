//package com.example.demo.service;
//
//import com.example.demo.mapper.ImageDataMapperImpl;
//import com.example.demo.mapper.UserMapperImpl;
//import com.example.demo.model.User;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.service.impl.UserServiceImpl;
//import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {UserMapperImpl.class, ImageDataMapperImpl.class})
//@ExtendWith(MockitoExtension.class)
//public class UserServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//
//    @Test
//    public void testRegisterUser() throws Exception {
//
//        User user = new User();
//        when(userRepository.save(any())).thenReturn(user);
//        userService.registerUser(user);
//
//    }
//}

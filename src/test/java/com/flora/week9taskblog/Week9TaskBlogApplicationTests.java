package com.flora.week9taskblog;

import com.flora.week9taskblog.Payload.Response.UserResponse;
import com.flora.week9taskblog.Repository.UserRepository;
import com.flora.week9taskblog.Service.ServiceImpl.UserServiceImpl;
import com.flora.week9taskblog.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Week9TaskBlogApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class Week9TaskBlogApplicationTests {

    @Test
    void contextLoads() {
    }
//    UserServiceImpl userService = new UserServiceImpl();
    UserResponse userResponse = new UserResponse("Successfully",new User());
    @Mock
    private UserRepository mockedUserRepository;

    void userShouldConnectWithOtherUsers(){
        when(mockedUserRepository.addConnect(4l,"amy"))
                .thenReturn(userResponse);

    }

    void userShouldViewConnectionPost(){

    }
    @Test
    void testOne() {
        assertNotNull("NOT NULL");
        assertNotEquals("John", "Duke");
        assertThrows(NumberFormatException.class, () -> Integer.valueOf("duke"));
        assertEquals("hello world", "HELLO WORLD".toLowerCase());
    }
}

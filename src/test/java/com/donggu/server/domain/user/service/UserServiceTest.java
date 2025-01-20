package com.donggu.server.domain.user.service;

import com.donggu.server.domain.user.domain.Role;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(1L)
                .email("test@donggu.com")
                .role(Role.ROLE_USER)
                .build();
    }

    @Test
    void findByEmail() {
        when(userRepository.findByEmail("test@donggu.com")).thenReturn(Optional.of(user));

        User testUser = userService.findByEmail("test@donggu.com");

        assertEquals(testUser, user);
    }

    @Test
    void findById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User testUser = userService.findById(1L);

        assertEquals(testUser, user);
    }

    @Test
    void getUserList() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> userList = userService.getUserList();

        assertNotNull(userList);
        assertEquals(userList.size(), 1);
        assertEquals(userList.get(0), user);
    }

    @Test
    void saveUser() {
        when(userRepository.save(user)).thenReturn(user);

        User testUser = userRepository.save(user);

        assertEquals(testUser, user);
        verify(userRepository, times(1)).save(user);
    }
}
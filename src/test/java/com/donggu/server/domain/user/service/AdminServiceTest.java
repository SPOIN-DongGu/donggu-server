package com.donggu.server.domain.user.service;

import com.donggu.server.domain.user.domain.Role;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.repository.UserRepository;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private UserRepository userRepository;

    @Test
    void changeUserRoleToAdmin_Success() {
        User user = User.builder()
                .id(1L)
                .email("test@donggu.com")
                .role(Role.ROLE_USER)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        adminService.changeUserRoleToAdmin(1L);

        assertEquals(user.getRole(), Role.ROLE_ADMIN);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changeUserRoleToAdmin_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> adminService.changeUserRoleToAdmin(1L));
        assertEquals(exception.getErrorCode(), ErrorCode.NOT_FOUND);
        verify(userRepository, times(1)).findById(1L);
    }
}
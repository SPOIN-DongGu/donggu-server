package com.donggu.server.domain.pickupjoin.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.dto.PickupDetailResponseDto;
import com.donggu.server.domain.pickup.service.PickupService;
import com.donggu.server.domain.pickupjoin.domain.PickupJoin;
import com.donggu.server.domain.pickupjoin.dto.PickupJoinRequestDto;
import com.donggu.server.domain.pickupjoin.repository.PickupJoinRepository;
import com.donggu.server.domain.user.domain.Gender;
import com.donggu.server.domain.user.domain.Position;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.service.UserService;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PickupJoinServiceTest {

    @InjectMocks
    private PickupJoinService pickupJoinService;

    @Mock
    private PickupJoinRepository pickupJoinRepository;

    @Mock
    private PickupService pickupService;

    @Mock
    private UserService userService;

    @Test
    void getAllAppliedPickup_Success() {
        User user = mock(User.class);
        PickupJoin pickupJoin1 = mock(PickupJoin.class);
        PickupJoin pickupJoin2 = mock(PickupJoin.class);

        when(userService.findById(1L)).thenReturn(user);
        when(pickupJoinRepository.findAllByUser(user)).thenReturn(List.of(pickupJoin1, pickupJoin2));
        when(pickupJoin1.getPickup()).thenReturn(mock(Pickup.class));
        when(pickupJoin2.getPickup()).thenReturn(mock(Pickup.class));

        List<PickupDetailResponseDto> result = pickupJoinService.getAllAppliedPickup(1L);

        assertNotNull(result);
        assertEquals(result.size(), 2);
        verify(pickupJoinRepository, times(1)).findAllByUser(user);
    }

    @Test
    void applyPickup_Success() {
        Pickup pickup = mock(Pickup.class);
        User user = mock(User.class);
        PickupJoinRequestDto dto = new PickupJoinRequestDto(1L, "test", Gender.MALE, 0.0, 0.0, Position.PG);

        when(pickupService.findPickupById(1L)).thenReturn(pickup);
        when(userService.findById(1L)).thenReturn(user);
        when(pickupJoinRepository.findByPickupAndUser(pickup, user)).thenReturn(null);

        pickupJoinService.applyPickup(1L, dto);

        verify(pickupJoinRepository, times(1)).save(any(PickupJoin.class));
    }

    @Test
    void applyPickup_AlreadyApply() {
        Long id = 1L;
        Pickup pickup = mock(Pickup.class);
        User user = mock(User.class);
        PickupJoinRequestDto dto = new PickupJoinRequestDto(id, "test", Gender.MALE, 0.0, 0.0, Position.PG);

        when(pickupService.findPickupById(id)).thenReturn(pickup);
        when(userService.findById(id)).thenReturn(user);
        when(pickupJoinRepository.findByPickupAndUser(pickup, user)).thenReturn(mock(PickupJoin.class));

        CustomException exception = assertThrows(CustomException.class, () -> pickupJoinService.applyPickup(id, dto));

        assertEquals(exception.getErrorCode(), ErrorCode.ALREADY_APPLY);
        verify(pickupService, times(1)).findPickupById(id);
        verify(userService, times(1)).findById(id);
        verify(pickupJoinRepository, times(1)).findByPickupAndUser(pickup, user);
    }
}
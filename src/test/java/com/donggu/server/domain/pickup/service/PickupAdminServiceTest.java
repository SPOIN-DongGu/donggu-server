package com.donggu.server.domain.pickup.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.dto.PickupRequestDto;
import com.donggu.server.domain.pickup.repository.PickupRepository;
import com.donggu.server.domain.user.domain.Gender;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PickupAdminServiceTest {

    @InjectMocks
    private PickupAdminService pickupAdminService;

    @Mock
    private PickupRepository pickupRepository;

    private PickupRequestDto mockDto;

    @BeforeEach
    void setUp() {
        mockDto = new PickupRequestDto(
                LocalDate.parse("2024-12-13"),
                LocalTime.parse("01:00:00"),
                LocalTime.parse("02:00:00"),
                "testRegion",
                "testLocation",
                Gender.MALE,
                5,
                10000L,
                12
        );
    }

    @Test
    void registerPickup() {
        pickupAdminService.registerPickup(mockDto);

        verify(pickupRepository, times(1)).save(any(Pickup.class));
    }

    @Test
    void updatePickup_Success() {
        Long pickupId = 1L;
        Pickup pickup = mock(Pickup.class);
        when(pickupRepository.findById(pickupId)).thenReturn(Optional.of(pickup));

        pickupAdminService.updatePickup(pickupId, mockDto);

        verify(pickup, times(1)).updateDate(mockDto.date());
        verify(pickup, times(1)).updateStartTime(mockDto.startTime());
        verify(pickup, times(1)).updateEndTime(mockDto.endTime());
        verify(pickup, times(1)).updateRegion(mockDto.region());
        verify(pickup, times(1)).updateLocation(mockDto.location());
        verify(pickup, times(1)).updateGender(mockDto.gender());
        verify(pickup, times(1)).updatePrice(mockDto.price());
        verify(pickup, times(1)).updateMaxParticipant(mockDto.maxParticipant());
        verify(pickupRepository, times(1)).save(pickup);
    }

    @Test
    void updatePickup_pickupNotFound() {
        when(pickupRepository.findById(2L)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> pickupAdminService.updatePickup(2L, mockDto));
        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
        verify(pickupRepository, times(1)).findById(2L);
    }

    @Test
    void deletePickup_Success() {
        Long pickupId = 1L;
        Pickup pickup = mock(Pickup.class);
        when(pickupRepository.findById(pickupId)).thenReturn(Optional.of(pickup));

        pickupAdminService.deletePickup(pickupId);

        verify(pickupRepository, times(1)).findById(pickupId);
        verify(pickupRepository, times(1)).delete(pickup);
    }

    @Test
    void deletePickup_pickupNotFound() {
        Long pickupId = 1L;
        when(pickupRepository.findById(pickupId)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> pickupAdminService.deletePickup(pickupId));
        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
        verify(pickupRepository, times(1)).findById(pickupId);
    }
}
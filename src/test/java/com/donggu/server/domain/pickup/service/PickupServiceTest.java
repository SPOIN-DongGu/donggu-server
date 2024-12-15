package com.donggu.server.domain.pickup.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.dto.PickupResponseDto;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PickupServiceTest {

    @InjectMocks
    private PickupService pickupService;

    @Mock
    private PickupRepository pickupRepository;

    private Pickup mockPickup1;
    private Pickup mockPickup2;

    @BeforeEach
    void setUp() {
        mockPickup1 = Pickup.builder()
                .id(1L)
                .date(LocalDate.parse("2024-12-13"))
                .startTime(LocalTime.parse("01:00:00"))
                .endTime(LocalTime.parse("02:00:00"))
                .region("testRegion1")
                .location("testLocation1")
                .gender(Gender.MALE)
                .gameType(5)
                .price(10000L)
                .maxParticipant(12)
                .currentParticipant(0)
                .build();

        mockPickup2 = Pickup.builder()
                .id(2L)
                .date(LocalDate.parse("2024-12-14"))
                .startTime(LocalTime.parse("03:00:00"))
                .endTime(LocalTime.parse("04:00:00"))
                .region("testRegion2")
                .location("testLocation2")
                .gender(Gender.FEMALE)
                .gameType(3)
                .price(20000L)
                .maxParticipant(8)
                .currentParticipant(0)
                .build();
    }

    @Test
    void getAllPickup() {
        when(pickupRepository.findAll()).thenReturn(List.of(mockPickup1, mockPickup2));

        List<PickupResponseDto> dtoList = pickupService.getAllPickup();

        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(mockPickup1.getId(), dtoList.get(0).id());
        verify(pickupRepository, times(1)).findAll();
    }

    @Test
    void getPickup_Success() {
        when(pickupRepository.findById(1L)).thenReturn(Optional.of(mockPickup1));

        PickupResponseDto dto = pickupService.getPickup(1L);

        assertNotNull(dto);
        assertEquals(dto.id(), mockPickup1.getId());
        assertEquals(dto.location(), mockPickup1.getLocation());
        verify(pickupRepository, times(1)).findById(1L);
    }

    @Test
    void getPickup_pickupNotFound() {
        when(pickupRepository.findById(3L)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> pickupService.getPickup(3L));
        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
        verify(pickupRepository, times(1)).findById(3L);
    }

    @Test
    void findPickupById() {
        when(pickupRepository.findById(1L)).thenReturn(Optional.of(mockPickup1));

        Pickup pickup = pickupService.findPickupById(1L);

        assertNotNull(pickup);
        assertEquals(pickup.getId(), mockPickup1.getId());
        verify(pickupRepository, times(1)).findById(1L);
    }

    @Test
    void findPickupById_pickupNotFound() {
        when(pickupRepository.findById(3L)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> pickupService.findPickupById(3L));
        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
        verify(pickupRepository, times(1)).findById(3L);
    }

    @Test
    void savePickup() {
        when(pickupRepository.save(mockPickup1)).thenReturn(mockPickup1);

        pickupService.savePickup(mockPickup1);

        verify(pickupRepository, times(1)).save(mockPickup1);
    }
}
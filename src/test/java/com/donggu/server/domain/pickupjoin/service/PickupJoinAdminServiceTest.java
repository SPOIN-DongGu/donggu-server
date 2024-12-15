package com.donggu.server.domain.pickupjoin.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.service.PickupService;
import com.donggu.server.domain.pickupjoin.domain.PickupJoin;
import com.donggu.server.domain.pickupjoin.domain.Status;
import com.donggu.server.domain.pickupjoin.dto.PickupJoinResponseDto;
import com.donggu.server.domain.pickupjoin.repository.PickupJoinRepository;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PickupJoinAdminServiceTest {

    @InjectMocks
    private PickupJoinAdminService pickupJoinAdminService;

    @Mock
    private PickupService pickupService;
    @Mock
    private PickupJoinRepository pickupJoinRepository;

    @Test
    void getAppliedUsersByPickup() {
        Long pickupId = 1L;
        Pickup pickup = Mockito.mock(Pickup.class);
        PickupJoin pickupJoin1 = Mockito.mock(PickupJoin.class);
        PickupJoin pickupJoin2 = Mockito.mock(PickupJoin.class);

        when(pickupService.findPickupById(pickupId)).thenReturn(pickup);
        when(pickupJoinRepository.findAllByPickup(pickup)).thenReturn(List.of(pickupJoin1, pickupJoin2));

        List<PickupJoinResponseDto> result = pickupJoinAdminService.getAppliedUsersByPickup(pickupId);
        assertEquals(2, result.size());
        verify(pickupJoin1, times(1)).getId();
        verify(pickupJoin2, times(1)).getId();
    }

    @Test
    @Transactional
    void handleUserApply_SuccessForApproved() {
        Long id = 1L;
        Pickup pickup = Pickup.builder().id(id).maxParticipant(10).currentParticipant(0).build();
        PickupJoin pickupJoin = PickupJoin.builder().id(id).status(Status.PENDING).build();

        when(pickupService.findPickupById(id)).thenReturn(pickup);
        when(pickupJoinRepository.findById(id)).thenReturn(Optional.of(pickupJoin));

        pickupJoinAdminService.handleUserApply(id, id, Status.APPROVED);

        assertEquals(pickup.getCurrentParticipant(), 1);
        assertEquals(pickupJoin.getStatus(), Status.APPROVED);
        verify(pickupService, times(1)).savePickup(pickup);
        verify(pickupJoinRepository, times(1)).save(pickupJoin);
    }

    @Test
    @Transactional
    void handleUserApply_SuccessForRejected() {
        Long id = 1L;
        Pickup pickup = Pickup.builder().id(id).maxParticipant(10).currentParticipant(0).build();
        PickupJoin pickupJoin = PickupJoin.builder().id(id).status(Status.PENDING).build();

        when(pickupService.findPickupById(id)).thenReturn(pickup);
        when(pickupJoinRepository.findById(id)).thenReturn(Optional.of(pickupJoin));

        pickupJoinAdminService.handleUserApply(id, id, Status.REJECTED);

        assertEquals(pickup.getCurrentParticipant(), 0);
        assertEquals(pickupJoin.getStatus(), Status.REJECTED);
        verify(pickupService, times(1)).savePickup(pickup);
        verify(pickupJoinRepository, times(1)).save(pickupJoin);
    }

    @Test
    void handleUserApply_PickupJoinNotFound() {
        Long id = 1L;
        when(pickupService.findPickupById(id)).thenReturn(mock(Pickup.class));
        when(pickupJoinRepository.findById(id)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(
                        CustomException.class,
                        () -> pickupJoinAdminService.handleUserApply(id, id, Status.APPROVED)
        );

        assertEquals(exception.getErrorCode(), ErrorCode.NOT_FOUND);
        verify(pickupService, times(1)).findPickupById(id);
        verify(pickupJoinRepository, times(1)).findById(id);
    }

    @Test
    void handleUserApply_AlreadyProcessed() {
        Long id = 1L;
        PickupJoin pickupJoin = Mockito.mock(PickupJoin.class);

        when(pickupService.findPickupById(id)).thenReturn(mock(Pickup.class));
        when(pickupJoinRepository.findById(id)).thenReturn(Optional.of(pickupJoin));
        when(pickupJoin.getStatus()).thenReturn(Status.APPROVED);

        CustomException exception = assertThrows(
                CustomException.class,
                () -> pickupJoinAdminService.handleUserApply(id, id, Status.APPROVED)
        );

        assertEquals(exception.getErrorCode(), ErrorCode.ALREADY_PROCESSED);
        verify(pickupService, times(1)).findPickupById(id);
        verify(pickupJoinRepository, times(1)).findById(id);
        verify(pickupJoin, times(1)).getStatus();
    }

    @Test
    void handleUserApply_InvalidJoinUser() {
        Long id = 1L;
        Pickup pickup = Mockito.mock(Pickup.class);
        PickupJoin pickupJoin = Mockito.mock(PickupJoin.class);

        when(pickupService.findPickupById(id)).thenReturn(pickup);
        when(pickupJoinRepository.findById(id)).thenReturn(Optional.of(pickupJoin));
        when(pickupJoin.getStatus()).thenReturn(Status.PENDING);
        when(pickup.getMaxParticipant()).thenReturn(0);
        when(pickup.getCurrentParticipant()).thenReturn(0);

        CustomException exception = assertThrows(
                CustomException.class,
                () -> pickupJoinAdminService.handleUserApply(id, id, Status.APPROVED)
        );

        assertEquals(exception.getErrorCode(), ErrorCode.INVALID_JOIN_USER);
        verify(pickupService, times(1)).findPickupById(id);
        verify(pickupJoinRepository, times(1)).findById(id);
        verify(pickupJoin, times(1)).getStatus();
        verify(pickup, times(1)).getMaxParticipant();
        verify(pickup, times(1)).getCurrentParticipant();
    }
}
package com.bank.transfer.controller;

import com.bank.transfer.dto.AuditDto;
import com.bank.transfer.service.AuditService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("TransferControllerATest Test")
@ExtendWith(MockitoExtension.class)
class AuditControllerTest {

    @InjectMocks
    private AuditController controller;

    @Mock
    private AuditService service;

    @DisplayName("Чтение по ID, позитивный сценарий")
    @Test
    void readByIDPositiveTest() {
        Long id = 1L;
        AuditDto expectedDto = new AuditDto();
        when(service.findById(id)).thenReturn(expectedDto);

        AuditDto actualDto = controller.read(id);

        assertEquals(expectedDto, actualDto);
        verify(service, times(1)).findById(eq(id));
    }

    @DisplayName("Чтение объектов по несуществующим ID, негативный сценарий")
    @Test
    void readByIDNonExistIdNegativeTest() {
        Long id = -1L;
        String errorMessage = "ID is null: null";
        when(service.findById(id)).thenThrow(new EntityNotFoundException(errorMessage));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            controller.read(id);
        });

        assertEquals(errorMessage, exception.getMessage());
        verify(service, times(1)).findById(id);
    }

}
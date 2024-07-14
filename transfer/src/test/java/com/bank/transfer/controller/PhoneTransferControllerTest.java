package com.bank.transfer.controller;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.service.PhoneTransferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("TransferControllerPTTest Test")
@ExtendWith(MockitoExtension.class)
class PhoneTransferControllerTest {

    @InjectMocks
    private PhoneTransferController controller;

    @Mock
    private PhoneTransferService service;

    @DisplayName("Чтение всех объектов по ID, позитивный сценарий")
    @Test
    void readAllByIDPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<PhoneTransferDto> dtoAll = Arrays.asList(new PhoneTransferDto(), new PhoneTransferDto());
        when(service.findAllById(ids)).thenReturn(dtoAll);

        ResponseEntity<List<PhoneTransferDto>> response = controller.readAll(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtoAll, response.getBody());
        verify(service, times(1)).findAllById(ids);
    }

    @DisplayName("Чтение всех объектов по несуществующим ID, негативный сценарий")
    @Test
    void readAllByIDNonExistIdNegativeTest() {
        List<Long> ids = Arrays.asList(null, null);
        String errorMessage = "ID is null: null";
        when(service.findAllById(ids)).thenThrow(new EntityNotFoundException(errorMessage));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            controller.readAll(ids);
        });

        assertEquals(errorMessage, exception.getMessage());
        verify(service, times(1)).findAllById(ids);
    }

    @DisplayName("Чтение по ID, позитивный сценарий")
    @Test
    void readByIDPositiveTest() {
        Long id = 1L;
        PhoneTransferDto expectedDto = new PhoneTransferDto();
        when(service.findById(id)).thenReturn(expectedDto);

        PhoneTransferDto actualDto = controller.read(id);

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

    @DisplayName("Создание объекта, позитивный сценарий")
    @Test
    void createPositiveTest() {
        PhoneTransferDto dto = new PhoneTransferDto();
        when(service.save(dto)).thenReturn(dto);

        ResponseEntity<PhoneTransferDto> response = controller.create(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(service, times(1)).save(dto);
    }

    @DisplayName("ошибка в создании объекта, негативный сценарий")
    @Test
    void createNonExistIdNegativeTest() {
        PhoneTransferDto dto = new PhoneTransferDto();
        String errorMessage = "Entity not found";
        when(service.save(dto)).thenThrow(new EntityNotFoundException(errorMessage));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            controller.create(dto);
        });

        assertEquals(errorMessage, exception.getMessage());
        verify(service, times(1)).save(dto);
    }

    @DisplayName("Изменение объекта по ID, позитивный сценарий")
    @Test
    void testUpdate() {
        Long id = 1L;
        PhoneTransferDto dto = new PhoneTransferDto();
        when(service.update(id, dto)).thenReturn(dto);

        ResponseEntity<PhoneTransferDto> response = controller.update(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(service, times(1)).update(eq(id), eq(dto));
    }

    @DisplayName("ошибка при изменении объекта по ID, негативный сценарий")
    @Test
    void testUpdateNonExistIdNegativeTest() {
        Long id = 1L;
        PhoneTransferDto dto = new PhoneTransferDto();
        String errorMessage = "Entity not found";
        when(service.update(id, dto)).thenThrow(new EntityNotFoundException(errorMessage));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            controller.update(id, dto);
        });

        assertEquals(errorMessage, exception.getMessage());
        verify(service, times(1)).update(eq(id), eq(dto));
    }
}
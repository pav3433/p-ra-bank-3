package com.bank.transfer.controller;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.service.CardTransferService;
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

@DisplayName("TransferControllerCTTest Test")
@ExtendWith(MockitoExtension.class)
class CardTransferControllerTest {

    @InjectMocks
    private CardTransferController controller;

    @Mock
    private CardTransferService service;

    @DisplayName("Чтение всех объектов по ID, позитивный сценарий")
    @Test
    void readAllByIDPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<CardTransferDto> dtoAll = Arrays.asList(new CardTransferDto(), new CardTransferDto());
        when(service.findAllById(ids)).thenReturn(dtoAll);

        ResponseEntity<List<CardTransferDto>> response = controller.readAll(ids);

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
        CardTransferDto expectedDto = new CardTransferDto();
        when(service.findById(id)).thenReturn(expectedDto);

        CardTransferDto actualDto = controller.read(id);

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
        CardTransferDto dto = new CardTransferDto();
        when(service.save(dto)).thenReturn(dto);

        ResponseEntity<CardTransferDto> response = controller.create(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(service, times(1)).save(dto);
    }

    @DisplayName("ошибка в создании объекта, негативный сценарий")
    @Test
    void createNonExistIdNegativeTest() {
        CardTransferDto dto = new CardTransferDto();
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
        CardTransferDto dto = new CardTransferDto();
        when(service.update(id, dto)).thenReturn(dto);

        ResponseEntity<CardTransferDto> response = controller.update(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(service, times(1)).update(eq(id), eq(dto));
    }

    @DisplayName("ошибка при изменении объекта по ID, негативный сценарий")
    @Test
    void testUpdateNonExistIdNegativeTest() {
        Long id = 1L;
        CardTransferDto dto = new CardTransferDto();
        String errorMessage = "Entity not found";
        when(service.update(id, dto)).thenThrow(new EntityNotFoundException(errorMessage));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            controller.update(id, dto);
        });

        assertEquals(errorMessage, exception.getMessage());
        verify(service, times(1)).update(eq(id), eq(dto));
    }
}
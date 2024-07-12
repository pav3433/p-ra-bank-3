package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.service.HistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@DisplayName("HistoryControllerTest Test")
class HistoryControllerTest {

    @Mock
    private HistoryService service;

    @InjectMocks
    private HistoryController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        Long id = 1L;
        HistoryDto dto = new HistoryDto();
        when(service.readById(id)).thenReturn(dto);

        ResponseEntity<HistoryDto> response = controller.read(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() {
        Long id = 999L;
        when(service.readById(id)).thenReturn(null);

        ResponseEntity<HistoryDto> response = controller.read(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @DisplayName("чтение по нескольким id, негативный сценарий")
    void readAllByIdPositiveTest() {
        List<Long> ids = List.of(1L, 2L);
        List<HistoryDto> dtos = List.of(new HistoryDto(), new HistoryDto());
        when(service.readAllById(ids)).thenReturn(dtos);

        ResponseEntity<List<HistoryDto>> response = controller.readAll(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }

    @Test
    @DisplayName("чтение по несуществующим id, негативный сценарий")
    void readAllByNonExistIdNegativeTest() {
        List<Long> ids = List.of(999L);
        when(service.readAllById(ids)).thenReturn(Collections.emptyList());

        ResponseEntity<List<HistoryDto>> response = controller.readAll(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() {
        HistoryDto dto = new HistoryDto();
        when(service.create(dto)).thenReturn(dto);

        ResponseEntity<HistoryDto> response = controller.create(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    @DisplayName("создание, негативный сценарий")
    void createNegativeTest() {
        HistoryDto dto = new HistoryDto();
        when(service.create(dto)).thenReturn(null);

        ResponseEntity<HistoryDto> response = controller.create(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        Long id = 1L;
        HistoryDto dto = new HistoryDto();
        when(service.update(id, dto)).thenReturn(dto);

        ResponseEntity<HistoryDto> response = controller.update(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    @DisplayName("обновление, негативный сценарий")
    void updateNegativeTest() {
        Long id = 999L;
        HistoryDto dto = new HistoryDto();
        when(service.update(id, dto)).thenReturn(null);

        ResponseEntity<HistoryDto> response = controller.update(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}

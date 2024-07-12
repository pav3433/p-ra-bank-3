package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@DisplayName("HistoryServiceImplTest Test")

class HistoryServiceImplTest {

    @InjectMocks
    private HistoryServiceImpl historyService;

    @Mock
    private HistoryMapper mapper;

    @Mock
    private HistoryRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("поиск по id, позитивный сценарий")
    @Test
    void findByIdPositiveTest() {
        Long id = 1L;
        HistoryEntity entity = new HistoryEntity();
        HistoryDto dto = new HistoryDto();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        HistoryDto result = historyService.readById(id);

        assertNotNull(result);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(entity);
    }

    @DisplayName("поиск по несуществующему id, негативный сценарий")
    @Test
    void findByIdNegativeTest() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> historyService.readById(id));
        verify(repository, times(1)).findById(id);
    }

    @DisplayName("поиск всех по id, позитивный сценарий")
    @Test
    void findAllByIdPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L);
        HistoryEntity entity1 = new HistoryEntity();
        HistoryEntity entity2 = new HistoryEntity();
        List<HistoryEntity> entities = Arrays.asList(entity1, entity2);
        HistoryDto dto1 = new HistoryDto();
        HistoryDto dto2 = new HistoryDto();
        List<HistoryDto> dtos = Arrays.asList(dto1, dto2);

        when(repository.findAllById(ids)).thenReturn(entities);
        when(mapper.toListDto(entities)).thenReturn(dtos);

        List<HistoryDto> result = historyService.readAllById(ids);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAllById(ids);
        verify(mapper, times(1)).toListDto(entities);
    }

    @DisplayName("поиск всех по id, негативный сценарий")
    @Test
    void findAllByIdNegativeTest() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<HistoryEntity> entities = Arrays.asList(new HistoryEntity());

        when(repository.findAllById(ids)).thenReturn(entities);

        assertThrows(EntityNotFoundException.class, () -> historyService.readAllById(ids));
        verify(repository, times(1)).findAllById(ids);
    }

    @DisplayName("создание записи, позитивный сценарий")
    @Test
    void createTest() {
        HistoryDto dto = new HistoryDto();
        HistoryEntity entity = new HistoryEntity();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        HistoryDto result = historyService.create(dto);

        assertNotNull(result);
        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toDto(entity);
    }

    @DisplayName("создание записи, негативный сценарий")
    @Test
    void createNegativeTest() {
        HistoryDto dto = new HistoryDto();
        HistoryEntity entity = new HistoryEntity();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenThrow(new RuntimeException("Database error"));


        assertThrows(RuntimeException.class, () -> historyService.create(dto));
        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(0)).toDto(entity);
    }


    @DisplayName("обновление записи, позитивный сценарий")
    @Test
    void updatePositiveTest() {
        Long id = 1L;
        HistoryDto dto = new HistoryDto();
        HistoryEntity entity = new HistoryEntity();
        HistoryEntity updatedEntity = new HistoryEntity();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.mergeToEntity(dto, entity)).thenReturn(updatedEntity);
        when(repository.save(updatedEntity)).thenReturn(updatedEntity);
        when(mapper.toDto(updatedEntity)).thenReturn(dto);

        HistoryDto result = historyService.update(id, dto);

        assertNotNull(result);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).mergeToEntity(dto, entity);
        verify(repository, times(1)).save(updatedEntity);
        verify(mapper, times(1)).toDto(updatedEntity);
    }

    @DisplayName("обновление записи, негативный сценарий")
    @Test
    void updateNegativeTest() {
        Long id = 1L;
        HistoryDto dto = new HistoryDto();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> historyService.update(id, dto));
        verify(repository, times(1)).findById(id);
    }
}

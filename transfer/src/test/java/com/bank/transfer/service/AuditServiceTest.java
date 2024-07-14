package com.bank.transfer.service;

import com.bank.transfer.dto.AuditDto;

import com.bank.transfer.entity.AuditEntity;
import com.bank.transfer.mapper.AuditMapper;
import com.bank.transfer.repository.AuditRepository;
import com.bank.transfer.service.Impl.AuditServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @InjectMocks
    private AuditServiceImpl service;

    @Mock
    private AuditMapper mapper;

    @Mock
    private AuditRepository repository;

    @DisplayName("поиск по id, позитивный сценарий")
    @Test
    void findByIdPositiveTest() {
        Long id = 1L;
        AuditEntity entity = new AuditEntity();
        AuditDto dto = new AuditDto();
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        AuditDto result = service.findById(id);

        assertNotNull(result);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(entity);
    }

    @DisplayName("поиск по несуществующему id, негативный сценарий")
    @Test
    void findByNonExistIdNegativeTest() {
        Long id = 2L;
        String expectedMessage = "Не найден аудит с ID  " + id;

        when(repository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(id);
        });
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(0)).toDto(any(AuditEntity.class));
    }
}
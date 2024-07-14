package com.bank.transfer.service;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import com.bank.transfer.mapper.AccountTransferMapper;
import com.bank.transfer.repository.AccountTransferRepository;
import com.bank.transfer.service.Impl.AccountTransferServiceImpl;
import com.bank.transfer.service.common.EntityNotFoundReturner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountTransferServiceTest {

    @InjectMocks
    private AccountTransferServiceImpl service;

    @Mock
    private AccountTransferMapper mapper;

    @Mock
    private AccountTransferRepository repository;

    @Mock
    private EntityNotFoundReturner entityNotFoundReturner;

    @DisplayName("поиск по id, позитивный сценарий")
    @Test
    void findByIdPositiveTest() {
        Long id = 1L;
        AccountTransferEntity entity = new AccountTransferEntity();
        AccountTransferDto dto = new AccountTransferDto();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        AccountTransferDto result = service.findById(id);

        assertNotNull(result);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(entity);
    }

    @DisplayName("поиск по несуществующему id, негативный сценарий")
    @Test
    void findByNonExistIdNegativeTest() {
        Long id = 2L;
        String expectedMessage = "Сущность не найдена";

        when(repository.findById(id)).thenReturn(Optional.empty());
        when(entityNotFoundReturner.getEntityNotFoundException(anyLong(), anyString()))
                .thenReturn(new EntityNotFoundException(expectedMessage));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(id);
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(repository, times(1)).findById(id);
        verify(mapper, times(0)).toDto(any(AccountTransferEntity.class));
    }

    @DisplayName("поиск по id всех объектов, позитивный сценарий")
    @Test
    void findAllByIdPositiveTest() {
        List<Long> ids = List.of(1L, 2L);
        AccountTransferDto dto1 = new AccountTransferDto();
        AccountTransferDto dto2 = new AccountTransferDto();
        AccountTransferEntity entity1 = new AccountTransferEntity();
        AccountTransferEntity entity2 = new AccountTransferEntity();

        when(repository.findById(1L)).thenReturn(Optional.of(entity1));
        when(repository.findById(2L)).thenReturn(Optional.of(entity2));
        when(mapper.toDto(entity1)).thenReturn(dto1);
        when(mapper.toDto(entity2)).thenReturn(dto2);

        List<AccountTransferDto> result = service.findAllById(ids);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).findById(2L);
    }

    @DisplayName("поиск по id всех объектов, негативный сценарий (не найдена одна из сущностей)")
    @Test
    void findAllByIdNonExistEntityNegativeTest() {
        List<Long> ids = List.of(1L, 2L);
        AccountTransferDto dto1 = new AccountTransferDto();
        AccountTransferEntity entity1 = new AccountTransferEntity();
        String expectedMessage = "Entity not found";

        when(repository.findById(1L)).thenReturn(Optional.of(entity1));
        when(repository.findById(2L)).thenReturn(Optional.empty());
        when(mapper.toDto(entity1)).thenReturn(dto1);
        when(entityNotFoundReturner.getEntityNotFoundException(anyLong(), anyString()))
                .thenReturn(new EntityNotFoundException(expectedMessage));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findAllById(ids);
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).findById(2L);
        verify(mapper, times(1)).toDto(entity1);
    }

    @DisplayName("создание объекта, позитивный сценарий")
    @Test
    void createPositiveTest() {
        AccountTransferDto dto = new AccountTransferDto();
        AccountTransferEntity entity = new AccountTransferEntity();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        AccountTransferDto result = service.save(dto);

        assertNotNull(result);
        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toDto(entity);
    }

    @DisplayName("создание объекта, негативный сценарий (ошибка сохранения)")
    @Test
    void createNegativeTest() {
        AccountTransferDto dto = new AccountTransferDto();
        AccountTransferEntity entity = new AccountTransferEntity();
        String errorMessage = "Error saving entity";

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenThrow(new RuntimeException(errorMessage));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.save(dto);
        });

        assertEquals(errorMessage, exception.getMessage());
        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(0)).toDto(any());
    }


    @DisplayName("обновление объекта по ID, позитивный сценарий")
    @Test
    void updatePositiveTest() {
        Long id = 1L;
        AccountTransferDto dto = new AccountTransferDto();
        AccountTransferEntity entity = new AccountTransferEntity();
        AccountTransferEntity updatedEntity = new AccountTransferEntity();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.mergeToEntity(dto, entity)).thenReturn(updatedEntity);
        when(repository.save(updatedEntity)).thenReturn(updatedEntity);
        when(mapper.toDto(updatedEntity)).thenReturn(dto);

        AccountTransferDto result = service.update(id, dto);

        assertNotNull(result);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).mergeToEntity(dto, entity);
        verify(repository, times(1)).save(updatedEntity);
        verify(mapper, times(1)).toDto(updatedEntity);
    }

    @DisplayName("обновление объекта по несуществующему ID, негативный сценарий")
    @Test
    void updateNonExistIdNegativeTest() {
        Long id = 2L;
        AccountTransferDto dto = new AccountTransferDto();
        String expectedMessage = "Сущность не найдена";

        when(repository.findById(id)).thenReturn(Optional.empty());
        when(entityNotFoundReturner.getEntityNotFoundException(anyLong(), anyString()))
                .thenReturn(new EntityNotFoundException(expectedMessage));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            service.update(id, dto);
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(repository, times(1)).findById(id);
        verify(mapper, times(0)).mergeToEntity(any(), any());
        verify(repository, times(0)).save(any());
        verify(mapper, times(0)).toDto(any());
    }
}

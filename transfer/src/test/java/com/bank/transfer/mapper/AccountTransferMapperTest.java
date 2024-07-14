package com.bank.transfer.mapper;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("TransferMapperATTest Test")
@ExtendWith(MockitoExtension.class)
class AccountTransferMapperTest {

    private final AccountTransferMapper accountTransferMapper = Mappers.getMapper(AccountTransferMapper.class);

    @DisplayName("маппинг в entity")
    @Test
    void toEntityTest() {
        AccountTransferDto dto = AccountTransferDto.builder()
                .accountNumber(1L)
                .amount(BigDecimal.valueOf(2L))
                .purpose("3")
                .accountDetailsId(4L)
                .build();

        AccountTransferEntity entity = accountTransferMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getAccountNumber());
        assertEquals(BigDecimal.valueOf(2L), entity.getAmount());
        assertEquals("3", entity.getPurpose());
        assertEquals(4L, entity.getAccountDetailsId());
        assertNull(entity.getId());
    }

    @DisplayName("маппинг в entity, на вход подан null")
    @Test
    void toEntityNullTest() {
        AccountTransferDto accountTransferDto = null;

        AccountTransferEntity entity = accountTransferMapper.toEntity(accountTransferDto);

        assertNull(entity);
    }

    @DisplayName("маппинг в dto")
    @Test
    void toDtoTest() {
        AccountTransferEntity entity = AccountTransferEntity.builder()
                .accountNumber(1L)
                .amount(BigDecimal.valueOf(2L))
                .purpose("3")
                .accountDetailsId(4L)
                .build();

        AccountTransferDto dto = accountTransferMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getAccountNumber());
        assertEquals(BigDecimal.valueOf(2L), dto.getAmount());
        assertEquals("3", dto.getPurpose());
        assertEquals(4L, dto.getAccountDetailsId());
        assertNull(dto.getId());
    }

    @DisplayName("маппинг в dto, на вход подан null")
    @Test
    void toDtoNullTest() {
        AccountTransferEntity accountTransferEntity = null;

        AccountTransferDto dto = accountTransferMapper.toDto(accountTransferEntity);

        assertNull(dto);
    }

    @DisplayName("слияние в entity")
    @Test
    void mergeToEntityTest() {
        AccountTransferDto dto = AccountTransferDto.builder()
                .id(0L)
                .accountNumber(1L)
                .amount(BigDecimal.valueOf(2L))
                .purpose("3")
                .accountDetailsId(4L)
                .build();
        AccountTransferEntity entity = AccountTransferEntity.builder()
                .id(0L)
                .accountNumber(11L)
                .amount(BigDecimal.valueOf(22L))
                .purpose("33")
                .accountDetailsId(44L)
                .build();

        accountTransferMapper.mergeToEntity(dto, entity);

        assertEquals(0L, entity.getId());
        assertEquals(1L, entity.getAccountNumber());
        assertEquals(BigDecimal.valueOf(2L), entity.getAmount());
        assertEquals("3", entity.getPurpose());
        assertEquals(4L, entity.getAccountDetailsId());
    }

    @DisplayName("слияние в entity, на вход подан null")
    @Test
    void mergeToEntityNullTest() {
        AccountTransferDto accountTransferDto = null;

        AccountTransferEntity entity = accountTransferMapper.mergeToEntity(accountTransferDto, AccountTransferEntity.builder()
                .id(0L)
                .accountNumber(1L)
                .amount(BigDecimal.valueOf(2L))
                .purpose("3")
                .accountDetailsId(4L)
                .build());

        assertEquals(0L, entity.getId());
        assertEquals(1L, entity.getAccountNumber());
        assertEquals(BigDecimal.valueOf(2L), entity.getAmount());
        assertEquals("3", entity.getPurpose());
        assertEquals(4L, entity.getAccountDetailsId());
    }

    @DisplayName("Маппинг List<dto> из List<entity>")
    @Test
    void toDtoListTest() {
        List<AccountTransferEntity> entityList = List.of(AccountTransferEntity.builder()
                        .id(0L)
                        .accountNumber(1L)
                        .amount(BigDecimal.valueOf(2L))
                        .purpose("3")
                        .accountDetailsId(4L)
                        .build()
                , AccountTransferEntity.builder()
                        .id(1L)
                        .accountNumber(11L)
                        .amount(BigDecimal.valueOf(22L))
                        .purpose("33")
                        .accountDetailsId(44L)
                        .build());

        List<AccountTransferDto> dtoList = accountTransferMapper.toDtoList(entityList);

        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(0L, dtoList.get(0).getId());
        assertEquals(1L, dtoList.get(0).getAccountNumber());
        assertEquals(BigDecimal.valueOf(2L), dtoList.get(0).getAmount());
        assertEquals("3", dtoList.get(0).getPurpose());
        assertEquals(4L, dtoList.get(0).getAccountDetailsId());

        assertEquals(1L, dtoList.get(1).getId());
        assertEquals(11L, dtoList.get(1).getAccountNumber());
        assertEquals(BigDecimal.valueOf(22L), dtoList.get(1).getAmount());
        assertEquals("33", dtoList.get(1).getPurpose());
        assertEquals(44L, dtoList.get(1).getAccountDetailsId());
    }

    @DisplayName("Маппинг List<dto> из List<entity>, на вход подан null")
    @Test
    void toDtoListNullTest() {
        List<AccountTransferEntity> entityList = new ArrayList<>();
        entityList.add(null);
        entityList.add(null);

        List<AccountTransferDto> dtoList = accountTransferMapper.toDtoList(entityList);

        assertEquals(entityList.size(), dtoList.size());
        assertNull(dtoList.get(0));
        assertNull(dtoList.get(1));
    }
}
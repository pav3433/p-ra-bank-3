package com.bank.transfer.mapper;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransferEntity;
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

@DisplayName("TransferMapperCTTest Test")
@ExtendWith(MockitoExtension.class)
class CardTransferMapperTest {

    private final CardTransferMapper cardTransferMapper = Mappers.getMapper(CardTransferMapper.class);

    @DisplayName("маппинг в entity")
    @Test
    void toEntityTest() {
        CardTransferDto dto = CardTransferDto.builder()
                .cardNumber(1L)
                .amount(BigDecimal.valueOf(2L))
                .purpose("3")
                .accountDetailsId(4L)
                .build();

        CardTransferEntity entity = cardTransferMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getCardNumber());
        assertEquals(BigDecimal.valueOf(2L), entity.getAmount());
        assertEquals("3", entity.getPurpose());
        assertEquals(4L, entity.getAccountDetailsId());
        assertNull(entity.getId());
    }

    @DisplayName("маппинг в entity, на вход подан null")
    @Test
    void toEntityNullTest() {
        CardTransferDto CardTransferDto = null;

        CardTransferEntity entity = cardTransferMapper.toEntity(CardTransferDto);

        assertNull(entity);
    }

    @DisplayName("маппинг в dto")
    @Test
    void toDtoTest() {
        CardTransferEntity entity = CardTransferEntity.builder()
                .cardNumber(1L)
                .amount(BigDecimal.valueOf(2L))
                .purpose("3")
                .accountDetailsId(4L)
                .build();

        CardTransferDto dto = cardTransferMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getCardNumber());
        assertEquals(BigDecimal.valueOf(2L), dto.getAmount());
        assertEquals("3", dto.getPurpose());
        assertEquals(4L, dto.getAccountDetailsId());
        assertNull(dto.getId());
    }

    @DisplayName("маппинг в dto, на вход подан null")
    @Test
    void toDtoNullTest() {
        CardTransferEntity CardTransferEntity = null;

        CardTransferDto dto = cardTransferMapper.toDto(CardTransferEntity);

        assertNull(dto);
    }

    @DisplayName("слияние в entity")
    @Test
    void mergeToEntityTest() {
        CardTransferDto dto = CardTransferDto.builder()
                .id(0L)
                .cardNumber(1L)
                .amount(BigDecimal.valueOf(2L))
                .purpose("3")
                .accountDetailsId(4L)
                .build();
        CardTransferEntity entity = CardTransferEntity.builder()
                .id(0L)
                .cardNumber(11L)
                .amount(BigDecimal.valueOf(22L))
                .purpose("33")
                .accountDetailsId(44L)
                .build();

        cardTransferMapper.mergeToEntity(dto, entity);

        assertEquals(0L, entity.getId());
        assertEquals(1L, entity.getCardNumber());
        assertEquals(BigDecimal.valueOf(2L), entity.getAmount());
        assertEquals("3", entity.getPurpose());
        assertEquals(4L, entity.getAccountDetailsId());
    }

    @DisplayName("слияние в entity, на вход подан null")
    @Test
    void mergeToEntityNullTest() {
        CardTransferDto CardTransferDto = null;

        CardTransferEntity entity = cardTransferMapper.mergeToEntity(CardTransferDto, CardTransferEntity.builder()
                .id(0L)
                .cardNumber(1L)
                .amount(BigDecimal.valueOf(2L))
                .purpose("3")
                .accountDetailsId(4L)
                .build());

        assertEquals(0L, entity.getId());
        assertEquals(1L, entity.getCardNumber());
        assertEquals(BigDecimal.valueOf(2L), entity.getAmount());
        assertEquals("3", entity.getPurpose());
        assertEquals(4L, entity.getAccountDetailsId());
    }

    @DisplayName("Маппинг List<dto> из List<entity>")
    @Test
    void toDtoListTest() {
        List<CardTransferEntity> entityList = List.of(CardTransferEntity.builder()
                        .id(0L)
                        .cardNumber(1L)
                        .amount(BigDecimal.valueOf(2L))
                        .purpose("3")
                        .accountDetailsId(4L)
                        .build()
                , CardTransferEntity.builder()
                        .id(1L)
                        .cardNumber(11L)
                        .amount(BigDecimal.valueOf(22L))
                        .purpose("33")
                        .accountDetailsId(44L)
                        .build());

        List<CardTransferDto> dtoList = cardTransferMapper.toDtoList(entityList);

        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(0L, dtoList.get(0).getId());
        assertEquals(1L, dtoList.get(0).getCardNumber());
        assertEquals(BigDecimal.valueOf(2L), dtoList.get(0).getAmount());
        assertEquals("3", dtoList.get(0).getPurpose());
        assertEquals(4L, dtoList.get(0).getAccountDetailsId());

        assertEquals(1L, dtoList.get(1).getId());
        assertEquals(11L, dtoList.get(1).getCardNumber());
        assertEquals(BigDecimal.valueOf(22L), dtoList.get(1).getAmount());
        assertEquals("33", dtoList.get(1).getPurpose());
        assertEquals(44L, dtoList.get(1).getAccountDetailsId());
    }

    @DisplayName("Маппинг List<dto> из List<entity>, на вход подан null")
    @Test
    void toDtoListNullTest() {
        List<CardTransferEntity> entityList = new ArrayList<>();
        entityList.add(null);
        entityList.add(null);

        List<CardTransferDto> dtoList = cardTransferMapper.toDtoList(entityList);

        assertEquals(entityList.size(), dtoList.size());
        assertNull(dtoList.get(0));
        assertNull(dtoList.get(1));
    }
}
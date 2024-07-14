package com.bank.transfer.mapper;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
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

@DisplayName("TransferMapperPTTest Test")
@ExtendWith(MockitoExtension.class)
class PhoneTransferMapperTest {

    private final PhoneTransferMapper phoneTransferMapper = Mappers.getMapper(PhoneTransferMapper.class);

    @DisplayName("маппинг в entity")
    @Test
    void toEntityTest() {
        PhoneTransferDto dto = PhoneTransferDto.builder()
                .phoneNumber(1L)
                .amount(BigDecimal.valueOf(2L))
                .purpose("3")
                .accountDetailsId(4L)
                .build();

        PhoneTransferEntity entity = phoneTransferMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getPhoneNumber());
        assertEquals(BigDecimal.valueOf(2L), entity.getAmount());
        assertEquals("3", entity.getPurpose());
        assertEquals(4L, entity.getAccountDetailsId());
        assertNull(entity.getId());
    }

    @DisplayName("маппинг в entity, на вход подан null")
    @Test
    void toEntityNullTest() {
        PhoneTransferDto PhoneTransferDto = null;

        PhoneTransferEntity entity = phoneTransferMapper.toEntity(PhoneTransferDto);

        assertNull(entity);
    }

    @DisplayName("маппинг в dto")
    @Test
    void toDtoTest() {
        PhoneTransferEntity entity = PhoneTransferEntity.builder()
                .phoneNumber(1L)
                .amount(BigDecimal.valueOf(2L))
                .purpose("3")
                .accountDetailsId(4L)
                .build();

        PhoneTransferDto dto = phoneTransferMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getPhoneNumber());
        assertEquals(BigDecimal.valueOf(2L), dto.getAmount());
        assertEquals("3", dto.getPurpose());
        assertEquals(4L, dto.getAccountDetailsId());
        assertNull(dto.getId());
    }

    @DisplayName("маппинг в dto, на вход подан null")
    @Test
    void toDtoNullTest() {
        PhoneTransferEntity PhoneTransferEntity = null;

        PhoneTransferDto dto = phoneTransferMapper.toDto(PhoneTransferEntity);

        assertNull(dto);
    }

    @DisplayName("слияние в entity")
    @Test
    void mergeToEntityTest() {
        PhoneTransferDto dto = PhoneTransferDto.builder()
                .id(0L)
                .phoneNumber(1L)
                .amount(BigDecimal.valueOf(2L))
                .purpose("3")
                .accountDetailsId(4L)
                .build();
        PhoneTransferEntity entity = PhoneTransferEntity.builder()
                .id(0L)
                .phoneNumber(11L)
                .amount(BigDecimal.valueOf(22L))
                .purpose("33")
                .accountDetailsId(44L)
                .build();

        phoneTransferMapper.mergeToEntity(dto, entity);

        assertEquals(0L, entity.getId());
        assertEquals(1L, entity.getPhoneNumber());
        assertEquals(BigDecimal.valueOf(2L), entity.getAmount());
        assertEquals("3", entity.getPurpose());
        assertEquals(4L, entity.getAccountDetailsId());
    }

    @DisplayName("слияние в entity, на вход подан null")
    @Test
    void mergeToEntityNullTest() {
        PhoneTransferDto PhoneTransferDto = null;

        PhoneTransferEntity entity = phoneTransferMapper.mergeToEntity(PhoneTransferDto, PhoneTransferEntity.builder()
                .id(0L)
                .phoneNumber(1L)
                .amount(BigDecimal.valueOf(2L))
                .purpose("3")
                .accountDetailsId(4L)
                .build());

        assertEquals(0L, entity.getId());
        assertEquals(1L, entity.getPhoneNumber());
        assertEquals(BigDecimal.valueOf(2L), entity.getAmount());
        assertEquals("3", entity.getPurpose());
        assertEquals(4L, entity.getAccountDetailsId());
    }

    @DisplayName("Маппинг List<dto> из List<entity>")
    @Test
    void toDtoListTest() {
        List<PhoneTransferEntity> entityList = List.of(PhoneTransferEntity.builder()
                        .id(0L)
                        .phoneNumber(1L)
                        .amount(BigDecimal.valueOf(2L))
                        .purpose("3")
                        .accountDetailsId(4L)
                        .build()
                , PhoneTransferEntity.builder()
                        .id(1L)
                        .phoneNumber(11L)
                        .amount(BigDecimal.valueOf(22L))
                        .purpose("33")
                        .accountDetailsId(44L)
                        .build());

        List<PhoneTransferDto> dtoList = phoneTransferMapper.toDtoList(entityList);

        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals(0L, dtoList.get(0).getId());
        assertEquals(1L, dtoList.get(0).getPhoneNumber());
        assertEquals(BigDecimal.valueOf(2L), dtoList.get(0).getAmount());
        assertEquals("3", dtoList.get(0).getPurpose());
        assertEquals(4L, dtoList.get(0).getAccountDetailsId());

        assertEquals(1L, dtoList.get(1).getId());
        assertEquals(11L, dtoList.get(1).getPhoneNumber());
        assertEquals(BigDecimal.valueOf(22L), dtoList.get(1).getAmount());
        assertEquals("33", dtoList.get(1).getPurpose());
        assertEquals(44L, dtoList.get(1).getAccountDetailsId());
    }

    @DisplayName("Маппинг List<dto> из List<entity>, на вход подан null")
    @Test
    void toDtoListNullTest() {
        List<PhoneTransferEntity> entityList = new ArrayList<>();
        entityList.add(null);
        entityList.add(null);

        List<PhoneTransferDto> dtoList = phoneTransferMapper.toDtoList(entityList);

        assertEquals(entityList.size(), dtoList.size());
        assertNull(dtoList.get(0));
        assertNull(dtoList.get(1));
    }
}
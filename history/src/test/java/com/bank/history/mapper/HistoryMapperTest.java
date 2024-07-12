package com.bank.history.mapper;


import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
@DisplayName("HistoryMapperTest Test")

class HistoryMapperTest {

    private final HistoryMapper historyMapper = Mappers.getMapper(HistoryMapper.class);

    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        HistoryDto dto = HistoryDto.builder()
                .transferAuditId(1L)
                .profileAuditId(2L)
                .accountAuditId(3L)
                .antiFraudAuditId(4L)
                .publicBankInfoAuditId(5L)
                .authorizationAuditId(6L)
                .build();

        HistoryEntity entity = historyMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getTransferAuditId());
        assertEquals(2L, entity.getProfileAuditId());
        assertEquals(3L, entity.getAccountAuditId());
        assertEquals(4L, entity.getAntiFraudAuditId());
        assertEquals(5L, entity.getPublicBankInfoAuditId());
        assertEquals(6L, entity.getAuthorizationAuditId());
        assertNull(entity.getId());  // Ensure the ID is ignored and not set
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null")
    void toEntityNullTest() {
        HistoryDto dto = null;
        HistoryEntity entity = historyMapper.toEntity(dto);
        assertNull(entity);
    }

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        HistoryEntity entity = new HistoryEntity();
        entity.setId(1L);
        entity.setTransferAuditId(1L);
        entity.setProfileAuditId(2L);
        entity.setAccountAuditId(3L);
        entity.setAntiFraudAuditId(4L);
        entity.setPublicBankInfoAuditId(5L);
        entity.setAuthorizationAuditId(6L);

        HistoryDto dto = historyMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(1L, dto.getTransferAuditId());
        assertEquals(2L, dto.getProfileAuditId());
        assertEquals(3L, dto.getAccountAuditId());
        assertEquals(4L, dto.getAntiFraudAuditId());
        assertEquals(5L, dto.getPublicBankInfoAuditId());
        assertEquals(6L, dto.getAuthorizationAuditId());
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    void toDtoNullTest() {
        HistoryEntity entity = null;
        HistoryDto dto = historyMapper.toDto(entity);
        assertNull(dto);
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        HistoryDto dto = HistoryDto.builder()
                .transferAuditId(1L)
                .profileAuditId(2L)
                .accountAuditId(3L)
                .antiFraudAuditId(4L)
                .publicBankInfoAuditId(5L)
                .authorizationAuditId(6L)
                .build();

        HistoryEntity entity = new HistoryEntity();
        entity.setId(1L);
        entity.setTransferAuditId(10L);
        entity.setProfileAuditId(20L);
        entity.setAccountAuditId(30L);
        entity.setAntiFraudAuditId(40L);
        entity.setPublicBankInfoAuditId(50L);
        entity.setAuthorizationAuditId(60L);

        historyMapper.mergeToEntity(dto, entity);
        assertEquals(1L, entity.getId()); // Ensure the ID remains unchanged
        assertEquals(1L, entity.getTransferAuditId());
        assertEquals(2L, entity.getProfileAuditId());
        assertEquals(3L, entity.getAccountAuditId());
        assertEquals(4L, entity.getAntiFraudAuditId());
        assertEquals(5L, entity.getPublicBankInfoAuditId());
        assertEquals(6L, entity.getAuthorizationAuditId());
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        HistoryDto dto = null;
        HistoryEntity entity = new HistoryEntity();
        historyMapper.mergeToEntity(dto, entity);

        assertNull(entity.getTransferAuditId());
        assertNull(entity.getProfileAuditId());
        assertNull(entity.getAccountAuditId());
        assertNull(entity.getAntiFraudAuditId());
        assertNull(entity.getPublicBankInfoAuditId());
        assertNull(entity.getAuthorizationAuditId());
    }

    @Test
    @DisplayName("маппинг списка в dto")
    void toListDtoTest() {
        HistoryEntity entity1 = new HistoryEntity();
        entity1.setId(1L);
        entity1.setTransferAuditId(1L);
        entity1.setProfileAuditId(2L);
        entity1.setAccountAuditId(3L);
        entity1.setAntiFraudAuditId(4L);
        entity1.setPublicBankInfoAuditId(5L);
        entity1.setAuthorizationAuditId(6L);

        HistoryEntity entity2 = new HistoryEntity();
        entity2.setId(2L);
        entity2.setTransferAuditId(11L);
        entity2.setProfileAuditId(22L);
        entity2.setAccountAuditId(33L);
        entity2.setAntiFraudAuditId(44L);
        entity2.setPublicBankInfoAuditId(55L);
        entity2.setAuthorizationAuditId(66L);

        List<HistoryEntity> entityList = Arrays.asList(entity1, entity2);
        List<HistoryDto> dtoList = historyMapper.toListDto(entityList);

        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());

        assertEquals(1L, dtoList.get(0).getId());
        assertEquals(1L, dtoList.get(0).getTransferAuditId());
        assertEquals(2L, dtoList.get(0).getProfileAuditId());
        assertEquals(3L, dtoList.get(0).getAccountAuditId());
        assertEquals(4L, dtoList.get(0).getAntiFraudAuditId());
        assertEquals(5L, dtoList.get(0).getPublicBankInfoAuditId());
        assertEquals(6L, dtoList.get(0).getAuthorizationAuditId());

        assertEquals(2L, dtoList.get(1).getId());
        assertEquals(11L, dtoList.get(1).getTransferAuditId());
        assertEquals(22L, dtoList.get(1).getProfileAuditId());
        assertEquals(33L, dtoList.get(1).getAccountAuditId());
        assertEquals(44L, dtoList.get(1).getAntiFraudAuditId());
        assertEquals(55L, dtoList.get(1).getPublicBankInfoAuditId());
        assertEquals(66L, dtoList.get(1).getAuthorizationAuditId());
    }

    @Test
    @DisplayName("маппинг списка в dto, на вход подан null")
    void toListDtoNullTest() {
        List<HistoryEntity> entityList = null;
        List<HistoryDto> dtoList = historyMapper.toListDto(entityList);
        assertNull(dtoList);
    }
}

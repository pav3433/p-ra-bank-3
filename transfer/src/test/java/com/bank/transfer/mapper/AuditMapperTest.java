package com.bank.transfer.mapper;

import com.bank.transfer.dto.AuditDto;
import com.bank.transfer.entity.AuditEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("TransferMapperATest Test")
@ExtendWith(MockitoExtension.class)
class AuditMapperTest {

    AuditMapper auditMapper = Mappers.getMapper(AuditMapper.class);

    @DisplayName("маппинг в dto")
    @Test
    void toDtoTest() {
        AuditEntity entity = AuditEntity.builder()
                .entityType("1")
                .operationType("2")
                .createdBy("3")
                .modifiedBy("4")
                .createdAt(new Timestamp(5L))
                .modifiedAt(new Timestamp(6L))
                .newEntityJson("7")
                .entityJson("8")
                .build();

        AuditDto dto = auditMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals("1", dto.getEntityType());
        assertEquals("2", dto.getOperationType());
        assertEquals("3", dto.getCreatedBy());
        assertEquals("4", dto.getModifiedBy());
        assertEquals(new Timestamp(5L), dto.getCreatedAt());
        assertEquals(new Timestamp(6L), dto.getModifiedAt());
        assertEquals("7", dto.getNewEntityJson());
        assertEquals("8", dto.getEntityJson());
        assertNull(dto.getId());
    }

    @DisplayName("маппинг в dto, на вход подан null")
    @Test
    void toDtoNullTest() {
        AuditEntity auditEntity = null;

        AuditDto dto = auditMapper.toDto(auditEntity);

        assertNull(dto);
    }
}
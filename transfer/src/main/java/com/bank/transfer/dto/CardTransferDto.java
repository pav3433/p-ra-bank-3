package com.bank.transfer.dto;

import com.bank.transfer.entity.CardTransferEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ДТО {@link CardTransferEntity}
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardTransferDto implements Serializable {

    Long id;

    Long cardNumber;

    BigDecimal amount;

    String purpose;
    Long accountDetailsId;
}

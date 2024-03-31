package com.elmar.loanguru.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvestmentDto {

    private Long id;
    private Long userId;
    private Long loanId;
    private BigDecimal amount;
    private LocalDateTime investedAt;
}

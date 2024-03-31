package com.elmar.loanguru.model.dto;

import com.elmar.loanguru.model.enums.LoanStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LoanDto {

    private Long id;
    private String createdBy;
    private LocalDateTime createdAt;
    private BigDecimal amount;
    private BigDecimal targetAmount;
    private double interestRate;
    private LocalDate endDate;
    private LoanStatus status;
}

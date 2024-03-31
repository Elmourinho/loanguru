package com.elmar.loanguru.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanInputDto {

    @NotNull(message = "Target amount is required")
    @Positive(message = "Target amount must be positive")
    private BigDecimal targetAmount;

    @NotNull(message = "Interest rate is required")
    @Positive(message = "Interest rate must be positive")
    private double interestRate;

    @Future
    private LocalDate endDate;
}

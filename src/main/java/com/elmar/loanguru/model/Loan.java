package com.elmar.loanguru.model;

import com.elmar.loanguru.model.enums.LoanStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    private LocalDateTime createdAt;

    private BigDecimal amount;

    @Column(name = "target_amount")
    private BigDecimal targetAmount;

    @Column(name = "interest_rate")
    private double interestRate;

    private LocalDate endDate;

    private LoanStatus status;

    public BigDecimal getMaxInvestAmount() {
        return targetAmount.subtract(amount);
    }
}

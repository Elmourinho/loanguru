package com.elmar.loanguru.repository;

import com.elmar.loanguru.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT l FROM Loan l WHERE l.status = com.elmar.loanguru.model.enums.LoanStatus.ACTIVE " +
            "ORDER BY l.interestRate ASC, l.endDate ASC")
    List<Loan> findAllLoans();
}

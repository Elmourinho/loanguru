package com.elmar.loanguru.controller;

import com.elmar.loanguru.model.dto.InvestmentDto;
import com.elmar.loanguru.model.dto.InvestmentInputDto;
import com.elmar.loanguru.model.dto.LoanDto;
import com.elmar.loanguru.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class InvestmentController {

    private final InvestmentService investmentService;

    @PostMapping("/user/{userId}/loan/{loanId}/invest")
    public ResponseEntity<InvestmentDto> invest(
            @Valid @RequestBody InvestmentInputDto investmentInputDto,
            @PathVariable String userId,
            @PathVariable String loanId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(investmentService.invest(userId, loanId, investmentInputDto));
    }

    @GetMapping("/users/{userId}/loans")
    public List<LoanDto> getLoansInvestedByUser(@PathVariable String userId) {
        return investmentService.getLoansInvestedByUser(userId);
    }
}

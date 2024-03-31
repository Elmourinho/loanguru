package com.elmar.loanguru.controller;

import com.elmar.loanguru.model.dto.LoanDto;
import com.elmar.loanguru.model.dto.LoanInputDto;
import com.elmar.loanguru.service.LoanService;
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
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<LoanDto> addL(@Valid @RequestBody LoanInputDto loan, @PathVariable String userId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loanService.add(userId, loan));
    }

    @GetMapping
    public ResponseEntity<List<LoanDto>> getAll(){
        return ResponseEntity.ok(loanService.getAll());
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<LoanDto> get(@PathVariable String loanId){
        return ResponseEntity.ok(loanService.get(loanId));
    }
}

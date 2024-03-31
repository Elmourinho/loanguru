package com.elmar.loanguru.service;

import com.elmar.loanguru.model.dto.LoanDto;
import com.elmar.loanguru.model.dto.LoanInputDto;

import java.util.List;

public interface LoanService {

    LoanDto add(String userId, LoanInputDto loanInputDto);

    LoanDto get(String loanId);

    List<LoanDto> getAll();
}

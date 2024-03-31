package com.elmar.loanguru.service;

import com.elmar.loanguru.model.dto.InvestmentDto;
import com.elmar.loanguru.model.dto.InvestmentInputDto;
import com.elmar.loanguru.model.dto.LoanDto;

import java.util.List;

public interface InvestmentService {

    InvestmentDto invest(String userId, String loanId, InvestmentInputDto investmentInputDto);

    List<LoanDto> getLoansInvestedByUser(String userId);
}

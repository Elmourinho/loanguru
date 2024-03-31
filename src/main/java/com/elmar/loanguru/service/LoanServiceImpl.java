package com.elmar.loanguru.service;

import com.elmar.loanguru.error.Errors;
import com.elmar.loanguru.exception.LoanException;
import com.elmar.loanguru.model.Loan;
import com.elmar.loanguru.model.User;
import com.elmar.loanguru.model.dto.LoanDto;
import com.elmar.loanguru.model.dto.LoanInputDto;
import com.elmar.loanguru.model.enums.LoanStatus;
import com.elmar.loanguru.repository.LoanRepository;
import com.elmar.loanguru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public LoanDto add(String userId, LoanInputDto loanInputDto) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new LoanException(Errors.USER_NOT_FOUND, Map.of("id", userId)));

        Loan loan = modelMapper.map(loanInputDto, Loan.class);
        loan.setCreatedBy(user);
        loan.setCreatedAt(LocalDateTime.now());
        loan.setAmount(BigDecimal.ZERO);
        loan.setStatus(LoanStatus.ACTIVE);

        Loan savedLoan = loanRepository.save(loan);

        LoanDto loanDto = modelMapper.map(savedLoan, LoanDto.class);
        loanDto.setCreatedBy(userId);

        return loanDto;
    }

    @Override
    public LoanDto get(String loanId) {
        Loan loan = loanRepository.findById(Long.valueOf(loanId))
                .orElseThrow(() -> new LoanException(Errors.USER_NOT_FOUND, Map.of("id", loanId)));

        return modelMapper.map(loan, LoanDto.class);
    }

    @Override
    public List<LoanDto> getAll() {
        return loanRepository.findAllLoans()
                .stream()
                .map(loan -> modelMapper.map(loan, LoanDto.class))
                .toList();
    }
}

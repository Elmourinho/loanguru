package com.elmar.loanguru.service;

import com.elmar.loanguru.error.Errors;
import com.elmar.loanguru.exception.LoanException;
import com.elmar.loanguru.model.Investment;
import com.elmar.loanguru.model.Loan;
import com.elmar.loanguru.model.User;
import com.elmar.loanguru.model.dto.InvestmentDto;
import com.elmar.loanguru.model.dto.InvestmentInputDto;
import com.elmar.loanguru.model.dto.LoanDto;
import com.elmar.loanguru.model.enums.LoanStatus;
import com.elmar.loanguru.repository.InvestmentRepository;
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
public class InvestmentServiceImpl implements InvestmentService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final InvestmentRepository investmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public InvestmentDto invest(String userId, String loanId, InvestmentInputDto investmentInputDto) {

        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new LoanException(Errors.USER_NOT_FOUND, Map.of("id", userId)));

        Loan loan = loanRepository.findById(Long.valueOf(loanId))
                .orElseThrow(() -> new LoanException(Errors.LOAN_NOT_FOUND, Map.of("id", loanId)));

        if (investmentInputDto.getAmount().compareTo(loan.getMaxInvestAmount()) > 0) {
            throw new LoanException(Errors.EXCEEDS_MAX_AMOUNT);
        }

        BigDecimal investmentAmount = investmentInputDto.getAmount();

        Investment investment = createInvestment(user, loan, investmentAmount);

        updateLoan(loan, investmentAmount);

        return modelMapper.map(investment, InvestmentDto.class);
    }

    @Override
    public List<LoanDto> getLoansInvestedByUser(String userId) {
        Long userIdValue = Long.valueOf(userId);
        return investmentRepository.findByUserId(userIdValue).stream()
                .map(investment -> {
                    LoanDto loanDto = modelMapper.map(investment.getLoan(), LoanDto.class);
                    loanDto.setCreatedBy(userId);
                    return loanDto;
                })
                .toList();
    }

    Investment createInvestment(User user, Loan loan, BigDecimal amount) {
        Investment investment = new Investment();
        investment.setUser(user);
        investment.setLoan(loan);
        investment.setAmount(amount);
        investment.setInvestedAt(LocalDateTime.now());
        return investmentRepository.save(investment);
    }

    void updateLoan(Loan loan, BigDecimal investmentAmount) {
        BigDecimal newLoanAmount = loan.getAmount().add(investmentAmount);
        loan.setAmount(newLoanAmount);
        if (newLoanAmount.compareTo(loan.getTargetAmount()) == 0) {
            loan.setStatus(LoanStatus.CLOSED);
        }
        loanRepository.save(loan);
    }
}

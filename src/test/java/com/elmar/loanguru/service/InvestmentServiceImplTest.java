package com.elmar.loanguru.service;

import com.elmar.loanguru.exception.LoanException;
import com.elmar.loanguru.model.Investment;
import com.elmar.loanguru.model.Loan;
import com.elmar.loanguru.model.User;
import com.elmar.loanguru.model.dto.InvestmentDto;
import com.elmar.loanguru.model.dto.InvestmentInputDto;
import com.elmar.loanguru.model.enums.LoanStatus;
import com.elmar.loanguru.repository.InvestmentRepository;
import com.elmar.loanguru.repository.LoanRepository;
import com.elmar.loanguru.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvestmentServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private InvestmentRepository investmentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private InvestmentServiceImpl investmentService;

    @Test
    void investShouldThrowExceptionWhenUserNotFound() {
        // given
        String userId = "1";
        String loanId = "1";
        InvestmentInputDto investmentInputDto = new InvestmentInputDto();
        investmentInputDto.setAmount(BigDecimal.valueOf(100));

        // when
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(LoanException.class, () -> investmentService.invest(userId, loanId, investmentInputDto));

        verify(userRepository, times(1)).findById(anyLong());
        verify(loanRepository, never()).findById(anyLong());
        verify(investmentRepository, never()).save(any());
    }

    @Test
    void investShouldUpdateLoanProperly() {
        // given
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setAmount(BigDecimal.valueOf(900)); // Initial amount
        loan.setTargetAmount(BigDecimal.valueOf(1000)); // Target amount
        loan.setStatus(LoanStatus.ACTIVE);

        BigDecimal investmentAmount = BigDecimal.valueOf(100);

        // when
        investmentService.updateLoan(loan, investmentAmount);

        // then
        assertEquals(LoanStatus.CLOSED, loan.getStatus());
        assertEquals(BigDecimal.valueOf(1000), loan.getAmount());
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void investShouldProceedOkWithProperValues() {
        // given
        String userId = "1";
        String loanId = "1";
        InvestmentInputDto investmentInputDto = new InvestmentInputDto();
        investmentInputDto.setAmount(BigDecimal.valueOf(100));

        User user = new User();
        user.setId(1L);

        Loan loan = new Loan();
        loan.setId(1L);
        loan.setAmount(BigDecimal.valueOf(900)); // Initial amount
        loan.setTargetAmount(BigDecimal.valueOf(1000)); // Target amount
        loan.setStatus(LoanStatus.ACTIVE);

        BigDecimal investmentAmount = BigDecimal.valueOf(100);

        Investment savedInvestment = new Investment();
        savedInvestment.setId(1L);
        savedInvestment.setUser(user);
        savedInvestment.setLoan(loan);
        savedInvestment.setAmount(investmentAmount);
        savedInvestment.setInvestedAt(LocalDateTime.now());

        InvestmentDto expectedInvestmentDto = new InvestmentDto();
        expectedInvestmentDto.setId(1L);

        // when
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));
        when(investmentRepository.save(any(Investment.class))).thenReturn(savedInvestment);
        when(modelMapper.map(savedInvestment, InvestmentDto.class)).thenReturn(expectedInvestmentDto);

        // then
        InvestmentDto result = investmentService.invest(userId, loanId, investmentInputDto);

        assertEquals(expectedInvestmentDto.getId(), result.getId());

        verify(userRepository, times(1)).findById(anyLong());
        verify(loanRepository, times(1)).findById(anyLong());
        verify(investmentRepository, times(1)).save(any(Investment.class));
        verify(modelMapper, times(1)).map(savedInvestment, InvestmentDto.class);
    }
}
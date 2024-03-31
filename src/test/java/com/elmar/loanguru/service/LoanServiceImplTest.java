package com.elmar.loanguru.service;

import com.elmar.loanguru.exception.LoanException;
import com.elmar.loanguru.model.Loan;
import com.elmar.loanguru.model.User;
import com.elmar.loanguru.model.dto.LoanDto;
import com.elmar.loanguru.model.dto.LoanInputDto;
import com.elmar.loanguru.model.enums.LoanStatus;
import com.elmar.loanguru.repository.LoanRepository;
import com.elmar.loanguru.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
    void addLoanShouldWorkOkWhenUserFound() {

        //given
        String userId = "1";
        LoanInputDto loanInputDto = new LoanInputDto();
        loanInputDto.setTargetAmount(BigDecimal.valueOf(1000));
        loanInputDto.setInterestRate(10);
        loanInputDto.setEndDate(LocalDate.MAX);

        User user = new User();
        user.setId(1L);

        Loan savedLoan = new Loan();
        savedLoan.setId(1L);
        savedLoan.setCreatedBy(user);
        savedLoan.setCreatedAt(LocalDateTime.now());
        savedLoan.setAmount(BigDecimal.ZERO);
        savedLoan.setStatus(LoanStatus.ACTIVE);

        LoanDto expectedLoanDto = new LoanDto();
        expectedLoanDto.setId(1L);
        expectedLoanDto.setCreatedBy(userId);

        // when
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(loanRepository.save(any(Loan.class))).thenReturn(savedLoan);
        when(modelMapper.map(any(LoanInputDto.class), eq(Loan.class))).thenReturn(savedLoan);
        when(modelMapper.map(savedLoan, LoanDto.class)).thenReturn(expectedLoanDto);

        LoanDto result = loanService.add(userId, loanInputDto);

        // then
        assertEquals(expectedLoanDto.getId(), result.getId());
        assertEquals(expectedLoanDto.getCreatedBy(), result.getCreatedBy());
    }

    @Test
    void addLoanShouldThrowExceptionWhenUserNotFound() {
        // given
        String userId = "1";
        LoanInputDto loanInputDto = new LoanInputDto();
        loanInputDto.setTargetAmount(BigDecimal.valueOf(1000));
        loanInputDto.setInterestRate(10);
        loanInputDto.setEndDate(LocalDate.MAX);

        // when
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        assertThrows(LoanException.class, () -> loanService.add(userId, loanInputDto));
    }

    @Test
    void getLoanShouldRetrieveLoanWhenLoanFound() {
        // given
        String loanId = "1";

        Loan loan = new Loan();
        loan.setId(1L);

        LoanDto expectedLoanDto = new LoanDto();
        expectedLoanDto.setId(1L);

        // when
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(modelMapper.map(loan, LoanDto.class)).thenReturn(expectedLoanDto);

        LoanDto result = loanService.get(loanId);

        // then
        assertEquals(expectedLoanDto.getId(), result.getId());
    }

    @Test
    void getLoanShouldThrowExceptionWhenLoanNotFound() {
        // given
        String loanId = "1";

        // when
        when(loanRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        assertThrows(LoanException.class, () -> loanService.get(loanId));
    }

    @Test
     void getAllLoansShouldRetrieveAllLoans() {
        // given
        List<Loan> loans = List.of(new Loan(), new Loan());

        List<LoanDto> expectedLoanDtoList = List.of(new LoanDto(), new LoanDto());

        // when
        when(loanRepository.findAllLoans()).thenReturn(loans);
        when(modelMapper.map(any(Loan.class), eq(LoanDto.class))).thenReturn(new LoanDto());

        List<LoanDto> result = loanService.getAll();

        // then
        assertEquals(expectedLoanDtoList.size(), result.size());
    }

}
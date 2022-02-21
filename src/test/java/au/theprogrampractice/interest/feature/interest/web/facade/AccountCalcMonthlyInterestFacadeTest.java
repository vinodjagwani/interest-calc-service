package au.theprogrampractice.interest.feature.interest.web.facade;

import au.theprogrampractice.interest.feature.interest.repository.entity.BalanceInterest;
import au.theprogrampractice.interest.feature.interest.service.BalanceInterestService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountCalcMonthlyInterestFacadeTest {

    @Mock
    BalanceInterestService balanceInterestService;

    @InjectMocks
    AccountCalcMonthlyInterestFacade accountCalcMonthlyInterestFacade;

    @Test
    void testCalculateMonthlyInterest() {
        when(balanceInterestService.findByBalanceDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(Flux.just(new BalanceInterest()));
        accountCalcMonthlyInterestFacade.calculateMonthlyInterest(3);
        verify(balanceInterestService, atLeastOnce()).findByBalanceDateBetween(any(LocalDate.class), any(LocalDate.class));
    }
}

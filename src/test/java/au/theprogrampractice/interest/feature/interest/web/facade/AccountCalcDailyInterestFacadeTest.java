package au.theprogrampractice.interest.feature.interest.web.facade;


import au.theprogrampractice.interest.config.InterestRatesConfigProp;
import au.theprogrampractice.interest.config.KafkaConfigProp;
import au.theprogrampractice.interest.feature.interest.repository.entity.BalanceInterest;
import au.theprogrampractice.interest.feature.interest.service.BalanceInterestService;
import au.theprogrampractice.interest.feature.interest.web.dto.AccountDailyInterestRequest;
import au.theprogrampractice.interest.feature.kafka.producer.PublishMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountCalcDailyInterestFacadeTest {

    @Mock
    ObjectMapper objectMapper;

    @Mock
    PublishMessage publishMessage;

    @Mock
    KafkaConfigProp kafkaConfigProp;

    @Mock
    BalanceInterestService balanceInterestService;

    @Mock
    InterestRatesConfigProp interestRatesConfigProp;

    @InjectMocks
    AccountCalcDailyInterestFacade accountCalcDailyInterestFacade;

    @Test
    void testProcessAccountEndOfDayBalances() {
        when(interestRatesConfigProp.getDailyRatePercentage()).thenReturn(BigDecimal.ONE);
        when(balanceInterestService.saveAll(any(Flux.class))).thenReturn(Flux.just(new BalanceInterest()));
        accountCalcDailyInterestFacade.processAccountEndOfDayBalances(LocalDate.MIN, List.of(buildAccountDailyInterestRequest()));
        verify(balanceInterestService, atLeastOnce()).saveAll(any(Flux.class));
    }

    private AccountDailyInterestRequest buildAccountDailyInterestRequest() {
        final AccountDailyInterestRequest request = new AccountDailyInterestRequest();
        request.setBalance(BigDecimal.TEN);
        request.setBsb("2432");
        request.setIdentification("2342");
        return request;
    }

}

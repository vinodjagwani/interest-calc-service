package au.theprogrampractice.interest.feature.interest.web.controller;

import au.theprogrampractice.interest.feature.interest.web.dto.AccountDailyInterestResponse;
import au.theprogrampractice.interest.feature.interest.web.dto.AccountMonthlyInterestResponse;
import au.theprogrampractice.interest.feature.interest.web.facade.AccountCalcDailyInterestFacade;
import au.theprogrampractice.interest.feature.interest.web.facade.AccountCalcMonthlyInterestFacade;
import au.theprogrampractice.interest.utils.MockUtils;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static java.util.Optional.ofNullable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


@FieldDefaults(level = AccessLevel.PRIVATE)
@WebFluxTest(value = AccountBalanceInterestController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
class AccountBalanceInterestControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    AccountCalcDailyInterestFacade accountCalcDailyInterestFacade;

    @MockBean
    AccountCalcMonthlyInterestFacade accountCalcMonthlyInterestFacade;

    @Test
    @SneakyThrows
    void testProcessAccountEndOfDayBalances() {
        final String request = ofNullable(MockUtils.getResource("mock/account-balance-calc-daily-interest-request.json", String.class)).orElse("");
        final AccountDailyInterestResponse[] response = ofNullable(MockUtils.getResource("mock/account-balance-calc-daily-interest-response.json", AccountDailyInterestResponse[].class)).orElse(new AccountDailyInterestResponse[]{});
        when(accountCalcDailyInterestFacade.processAccountEndOfDayBalances(any(LocalDate.class), anyList())).thenReturn(Flux.just(response));
        webTestClient.put().uri("/v1/accounts/interest/calc-daily-interest/{balanceDate}", LocalDate.now())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].bsb").isEqualTo("182182")
                .jsonPath("$.[0].identification").isEqualTo("182182")
                .jsonPath("$.[0].balance").isEqualTo(50)
                .jsonPath("$.[0].interest").isEqualTo(0.00273972600);
    }

    @Test
    @SneakyThrows
    void testCalculateMonthlyInterest() {
        final AccountMonthlyInterestResponse[] response = ofNullable(MockUtils.getResource("mock/account-balance-calc-monthly-interest-response.json", AccountMonthlyInterestResponse[].class)).orElse(new AccountMonthlyInterestResponse[]{});
        when(accountCalcMonthlyInterestFacade.calculateMonthlyInterest(any(Integer.class))).thenReturn(Flux.just(response));
        webTestClient.put().uri("/v1/accounts/interest/calc-monthly-interest/{month}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].bsb").isEqualTo("182182")
                .jsonPath("$.[0].identification").isEqualTo("182182")
                .jsonPath("$.[0].monthlyInterest").isEqualTo(0.00652054788);
    }

}

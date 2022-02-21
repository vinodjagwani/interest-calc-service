package au.theprogrampractice.interest.feature.account.web.controller;

import au.theprogrampractice.interest.feature.account.web.dto.AccountOpenCreateRequest;
import au.theprogrampractice.interest.feature.account.web.dto.AccountOpenCreateResponse;
import au.theprogrampractice.interest.feature.account.web.facade.AccountOpenFacade;
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
import reactor.core.publisher.Mono;

import static java.util.Optional.ofNullable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@FieldDefaults(level = AccessLevel.PRIVATE)
@WebFluxTest(value = AccountController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
class AccountControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    AccountOpenFacade accountOpenFacade;

    @Test
    @SneakyThrows
    void testProcessAccountOpening() {
        final String request = ofNullable(MockUtils.getResource("mock/account-open-request.json", String.class)).orElse("");
        final AccountOpenCreateResponse response = ofNullable(MockUtils.getResource("mock/account-open-request.json", AccountOpenCreateResponse.class)).orElse(new AccountOpenCreateResponse());
        when(accountOpenFacade.processAccountOpening(any(AccountOpenCreateRequest.class))).thenReturn(Mono.just(response));
        webTestClient.post().uri("/v1/accounts/open-account")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.bsb").isEqualTo("182182")
                .jsonPath("$.identification").isEqualTo("11122233333");
    }
}

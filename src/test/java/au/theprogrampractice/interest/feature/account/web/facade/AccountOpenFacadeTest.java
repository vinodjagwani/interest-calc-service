package au.theprogrampractice.interest.feature.account.web.facade;

import au.theprogrampractice.interest.feature.account.repository.entity.Account;
import au.theprogrampractice.interest.feature.account.service.AccountService;
import au.theprogrampractice.interest.feature.account.web.dto.AccountOpenCreateRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountOpenFacadeTest {

    @Mock
    AccountService accountService;

    @InjectMocks
    AccountOpenFacade accountOpenFacade;

    @Test
    void testProcessAccountOpening() {
        when(accountService.findByBsbAndIdentification(any(String.class), any(String.class))).thenReturn(Mono.just(new Account()));
        when(accountService.save(any(Account.class))).thenReturn(Mono.just(new Account()));
        accountOpenFacade.processAccountOpening(buildAccountOpenCreateRequest());
        verify(accountService, atLeastOnce()).save(any(Account.class));
    }

    private AccountOpenCreateRequest buildAccountOpenCreateRequest() {
        final AccountOpenCreateRequest request = new AccountOpenCreateRequest();
        request.setBsb("34");
        request.setIdentification("4r4");
        return request;
    }
}

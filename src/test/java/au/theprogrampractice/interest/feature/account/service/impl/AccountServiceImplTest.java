package au.theprogrampractice.interest.feature.account.service.impl;

import au.theprogrampractice.interest.feature.account.repository.AccountRepository;
import au.theprogrampractice.interest.feature.account.repository.entity.Account;
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
class AccountServiceImplTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    void testFindByBsbAndIdentification() {
        when(accountRepository.findByBsbAndIdentification(any(String.class), any(String.class))).thenReturn(Mono.just(new Account()));
        accountService.findByBsbAndIdentification("1234", "3424");
        verify(accountRepository, atLeastOnce()).findByBsbAndIdentification(any(String.class), any(String.class));
    }

    @Test
    void testSave() {
        when(accountRepository.save(any(Account.class))).thenReturn(Mono.just(new Account()));
        accountService.save(buildAccount());
        verify(accountRepository, atLeastOnce()).save(any(Account.class));
    }

    private Account buildAccount() {
        final Account account = new Account();
        account.setBsb("134");
        account.setIdentification("45");
        return account;
    }
}

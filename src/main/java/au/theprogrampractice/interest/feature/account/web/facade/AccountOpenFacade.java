package au.theprogrampractice.interest.feature.account.web.facade;

import au.theprogrampractice.interest.feature.account.repository.entity.Account;
import au.theprogrampractice.interest.feature.account.service.AccountService;
import au.theprogrampractice.interest.feature.account.web.dto.AccountOpenCreateRequest;
import au.theprogrampractice.interest.feature.account.web.dto.AccountOpenCreateResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountOpenFacade {

    AccountService accountService;

    @Transactional(rollbackFor = Exception.class)
    public Mono<AccountOpenCreateResponse> processAccountOpening(final AccountOpenCreateRequest request) {
        log.debug("Start calling saving account details with request [{}]", request);
        return accountService.findByBsbAndIdentification(request.getBsb(), request.getIdentification())
                .switchIfEmpty(accountService.save(buildAccount(request)))
                .map(this::buildAccountOpenCreateResponse);

    }

    private Account buildAccount(final AccountOpenCreateRequest request) {
        final Account account = new Account();
        account.setBsb(request.getBsb());
        account.setIdentification(request.getIdentification());
        account.setOpeningDate(request.getOpeningDate());
        account.setNew(true);
        return account;
    }

    private AccountOpenCreateResponse buildAccountOpenCreateResponse(final Account account) {
        final AccountOpenCreateResponse response = new AccountOpenCreateResponse();
        response.setBsb(account.getBsb());
        response.setIdentification(account.getIdentification());
        return response;
    }
}

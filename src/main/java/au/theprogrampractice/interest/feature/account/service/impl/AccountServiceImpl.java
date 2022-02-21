package au.theprogrampractice.interest.feature.account.service.impl;

import au.theprogrampractice.interest.feature.account.repository.AccountRepository;
import au.theprogrampractice.interest.feature.account.repository.entity.Account;
import au.theprogrampractice.interest.feature.account.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<Account> findByBsbAndIdentification(final String bsb, final String identification) {
        log.debug("Start querying account details with bsb [{}] and identification [{}]", bsb, identification);
        return accountRepository.findByBsbAndIdentification(bsb, identification);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Account> save(final Account account) {
        log.debug("Start saving account details with accountId [{}]", ofNullable(account.getId()));
        return accountRepository.save(account);
    }
}

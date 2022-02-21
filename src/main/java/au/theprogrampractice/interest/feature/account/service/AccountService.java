package au.theprogrampractice.interest.feature.account.service;

import au.theprogrampractice.interest.feature.account.repository.entity.Account;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<Account> save(final Account account);

    Mono<Account> findByBsbAndIdentification(final String bsb, final String identification);
}

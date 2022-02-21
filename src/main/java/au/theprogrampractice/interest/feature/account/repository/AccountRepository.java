package au.theprogrampractice.interest.feature.account.repository;

import au.theprogrampractice.interest.feature.account.repository.entity.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveCrudRepository<Account, String> {

    Mono<Account> findByBsbAndIdentification(final String bsb, final String identification);
}

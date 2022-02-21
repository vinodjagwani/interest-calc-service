package au.theprogrampractice.interest.feature.interest.repository;

import au.theprogrampractice.interest.feature.interest.repository.entity.BalanceInterest;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface BalanceInterestRepository extends ReactiveCrudRepository<BalanceInterest, String> {

    Flux<BalanceInterest> findByBalanceDateBetween(final LocalDate startDate, final LocalDate endDate);

    Mono<Void> deleteByBsbAndIdentificationAndBalanceDate(final String bsb, final String identification, final LocalDate balanceDate);

}

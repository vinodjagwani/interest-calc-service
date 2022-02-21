package au.theprogrampractice.interest.feature.interest.service;

import au.theprogrampractice.interest.feature.interest.repository.entity.BalanceInterest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface BalanceInterestService {

    Flux<BalanceInterest> saveAll(final Flux<BalanceInterest> balanceInterests);

    Flux<BalanceInterest> findByBalanceDateBetween(final LocalDate startDate, final LocalDate endDate);

    Mono<Void> deleteByBsbAndIdentificationAndBalanceDate(final String bsb, final String identification, final LocalDate balanceDate);


}

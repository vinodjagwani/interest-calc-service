package au.theprogrampractice.interest.feature.interest.service.impl;

import au.theprogrampractice.interest.feature.interest.repository.BalanceInterestRepository;
import au.theprogrampractice.interest.feature.interest.repository.entity.BalanceInterest;
import au.theprogrampractice.interest.feature.interest.service.BalanceInterestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BalanceInterestServiceImpl implements BalanceInterestService {

    BalanceInterestRepository balanceInterestRepository;

    @Override
    @Transactional(readOnly = true)
    public Flux<BalanceInterest> findByBalanceDateBetween(final LocalDate startDate, final LocalDate endDate) {
        log.debug("Start querying balanceInterests with startDate [{}] and endDate [{}]", startDate, endDate);
        return balanceInterestRepository.findByBalanceDateBetween(startDate, endDate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Flux<BalanceInterest> saveAll(final Flux<BalanceInterest> balanceInterests) {
        log.debug("Start saving balanceInterests with balanceInterests [{}]", balanceInterests.log());
        return balanceInterestRepository.saveAll(balanceInterests);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> deleteByBsbAndIdentificationAndBalanceDate(final String bsb, final String identification, final LocalDate balanceDate) {
        log.debug("Start deleting balanceInterests with bsb [{}], identification [{}] and balanceDate [{}]", bsb, identification, balanceDate);
        return balanceInterestRepository.deleteByBsbAndIdentificationAndBalanceDate(bsb, identification, balanceDate);
    }
}

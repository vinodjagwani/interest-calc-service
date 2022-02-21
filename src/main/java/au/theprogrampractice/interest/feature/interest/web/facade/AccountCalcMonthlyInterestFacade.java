package au.theprogrampractice.interest.feature.interest.web.facade;

import au.theprogrampractice.interest.feature.interest.repository.entity.BalanceInterest;
import au.theprogrampractice.interest.feature.interest.service.BalanceInterestService;
import au.theprogrampractice.interest.feature.interest.web.dto.AccountMonthlyInterestResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountCalcMonthlyInterestFacade {

    BalanceInterestService balanceInterestService;

    public Flux<AccountMonthlyInterestResponse> calculateMonthlyInterest(final Integer month) {
        log.debug("Start calling monthly interest rates for the month of [{}]", month);
        final List<AccountMonthlyInterestResponse> result = Lists.newArrayList();
        final Map<String, BigDecimal> monthlyBalanceInterestMap = new HashMap<>();
        return balanceInterestService.findByBalanceDateBetween(getStartDateOfMonth(month), getEndDateOfMonth(month))
                .collectMultimap(BalanceInterest::getBsb, balanceInterest -> balanceInterest)
                .map(mp -> {
                    mp.keySet().forEach(k -> mp.get(k).forEach(balanceInterest -> {
                        final String bsbIdentificationKey = balanceInterest.getBsb().concat("-").concat(balanceInterest.getIdentification());
                        if (monthlyBalanceInterestMap.containsKey(bsbIdentificationKey)) {
                            monthlyBalanceInterestMap.put(bsbIdentificationKey, monthlyBalanceInterestMap.get(bsbIdentificationKey).add(balanceInterest.getInterest()));
                        } else {
                            monthlyBalanceInterestMap.put(bsbIdentificationKey, balanceInterest.getInterest());
                        }
                    }));
                    monthlyBalanceInterestMap.keySet().forEach(key -> result.add(buildAccountMonthlyInterestResponse(key, monthlyBalanceInterestMap)));
                    return result;
                }).thenMany(Flux.fromIterable(result));

    }

    private AccountMonthlyInterestResponse buildAccountMonthlyInterestResponse(final String key, final Map<String, BigDecimal> map) {
        final AccountMonthlyInterestResponse response = new AccountMonthlyInterestResponse();
        final String[] splitKey = key.split("-");
        response.setMonthlyInterest(map.get(key));
        response.setBsb(splitKey[0]);
        response.setIdentification(splitKey[1]);
        return response;
    }

    private LocalDate getStartDateOfMonth(final Integer month) {
        return LocalDate.now().withMonth(month).withDayOfMonth(1);
    }

    private LocalDate getEndDateOfMonth(final Integer month) {
        final int totalMonthDays = LocalDate.now().withMonth(month).lengthOfMonth();
        return LocalDate.now().withMonth(month).withDayOfMonth(totalMonthDays);
    }
}

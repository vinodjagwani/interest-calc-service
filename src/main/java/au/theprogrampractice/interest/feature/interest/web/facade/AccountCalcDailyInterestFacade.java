package au.theprogrampractice.interest.feature.interest.web.facade;


import au.theprogrampractice.interest.config.InterestRatesConfigProp;
import au.theprogrampractice.interest.config.KafkaConfigProp;
import au.theprogrampractice.interest.feature.interest.repository.entity.BalanceInterest;
import au.theprogrampractice.interest.feature.interest.service.BalanceInterestService;
import au.theprogrampractice.interest.feature.interest.web.dto.AccountDailyInterestRequest;
import au.theprogrampractice.interest.feature.interest.web.dto.AccountDailyInterestResponse;
import au.theprogrampractice.interest.feature.kafka.producer.PublishMessage;
import au.theprogrampractice.interest.feature.kafka.producer.dto.MessagePayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountCalcDailyInterestFacade {

    ObjectMapper objectMapper;

    PublishMessage publishMessage;

    KafkaConfigProp kafkaConfigProp;

    BalanceInterestService balanceInterestService;

    InterestRatesConfigProp interestRatesConfigProp;

    @Transactional(rollbackFor = Exception.class)
    public Flux<AccountDailyInterestResponse> processAccountEndOfDayBalances(final LocalDate balanceDate, final List<AccountDailyInterestRequest> request) {
        log.debug("Start calling end of day balance interest rates for balanceDate [{}] with accountDetails [{}]", balanceDate, request);
        final List<AccountDailyInterestResponse> result = Lists.newArrayList();
        final List<BalanceInterest> balanceInterests = Lists.newArrayList();
        return Flux.fromIterable(request)
                .flatMap(accountDailyInterest -> balanceInterestService.deleteByBsbAndIdentificationAndBalanceDate(accountDailyInterest.getBsb(), accountDailyInterest.getIdentification(), balanceDate)
                        .flatMap(emptyResult -> Mono.empty()))
                .switchIfEmpty(balanceInterestService.saveAll(buildBalanceInterests(balanceDate, request, result, balanceInterests)))
                .doOnSubscribe(rs -> sendMessageToKafka(result)).thenMany(Flux.fromIterable(result));

    }


    private Flux<BalanceInterest> buildBalanceInterests(final LocalDate balanceDate, final List<AccountDailyInterestRequest> request,
                                                        final List<AccountDailyInterestResponse> result,
                                                        final List<BalanceInterest> balanceInterests) {
        emptyIfNull(request).forEach(accountDetail -> {
            final BigDecimal interest = calculateDailyInterestRate(accountDetail.getBalance());
            balanceInterests.add(balanceInterest(accountDetail, interest, balanceDate));
            result.add(buildAccountDailyInterestResponse(accountDetail, interest));
        });
        return Flux.fromIterable(balanceInterests);
    }

    private BalanceInterest balanceInterest(final AccountDailyInterestRequest accountDetail, final BigDecimal interest, final LocalDate balanceDate) {
        final BalanceInterest balanceInterest = new BalanceInterest();
        balanceInterest.setInterest(interest);
        balanceInterest.setBalanceDate(balanceDate);
        balanceInterest.setBsb(accountDetail.getBsb());
        balanceInterest.setIdentification(accountDetail.getIdentification());
        balanceInterest.setBalance(accountDetail.getBalance());
        return balanceInterest;
    }

    private AccountDailyInterestResponse buildAccountDailyInterestResponse(final AccountDailyInterestRequest request, final BigDecimal interest) {
        final AccountDailyInterestResponse response = new AccountDailyInterestResponse();
        response.setBsb(request.getBsb());
        response.setIdentification(request.getIdentification());
        response.setBalance(request.getBalance());
        response.setInterest(interest);
        return response;
    }

    private BigDecimal calculateDailyInterestRate(final BigDecimal balance) {
        final BigDecimal interestPercentile = interestRatesConfigProp.getDailyRatePercentage().divide(BigDecimal.valueOf(100), MathContext.DECIMAL32);
        final BigDecimal timePeriod = BigDecimal.valueOf(BigDecimal.ONE.intValue()).divide(BigDecimal.valueOf(365), MathContext.DECIMAL32);
        return balance.multiply(interestPercentile.multiply(timePeriod));
    }

    private void sendMessageToKafka(List<AccountDailyInterestResponse> result) {
        try {
            publishMessage.sendToKafka(kafkaConfigProp.getTopics().stream().findFirst().orElse("demo-topic"),
                    Flux.just(MessagePayload.builder().metaData(objectMapper.writeValueAsString(result)).build()));
        } catch (final Exception ex) {
            log.error("Unable to parse message for topic: ", ex);
        }
    }
}

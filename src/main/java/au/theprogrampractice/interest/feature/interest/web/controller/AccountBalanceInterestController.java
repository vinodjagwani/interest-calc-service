package au.theprogrampractice.interest.feature.interest.web.controller;

import au.theprogrampractice.interest.annotation.DefaultApiResponse;
import au.theprogrampractice.interest.feature.interest.web.dto.AccountDailyInterestRequest;
import au.theprogrampractice.interest.feature.interest.web.dto.AccountDailyInterestResponse;
import au.theprogrampractice.interest.feature.interest.web.dto.AccountMonthlyInterestResponse;
import au.theprogrampractice.interest.feature.interest.web.facade.AccountCalcDailyInterestFacade;
import au.theprogrampractice.interest.feature.interest.web.facade.AccountCalcMonthlyInterestFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/accounts/interest")
@Api(tags = "Interest", description = "Interest")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountBalanceInterestController {

    AccountCalcDailyInterestFacade accountCalcDailyInterestFacade;

    AccountCalcMonthlyInterestFacade accountCalcMonthlyInterestFacade;

    @DefaultApiResponse
    @PutMapping(value = "/calc-daily-interest/{balanceDate}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "End of balances Api", nickname = "processAccountEndOfDayBalances", notes = "This API is used for calculate end of balances")
    public ResponseEntity<Flux<AccountDailyInterestResponse>> processAccountEndOfDayBalances(@PathVariable("balanceDate") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate balanceDate,
                                                                                             @Valid @RequestBody final List<@NotNull AccountDailyInterestRequest> request) {
        return new ResponseEntity<>(accountCalcDailyInterestFacade.processAccountEndOfDayBalances(balanceDate, request), HttpStatus.OK);
    }

    @DefaultApiResponse
    @PutMapping(value = "/calc-monthly-interest/{month}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Calculate Monthly Api", nickname = "calculateMonthlyInterest", notes = "This API is used for calculate monthly interest")
    public ResponseEntity<Flux<AccountMonthlyInterestResponse>> calculateMonthlyInterest(@PathVariable("month") @NotNull final Integer month) {
        return new ResponseEntity<>(accountCalcMonthlyInterestFacade.calculateMonthlyInterest(month), HttpStatus.OK);
    }
}

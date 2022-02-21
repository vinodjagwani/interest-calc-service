package au.theprogrampractice.interest.feature.account.web.controller;

import au.theprogrampractice.interest.annotation.DefaultApiResponse;
import au.theprogrampractice.interest.feature.account.web.dto.AccountOpenCreateRequest;
import au.theprogrampractice.interest.feature.account.web.dto.AccountOpenCreateResponse;
import au.theprogrampractice.interest.feature.account.web.facade.AccountOpenFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/accounts")
@Api(tags = "Accounts", description = "Accounts")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountController {

    AccountOpenFacade accountOpenFacade;

    @DefaultApiResponse
    @PostMapping(value = "/open-account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Account Open Api", nickname = "processAccountOpening", notes = "This API is used for open an account")
    public ResponseEntity<Mono<AccountOpenCreateResponse>> processAccountOpening(@Valid @RequestBody @NotNull final AccountOpenCreateRequest request) {
        return new ResponseEntity<>(accountOpenFacade.processAccountOpening(request), HttpStatus.CREATED);
    }

}

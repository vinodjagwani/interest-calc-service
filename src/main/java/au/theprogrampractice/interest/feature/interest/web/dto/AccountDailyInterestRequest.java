package au.theprogrampractice.interest.feature.interest.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "AccountDailyInterestRequest", description = "AccountDailyInterestRequest contains request fields for daily interest of balance")
public class AccountDailyInterestRequest {

    @ApiModelProperty(position = 1, name = "bsb", example = "182182")
    String bsb;

    @ApiModelProperty(position = 2, name = "identification", example = "182182")
    String identification;

    @ApiModelProperty(position = 3, name = "balance", example = "182182")
    BigDecimal balance;


}
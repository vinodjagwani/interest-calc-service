package au.theprogrampractice.interest.feature.interest.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "AccountDailyInterestResponse", description = "AccountDailyInterestResponse contains response fields for daily interest of balance")
public class AccountDailyInterestResponse {

    @ApiModelProperty(position = 1, name = "bsb", example = "1324")
    String bsb;

    @ApiModelProperty(position = 2, name = "identification", example = "1324")
    String identification;

    @ApiModelProperty(position = 3, name = "balance", example = "1324")
    BigDecimal balance;

    @ApiModelProperty(position = 4, name = "balance", example = "1324")
    BigDecimal interest;

}

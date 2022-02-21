package au.theprogrampractice.interest.feature.interest.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "AccountMonthlyInterestResponse", description = "AccountMonthlyInterestResponse contains response fields monthly interest of account")
public class AccountMonthlyInterestResponse {

    @ApiModelProperty(position = 1, name = "bsb", example = "182182")
    String bsb;

    @ApiModelProperty(position = 2, name = "identification", example = "182182")
    String identification;

    @ApiModelProperty(position = 3, name = "monthlyInterest", example = "90")
    BigDecimal monthlyInterest;


}

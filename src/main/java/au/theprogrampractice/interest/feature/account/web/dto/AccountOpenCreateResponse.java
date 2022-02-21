package au.theprogrampractice.interest.feature.account.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "AccountOpenCreateResponse", description = "AccountOpenCreateResponse contains response fields for creating opening of account")
public class AccountOpenCreateResponse {

    @ApiModelProperty(position = 1, name = "bsb", example = "182182")
    String bsb;

    @ApiModelProperty(position = 2, name = "identification", example = "1112223333")
    String identification;

}

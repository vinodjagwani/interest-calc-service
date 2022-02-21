package au.theprogrampractice.interest.feature.account.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "AccountOpenCreateRequest", description = "AccountOpenCreateRequest contains request fields for creating opening of account")
public class AccountOpenCreateRequest {

    @NotEmpty(message = "bsb can't be null or empty")
    @ApiModelProperty(position = 1, name = "bsb", example = "182182")
    String bsb;

    @NotEmpty(message = "identification can't be null or empty")
    @ApiModelProperty(position = 2, name = "identification", example = "111222333")
    String identification;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "openingDate can't be null or empty")
    @ApiModelProperty(position = 3, name = "openingDate", example = "2021-09-13")
    LocalDate openingDate;
}

package au.theprogrampractice.interest.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Validated
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "interest-rates")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InterestRatesConfigProp {

    @NotNull
    BigDecimal dailyRatePercentage;

    @NotNull
    BigDecimal monthlyRatePercentage;
}

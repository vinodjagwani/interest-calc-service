package au.theprogrampractice.interest.feature.interest.repository.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Setter
@Getter
@Table("balance_interest")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BalanceInterest implements Persistable<String> {

    @Id
    String balanceInterestId = UUID.randomUUID().toString();

    String bsb;

    String identification;

    BigDecimal balance;

    BigDecimal interest;

    LocalDate balanceDate;

    @Transient
    boolean isNew = true;

    @Override
    public String getId() {
        return balanceInterestId;
    }

    @Override
    @Transient
    public boolean isNew() {
        return isNew;
    }
}

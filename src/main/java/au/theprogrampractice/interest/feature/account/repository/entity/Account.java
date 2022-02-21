package au.theprogrampractice.interest.feature.account.repository.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Setter
@Getter
@Table("accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account implements Persistable<String> {

    @Id
    String bsb;

    String identification;

    LocalDate openingDate;

    @Transient
    boolean isNew = true;

    @Override
    public String getId() {
        return bsb.concat(",").concat(identification);
    }

    @Override
    @Transient
    public boolean isNew() {
        return isNew;
    }
}

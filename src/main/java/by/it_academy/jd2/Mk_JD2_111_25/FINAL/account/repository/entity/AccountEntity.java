package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.entity;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.enums.EAccountType;
import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account", schema = "fin_app")
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "dt_create")
    private Long dtCreate;

    @Column(name = "dt_update")
    private Long dtUpdate;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "balance")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EAccountType type;

    @Column(name = "currency_id")
    private String currency;

    @Column(name = "user_id")
    private String user;
}

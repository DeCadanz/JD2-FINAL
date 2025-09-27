package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.enums.EAccountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Account {
    private String uuid;
    private Long dtCreate;
    private Long dtUpdate;
    private String title;
    private String description;
    private BigDecimal balance;
    private EAccountType type;
    private String currency;
}

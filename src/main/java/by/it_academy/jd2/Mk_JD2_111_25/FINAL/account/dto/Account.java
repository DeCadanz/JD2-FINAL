package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.enums.EAccountType;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.annotations.ValidCurrency;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private BigDecimal balance;
    private EAccountType type;
    @ValidCurrency
    private String currency;
}

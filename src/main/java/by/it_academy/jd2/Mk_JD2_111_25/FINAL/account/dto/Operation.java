package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.annotations.ValidCurrency;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.annotations.ValidCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Operation {
    private String uuid;
    private Long dtCreate;
    private Long dtUpdate;
    private Long date;
    @NotBlank
    private String description;
    @NotBlank
    @ValidCategory
    private String category;
    private BigDecimal value;
    @NotBlank
    @ValidCurrency
    private String currency;
}

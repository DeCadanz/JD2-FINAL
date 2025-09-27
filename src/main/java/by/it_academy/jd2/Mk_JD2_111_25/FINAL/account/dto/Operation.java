package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto;

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
    private String description;
    private String category;
    private BigDecimal value;
    private String currency;
}

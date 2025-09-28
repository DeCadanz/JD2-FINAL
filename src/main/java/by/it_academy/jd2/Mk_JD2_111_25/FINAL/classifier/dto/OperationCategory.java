package by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OperationCategory {
    private String uuid;
    private Long dtCreate;
    private Long dtUpdate;
    @NotEmpty
    private String title;
}

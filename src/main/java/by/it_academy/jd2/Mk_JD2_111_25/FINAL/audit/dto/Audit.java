package by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.enums.EEssenceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Audit {
    private String uuid;
    private Long dt_create;
    private String userId;
    private String text;
    private EEssenceType type;
    private String id;
}

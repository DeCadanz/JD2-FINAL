package by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuditUser {
    private String uuid;
    private Long dtCreate;
    private UserAudit userAudit;
    private String text;
    private EEssenceType type;
    private String id;
}
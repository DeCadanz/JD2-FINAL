package by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.enums.ERole;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.enums.EStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
public class User {
    private String uuid;
    private Long dt_create;
    private Long dt_update;
    private String mail;
    private String fio;
    private ERole role;
    private EStatus status;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}

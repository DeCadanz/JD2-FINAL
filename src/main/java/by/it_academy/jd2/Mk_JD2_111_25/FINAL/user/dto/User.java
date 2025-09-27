package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.ERole;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.EStatus;
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
    //private String password;
}

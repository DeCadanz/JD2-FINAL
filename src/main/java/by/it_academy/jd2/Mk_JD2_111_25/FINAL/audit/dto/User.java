package by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.ERole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

    @Getter
    @Setter
    @ToString
    public class User {
        private String uuid;
        private String mail;
        private String fio;
        private ERole role;
}

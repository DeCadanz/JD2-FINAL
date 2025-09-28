package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.ERole;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.EStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRegister {
    @NotBlank
    @Email
    private String mail;
    @NotBlank
    private String fio;
    private ERole role;
    private EStatus status;
    @NotBlank
    private String password;

}

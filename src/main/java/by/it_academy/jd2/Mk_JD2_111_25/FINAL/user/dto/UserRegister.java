package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.ERole;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.EStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRegister {

    @NotNull
    @Email
    private String mail;
    @NotNull
    private String fio;
    private ERole role;
    private EStatus status;
    @NotNull
    @NotBlank
    private String password;

}

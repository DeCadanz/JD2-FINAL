package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogin {
    @NotBlank
    @Email
    private String mail;
    @NotBlank
    private String password;
}

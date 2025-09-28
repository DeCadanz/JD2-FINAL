package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogin {
    @NotEmpty
    @Email
    private String mail;
    @NotEmpty
    private String password;
}

package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.UserEntity;
import org.springframework.http.ResponseEntity;

public interface IVerificationService {
    public void addCode(String mail);
    public ResponseEntity<?> verifyCode(String code, String mail);
    public String getCode(UserEntity user);
}

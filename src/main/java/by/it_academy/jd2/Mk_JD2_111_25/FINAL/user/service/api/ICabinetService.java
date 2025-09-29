package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserLogin;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserRegister;
import org.springframework.http.ResponseEntity;

public interface ICabinetService {
    public void register(UserRegister user);
    public ResponseEntity<?> login(UserLogin userLogin);
    public ResponseEntity<User> getInfo(String uuid);
    public ResponseEntity<?> verify (String code, String mail);
}

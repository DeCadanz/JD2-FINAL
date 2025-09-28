package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserLogin;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserRegister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public interface ICabinetService {
    public void register(UserRegister user);
    public ResponseEntity<?> login(UserLogin ul);
    public ResponseEntity<User> getInfo(String uuid);
}

package by.it_academy.jd2.Mk_JD2_111_25.FINAL.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.enums.EStatus;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.api.ICodeRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.api.IUserRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.entity.UserEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.service.api.IVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationService implements IVerificationService {
    private final IUserRepository ur;
    private final ICodeRepository cr;
    private final UserService userService;

    public boolean verifyCode(String code, String mail) {
        Optional<UserEntity> u = ur.findByMail(mail);
        UserEntity ue = u.get();
        String uuid = ue.getUuid();

        if(code.equals(uuid)) {
            ue.setStatus(EStatus.ACTIVATED);
            ur.save(ue);
            return true;
        } else {
            return false;
        }
    }
}

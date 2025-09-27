package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.dto.ErrorResponse;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.EStatus;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.api.ICodeRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.api.IUserRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.CodeEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.UserEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.IVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerificationService implements IVerificationService {
    private final IUserRepository ur;
    private final ICodeRepository cr;


    @Override
    public String generateCode() {
        Random random = new Random();
        return String.valueOf(1000 + random.nextInt(9000));
    }

    @Override
    public ResponseEntity<?> verifyCode(String code, String mail) {
        UserEntity ue = ur.findByMail(mail).orElseThrow();
        String uuid = ue.getUuid();
        System.out.println(uuid);
        CodeEntity ce = cr.findByUuid(uuid).orElseThrow();

        String ucode = ce.getCode();
        System.out.println(ucode);

        if(ucode.equals(code)) {
            ue.setStatus(EStatus.ACTIVATED);
            ur.save(ue);
            return ResponseEntity.ok().build();
        } else {
            ErrorResponse error = new ErrorResponse("Invalid verification code");
            return ResponseEntity.badRequest().body(error);
        }
    }

    @Override
    public String getCode(UserEntity user) {
        String uuid = user.getUuid();
        System.out.println(uuid);
        return cr.findById(uuid).get().getCode();
    }
}

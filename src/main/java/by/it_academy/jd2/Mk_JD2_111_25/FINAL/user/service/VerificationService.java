package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.exceptions.CodeNotFoundException;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.exceptions.UserNotFoundException;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.EStatus;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.api.ICodeRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.api.IUserRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.CodeEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.UserEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.IMailService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.IVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerificationService implements IVerificationService {
    private final IUserRepository userRepository;
    private final ICodeRepository codeRepository;
    private final IMailService mailService;

    @Override
    public void addCode(String uuid, String mail) {
        Random random = new Random();
        String code = String.valueOf(1000 + random.nextInt(9000));
        CodeEntity codeEntity = new CodeEntity();
//        UserEntity userEntity = userRepository.findByMail(mail)
//                .orElseThrow(() -> new UserNotFoundException());
        codeEntity.setUuid(uuid);
        codeEntity.setCode(code);
        codeRepository.save(codeEntity);
        mailService.sendCode(mail, code);
    }

    @Override
    public boolean verifyCode(String code, String mail) {
        UserEntity userEntity = userRepository.findByMail(mail)
                .orElseThrow(() -> new UserNotFoundException());
        String uuid = userEntity.getUuid();
        CodeEntity codeEntity = codeRepository.findByUuid(uuid)
                .orElseThrow(() -> new CodeNotFoundException());
        String ucode = codeEntity.getCode();
        if (ucode.equals(code)) {
            userEntity.setStatus(EStatus.ACTIVATED);
            userRepository.save(userEntity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getCode(UserEntity userEntity) {
        String uuid = userEntity.getUuid();
        CodeEntity codeEntity = codeRepository.findByUuid(uuid)
                .orElseThrow(() -> new CodeNotFoundException());
        return codeEntity.getCode();
    }
}

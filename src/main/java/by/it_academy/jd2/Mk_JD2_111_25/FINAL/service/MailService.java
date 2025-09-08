package by.it_academy.jd2.Mk_JD2_111_25.FINAL.service;


import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.api.ICodeRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.api.IUserRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final IUserRepository ur;
    private final ICodeRepository cr;

    public void sendCode(User user) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getMail());
        message.setSubject("Код верификации");
        message.setText(getCode(user));
        mailSender.send(message);
    }

    public String getCode(User user) {
        Optional<UserEntity> u = ur.findByMail(user.getMail());
        UserEntity ue = u.get();
        String uuid = ue.getUuid();
        return cr.findById(uuid).get().getCode();

    }

    public boolean verifyCode(String code, String mail) {
        Optional<UserEntity> u = ur.findByMail(mail);
        UserEntity ue = u.get();
        String uuid = ue.getUuid();
        return code.equals(cr.findById(uuid).get().getCode());
    }
}

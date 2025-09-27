package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service;


import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.UserEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.IMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MailService implements IMailService {
    private final JavaMailSender mailSender;
    private final VerificationService vs;

    @Override
    public void sendCode(UserEntity user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getMail());
        message.setSubject("Код верификации");
        message.setText(vs.getCode(user));
        mailSender.send(message);
    }

}

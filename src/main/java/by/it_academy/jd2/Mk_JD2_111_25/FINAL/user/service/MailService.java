package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.IMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MailService implements IMailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendCode(String mail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject("Код верификации");
        message.setText(code);
        mailSender.send(message);
    }

}

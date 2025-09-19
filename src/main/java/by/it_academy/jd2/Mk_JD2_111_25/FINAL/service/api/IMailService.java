package by.it_academy.jd2.Mk_JD2_111_25.FINAL.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.User;

public interface IMailService {
    public void sendCode(User user);
    public String getCode(User user);
}

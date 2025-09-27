package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.UserEntity;

public interface IMailService {
    public void sendCode(UserEntity user);
}

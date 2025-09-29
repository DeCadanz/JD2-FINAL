package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserRegister;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.PageOfUser;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    public String add(UserRegister userRegister, boolean selfRegister);
    public User getByUuid(String uuid);
    public String update(String uuid, Long dtUpdate, UserRegister user);
    public PageOfUser<User> getPage(Pageable pageable);
}

package by.it_academy.jd2.Mk_JD2_111_25.FINAL.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.UserLogin;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.UsersPage;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    public void register(User user);
    public User getByUuid(String uuid);
    public void update(String uuid, Long dtUpdate, User user);
    public void login(UserLogin ul);
    public UsersPage<User> getAll(Pageable pageable);

}

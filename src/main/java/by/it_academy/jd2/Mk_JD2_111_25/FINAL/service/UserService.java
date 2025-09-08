package by.it_academy.jd2.Mk_JD2_111_25.FINAL.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.api.ICodeRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.api.IUserRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.entity.CodeEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.entity.UserEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.util.CodeGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final IUserRepository ur;
    private final ICodeRepository cr;

    public void register(User user) {
        UserEntity ue = new UserEntity();
        CodeEntity ce = new CodeEntity();
        CodeGenerator cg = new CodeGenerator();

        String uuid = UUID.randomUUID().toString();

        ue.setUuid(uuid);
        ue.setDtCreate(System.currentTimeMillis() / 1000L);
        ue.setDtUpdate(System.currentTimeMillis() / 1000L);
        ue.setFio(user.getFio());
        ue.setMail(user.getMail());
        ue.setRole(user.getRole());
        ue.setStatus(user.getStatus());
        ue.setPassword(user.getPassword());

        ce.setUuid(uuid);
        ce.setCode(cg.getCode());
        cr.save(ce);
        ur.save(ue);
    }

    public User getByUuid(String uuid) {
        User ud = new User();
        Optional<UserEntity> ue = ur.findByUuid(uuid);
        ud.setUuid(uuid);
        ud.setDt_create(ue.get().getDtCreate());
        ud.setDt_update(ue.get().getDtUpdate());
        ud.setFio(ue.get().getFio());
        ud.setMail(ue.get().getMail());
        ud.setRole(ue.get().getRole());
        ud.setStatus(ue.get().getStatus());

        return ud;
    }

    public void update(String uuid, Long dtUpdate, User user) {
        UserEntity ue = ur.findByUuid(uuid).orElseThrow();

        ue.setDtUpdate(dtUpdate);
        ue.setMail(user.getMail());
        ue.setFio(user.getFio());
        ue.setRole(user.getRole());
        ue.setStatus(user.getStatus());
        ue.setPassword(user.getPassword());

        ur.save(ue);
    }
}

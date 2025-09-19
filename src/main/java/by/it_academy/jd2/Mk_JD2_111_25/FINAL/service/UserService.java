package by.it_academy.jd2.Mk_JD2_111_25.FINAL.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.UserLogin;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.UsersPage;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.api.ICodeRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.api.IUserRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.entity.CodeEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.repository.entity.UserEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.service.api.IUserService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.util.CodeGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserService implements IUserService {

    private final IUserRepository ur;
    private final ICodeRepository cr;

    @Transactional
    public void register(User user) {
        UserEntity ue = new UserEntity();
        CodeEntity ce = new CodeEntity();
        CodeGenerator cg = new CodeGenerator();

        String uuid = UUID.randomUUID().toString();

        Long utime = Instant.now().getEpochSecond();
        ue.setUuid(uuid);
        ue.setDtCreate(utime);
        ue.setDtUpdate(utime);
        ue.setFio(user.getFio());
        ue.setMail(user.getMail());
        if(user.getRole() != null) {
            ue.setRole(user.getRole());
        }
        if(user.getStatus() != null) {
            ue.setStatus(user.getStatus());
        }
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

    public User getByMail(String mail) {
        User ud = new User();
        Optional<UserEntity> ue = ur.findByMail(mail);
        ud.setUuid(ue.get().getUuid());
        ud.setDt_create(ue.get().getDtCreate());
        ud.setDt_update(ue.get().getDtUpdate());
        ud.setFio(ue.get().getFio());
        ud.setMail(ue.get().getMail());
        ud.setRole(ue.get().getRole());
        ud.setStatus(ue.get().getStatus());

        return ud;
    }

    @Transactional
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

    public void login(UserLogin ul) {
        UserEntity ue = ur.findByMail(ul.getMail()).orElseThrow();
    }

    public UsersPage<User> getAll(Pageable pageable) {
        Page<UserEntity> page = ur.findAll(pageable);

        List<User> content = new ArrayList<>();
        for(UserEntity ue : page.getContent()) {
            User user = new User();
            user.setUuid(ue.getUuid());
            user.setDt_create(ue.getDtCreate());
            user.setDt_update(ue.getDtUpdate());
            user.setFio(ue.getFio());
            user.setMail(ue.getMail());
            user.setRole(ue.getRole());
            user.setStatus(ue.getStatus());
            content.add(user);
        }
        Page<User> up = new PageImpl<>(content, pageable, page.getTotalElements());
        return new UsersPage<>(up);
    }
}

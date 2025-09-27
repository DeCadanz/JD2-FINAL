package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserRegister;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.PageOfUser;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.api.IUserRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.UserEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.IUserService;

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

    @Transactional
    public void add(UserRegister user) {
        UserEntity ue = new UserEntity();
        String uuid = UUID.randomUUID().toString();
        Long utime = Instant.now().getEpochSecond();
        ue.setUuid(uuid);
        ue.setDtCreate(utime);
        ue.setDtUpdate(utime);
        ue.setFio(user.getFio());
        ue.setMail(user.getMail());
        ue.setRole(user.getRole());
        ue.setStatus(user.getStatus());
        ue.setPassword(user.getPassword());
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
    public void update(String uuid, Long dtUpdate, UserRegister user) {
        UserEntity ue = ur.findByUuid(uuid).orElseThrow();
        ue.setDtUpdate(dtUpdate);
        ue.setMail(user.getMail());
        ue.setFio(user.getFio());
        ue.setRole(user.getRole());
        ue.setStatus(user.getStatus());
        ue.setPassword(user.getPassword());
        ur.save(ue);
    }

    public PageOfUser<User> getAll(Pageable pageable) {
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
        return new PageOfUser<>(up);
    }
}

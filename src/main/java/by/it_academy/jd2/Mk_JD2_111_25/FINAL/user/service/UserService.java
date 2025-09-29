package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.exceptions.UserAlreadyExistsException;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.exceptions.UserNotFoundException;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserRegister;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.PageOfUser;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.api.IUserRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.UserEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.IUserService;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.IVerificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IVerificationService verificationService;

    @Transactional
    public String add(UserRegister userRegister, boolean selfRegister) {
        if (userRepository.existsByMail(userRegister.getMail())) {
            throw new UserAlreadyExistsException();
        }
        UserEntity userEntity = new UserEntity();
        String uuid = UUID.randomUUID().toString();
        System.out.println("created userID: " + uuid);
        Long utime = Instant.now().getEpochSecond();
        userEntity.setUuid(uuid);
        userEntity.setDtCreate(utime);
        userEntity.setDtUpdate(utime);
        userEntity.setFio(userRegister.getFio());
        userEntity.setMail(userRegister.getMail());
        userEntity.setRole(userRegister.getRole());
        userEntity.setStatus(userRegister.getStatus());
        userEntity.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        userRepository.save(userEntity);
        if (selfRegister) {
            verificationService.addCode(uuid, userRegister.getMail());
        }
        return uuid;
    }

    public User getByUuid(String uuid) {
        UserEntity userEntity = userRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException());
        return getUser(userEntity);
    }

    public User getByMail(String mail) {
        UserEntity userEntity = userRepository.findByMail(mail)
                .orElseThrow(() -> new UserNotFoundException());
        return getUser(userEntity);
    }

    @Transactional
    public String update(String uuid, Long dtUpdate, UserRegister userRegister) {
        UserEntity userEntity = userRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException());
        userEntity.setDtUpdate(dtUpdate);
        userEntity.setMail(userRegister.getMail());
        userEntity.setFio(userRegister.getFio());
        userEntity.setRole(userRegister.getRole());
        userEntity.setStatus(userRegister.getStatus());
        userEntity.setPassword(userRegister.getPassword());
        userRepository.save(userEntity);
        return uuid;
    }

    public PageOfUser<User> getPage(Pageable pageable) {
        Page<UserEntity> page = userRepository.findAll(pageable);
        List<User> content = new ArrayList<>();
        for (UserEntity userEntity : page.getContent()) {
            content.add(getUser(userEntity));
        }
        Page<User> pageOfUser = new PageImpl<>(content, pageable, page.getTotalElements());
        return new PageOfUser<>(pageOfUser);
    }

    private User getUser(UserEntity userEntity) {
        User user = new User();
        user.setUuid(userEntity.getUuid());
        user.setDt_create(userEntity.getDtCreate());
        user.setDt_update(userEntity.getDtUpdate());
        user.setFio(userEntity.getFio());
        user.setMail(userEntity.getMail());
        user.setRole(userEntity.getRole());
        user.setStatus(userEntity.getStatus());
        return user;
    }
}

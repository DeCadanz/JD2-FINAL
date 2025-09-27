package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.dto.ErrorResponse;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserLogin;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserRegister;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.PageOfUser;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.ERole;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.EStatus;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.api.ICodeRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.api.IUserRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.CodeEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.UserEntity;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor

public class CabinetService implements ICabinetService {

    private final IUserRepository ur;
    private final ICodeRepository cr;
    private final IUserService us;
    private final IMailService ms;
    private final IVerificationService vs;
    private final ITokenService ts;

    @Transactional
    public void register(UserRegister userr) {
        userr.setStatus(EStatus.WAITING_ACTIVATION);
        userr.setRole(ERole.USER);
        us.add(userr);
        CodeEntity ce = new CodeEntity();
        UserEntity ue = ur.findByMail(userr.getMail()).orElseThrow();
        ce.setUuid(ue.getUuid());
        ce.setCode(vs.generateCode());
        cr.save(ce);
        ms.sendCode(ue);
    }

    public ResponseEntity<?> login(UserLogin userl) {
        UserEntity ue = ur.findByMail(userl.getMail()).orElseThrow();
        if(ue.getPassword().equals(userl.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).header("Authorization", "Bearer " + ts.generate(userl)).build();
        } else {
            ErrorResponse error = new ErrorResponse("Invalid verification code");
            return ResponseEntity.badRequest().body(error);
        }
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

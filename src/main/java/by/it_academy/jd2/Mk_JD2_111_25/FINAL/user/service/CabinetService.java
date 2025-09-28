package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.exceptions.InvalidPasswordException;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.exceptions.UserAlreadyExistsException;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.exceptions.UserNotFoundException;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.exceptions.UserNotVerifiedException;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserLogin;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserRegister;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.ERole;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.EStatus;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.api.IUserRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.UserEntity;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CabinetService implements ICabinetService {

    private final IUserRepository userRepository;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final IVerificationService verificationService;
    private final ITokenService tokenService;

    @Transactional
    public void register(UserRegister userRegister) {
        if (userRepository.findByMail(userRegister.getMail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        userRegister.setStatus(EStatus.WAITING_ACTIVATION);
        userRegister.setRole(ERole.USER);
        userService.add(userRegister);
        verificationService.addCode(userRegister.getMail());
    }

    public ResponseEntity<?> login(UserLogin userLogin) {
        UserEntity userEntity = userRepository.findByMail(userLogin.getMail())
                .orElseThrow(() -> new UserNotFoundException());
        if (passwordEncoder.matches(userLogin.getPassword(), userEntity.getPassword())) {
            if (userEntity.getStatus() == EStatus.WAITING_ACTIVATION) {
                throw new UserNotVerifiedException();
            } else {
                return ResponseEntity.status(HttpStatus.OK).header(
                        "Authorization", "Bearer " + tokenService.generate(userLogin)).build();
            }
        } else {
            throw new InvalidPasswordException();
        }
    }

    @Override
    public ResponseEntity<User> getInfo(String uuid) {
        User user = userService.getByUuid(uuid);
        return ResponseEntity.ok(user);
    }
}

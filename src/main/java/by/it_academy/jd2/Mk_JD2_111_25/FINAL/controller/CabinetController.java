package by.it_academy.jd2.Mk_JD2_111_25.FINAL.controller;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.enums.ERole;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.enums.EStatus;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.service.MailService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cabinet")
@RequiredArgsConstructor
public class CabinetController {

    private final UserService userService;
    private final MailService mailService;

    @PostMapping("/registration")
    public ResponseEntity<String> create(@RequestBody User user) {
        user.setRole(ERole.USER);
        user.setStatus(EStatus.WAITING_ACTIVATION);
        userService.register(user);
        mailService.sendCode(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/verification")
    public ResponseEntity<String> verify(@RequestParam("code") String code, @RequestParam("mail") String mail) {
        mailService.verifyCode(code, mail);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

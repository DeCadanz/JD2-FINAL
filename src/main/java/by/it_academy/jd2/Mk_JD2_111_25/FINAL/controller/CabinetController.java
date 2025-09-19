package by.it_academy.jd2.Mk_JD2_111_25.FINAL.controller;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.UserLogin;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.service.api.IMailService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.service.api.ITokenService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.service.api.IUserService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.service.api.IVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cabinet")
@RequiredArgsConstructor
public class CabinetController {

    private final IUserService us;
    private final IMailService ms;
    private final IVerificationService vs;
    private final ITokenService ts;

    @PostMapping("/registration")
    public ResponseEntity<String> create(@RequestBody User user) {
        us.register(user);
        ms.sendCode(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/verification")
    public ResponseEntity<String> verify(@RequestParam("code") String code, @RequestParam("mail") String mail) {
        vs.verifyCode(code, mail);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "/login", produces = "application/json")
    public ResponseEntity<String> login(@RequestBody UserLogin userLogin) {
        String token = ts.generate(userLogin);
        return ResponseEntity.status(HttpStatus.OK).header("Authorization", "Bearer " + token).build();
    }

    @GetMapping(path = "/me", produces = "application/json")
    public ResponseEntity<User> me() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String uuid = auth.getPrincipal().toString();
        User user = us.getByUuid(uuid);
        return ResponseEntity.ok(user);
    }
}
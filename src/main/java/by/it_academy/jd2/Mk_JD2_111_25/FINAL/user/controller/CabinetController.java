package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.controller;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserLogin;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserRegister;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.ICabinetService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.IVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cabinet")
@RequiredArgsConstructor
public class CabinetController {

    private final ICabinetService cabinetService;

    @PostMapping("/registration")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegister user) {
        cabinetService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/verification")
    public ResponseEntity<?> verify(@RequestParam("code") String code, @RequestParam("mail") String mail) {
        return cabinetService.verify(code, mail);
    }

    @PostMapping(path = "/login", produces = "application/json")
    public ResponseEntity<?> login(@Valid @RequestBody UserLogin userLogin) {
        return cabinetService.login(userLogin);
    }

    @GetMapping(path = "/me", produces = "application/json")
    public ResponseEntity<User> me() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return cabinetService.getInfo(auth.getName());
    }
}
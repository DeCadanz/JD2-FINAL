package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.controller;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.annotations.AuditPoint;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserRegister;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.PageOfUser;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final IUserService userService;

    @PostMapping()
    public ResponseEntity<String> create(@Valid @RequestBody UserRegister user) {
        userService.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity<PageOfUser<User>> getPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageOfUser<User> result = userService.getPage(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> get(@PathVariable("uuid") String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getByUuid(uuid));
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<User> update(@PathVariable("uuid") String uuid, @PathVariable("dt_update") Long dtUpdate, @Valid @RequestBody UserRegister user) {
        userService.update(uuid, dtUpdate, user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

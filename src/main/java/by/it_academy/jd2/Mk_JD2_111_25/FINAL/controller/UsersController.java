package by.it_academy.jd2.Mk_JD2_111_25.FINAL.controller;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService us;

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody User user) {
        us.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> get(@PathVariable("uuid") String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(us.getByUuid(uuid));
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<User> update(@PathVariable("uuid") String uuid, @PathVariable("dt_update") Long dtUpdate, @RequestBody User user) {
        us.update(uuid, dtUpdate, user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

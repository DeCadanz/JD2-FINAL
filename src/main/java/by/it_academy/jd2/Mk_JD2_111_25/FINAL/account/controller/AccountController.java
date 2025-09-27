package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.controller;


import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.Account;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.PageOfAccount;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service.api.IAccountService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService as;

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        System.out.println("eeee");
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String uuid = auth.getPrincipal().toString();
        System.out.println("User uuid: " + uuid);
        as.add(account, uuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PageOfAccount<Account>> getAccounts(@RequestParam("page") int page, @RequestParam("size") int size) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String uuid = auth.getPrincipal().toString();

        Pageable pageable = PageRequest.of(page, size); //переместить это всё в сервис
        PageOfAccount<Account> result = as.getAll(pageable, uuid);
        System.out.println(result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Account> getAccountById(@PathVariable("uuid") String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(as.getByUuid(uuid));
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<User> updateAccount(@PathVariable("uuid") String uuid, @PathVariable("dt_update") Long dtUpdate, @RequestBody Account account) {
        as.update(uuid, dtUpdate, account);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

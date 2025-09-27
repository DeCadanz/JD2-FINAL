package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.Account;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.PageOfAccount;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.api.IAccountRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.entity.AccountEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service.api.IAccountService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final IAccountRepository ar;

    @Override
    @Transactional
    public void add(Account account, String uuid) {
        AccountEntity ae = new AccountEntity();
        String auuid = UUID.randomUUID().toString();
        Long utime = Instant.now().getEpochSecond();
        BigDecimal balance = new BigDecimal(0);
        ae.setUuid(auuid);
        ae.setDtCreate(utime);
        ae.setDtUpdate(utime);
        ae.setTitle(account.getTitle());
        ae.setDescription(account.getDescription());
        ae.setBalance(balance);
        ae.setType(account.getType());
        ae.setCurrency(account.getCurrency());
        ae.setUser(uuid);

        ar.save(ae);
    }

    @Override
    public Account getByUuid(String uuid) {
        Account account = new Account();
        Optional<AccountEntity> ae = ar.findByUuid(uuid);
        account.setUuid(ae.get().getUuid());
        account.setDtCreate(ae.get().getDtCreate());
        account.setDtUpdate(ae.get().getDtUpdate());
        account.setTitle(ae.get().getTitle());
        account.setDescription(ae.get().getDescription());
        account.setBalance(ae.get().getBalance());
        account.setType(ae.get().getType());
        account.setCurrency(ae.get().getCurrency());
        return account;
    }

    @Override
    public PageOfAccount<Account> getAll(Pageable pageable, String uuuid) {
        Page<AccountEntity> page = ar.findByUser(pageable, uuuid);
        List<Account> content = new ArrayList<>();
        for(AccountEntity ae : page.getContent()) {
            Account account = new Account();
            account.setUuid(ae.getUuid());
            account.setDtCreate(ae.getDtCreate());
            account.setDtUpdate(ae.getDtUpdate());
            account.setTitle(ae.getTitle());
            account.setDescription(ae.getDescription());
            account.setBalance(ae.getBalance());
            account.setType(ae.getType());
            account.setCurrency(ae.getCurrency());
            content.add(account);
        }
        Page<Account> ap = new PageImpl<>(content, pageable, page.getTotalElements());
        return new PageOfAccount<>(ap);
    }

    @Transactional
    public void update(String uuid, Long dtUpdate, Account account) {
        AccountEntity ae = ar.findByUuid(uuid).orElseThrow();
        ae.setTitle(account.getTitle());
        ae.setDescription(account.getDescription());
        ae.setType(account.getType());
        ae.setCurrency(account.getCurrency());
        ar.save(ae);
    }
}

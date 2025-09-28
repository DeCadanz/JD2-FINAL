package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.Account;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.PageOfAccount;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.api.IAccountRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.entity.AccountEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service.api.IAccountService;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.exceptions.AccountNotFoundException;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;

    @Override
    @Transactional
    public void add(Account account, String uuid) {
        System.out.println(account.getCurrency());
        AccountEntity accountEntity = new AccountEntity();
        String aUuid = UUID.randomUUID().toString();
        Long utime = Instant.now().getEpochSecond();
        BigDecimal balance = new BigDecimal(0);
        accountEntity.setUuid(aUuid);
        accountEntity.setDtCreate(utime);
        accountEntity.setDtUpdate(utime);
        accountEntity.setTitle(account.getTitle());
        accountEntity.setDescription(account.getDescription());
        accountEntity.setBalance(balance);
        accountEntity.setType(account.getType());
        accountEntity.setCurrency(account.getCurrency());
        accountEntity.setUser(uuid);
        accountRepository.save(accountEntity);
    }

    @Override
    public Account getByUuid(String uuid) {
        AccountEntity accountEntity = accountRepository.findById(uuid)
                .orElseThrow(() -> new AccountNotFoundException());
        Account account = new Account();
        account.setUuid(accountEntity.getUuid());
        account.setDtCreate(accountEntity.getDtCreate());
        account.setDtUpdate(accountEntity.getDtUpdate());
        account.setTitle(accountEntity.getTitle());
        account.setDescription(accountEntity.getDescription());
        account.setBalance(accountEntity.getBalance());
        account.setType(accountEntity.getType());
        account.setCurrency(accountEntity.getCurrency());
        return account;
    }

    @Override
    public PageOfAccount<Account> getPage(Pageable pageable, String uUuid) {
        Page<AccountEntity> page = accountRepository.findByUser(pageable, uUuid);
        List<Account> content = new ArrayList<>();
        for(AccountEntity accountEntity : page.getContent()) {
            Account account = new Account();
            account.setUuid(accountEntity.getUuid());
            account.setDtCreate(accountEntity.getDtCreate());
            account.setDtUpdate(accountEntity.getDtUpdate());
            account.setTitle(accountEntity.getTitle());
            account.setDescription(accountEntity.getDescription());
            account.setBalance(accountEntity.getBalance());
            account.setType(accountEntity.getType());
            account.setCurrency(accountEntity.getCurrency());
            content.add(account);
        }
        Page<Account> pageOfAccount = new PageImpl<>(content, pageable, page.getTotalElements());
        return new PageOfAccount<>(pageOfAccount);
    }

    @Transactional
    public void update(String uuid, Long dtUpdate, Account account) {
        AccountEntity accountEntity = accountRepository.findById(uuid)
                .orElseThrow(() -> new AccountNotFoundException());
        accountEntity.setTitle(account.getTitle());
        accountEntity.setDescription(account.getDescription());
        accountEntity.setType(account.getType());
        accountEntity.setCurrency(account.getCurrency());
        accountRepository.save(accountEntity);
    }

    public void checkByUuid(String uuid) {
        AccountEntity accountEntity = accountRepository.findById(uuid)
                .orElseThrow(() -> new AccountNotFoundException());
    }
}

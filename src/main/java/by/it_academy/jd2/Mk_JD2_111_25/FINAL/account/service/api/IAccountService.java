package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.Account;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.PageOfAccount;
import org.springframework.data.domain.Pageable;

public interface IAccountService {
    public void add(Account account, String uuid);
    public Account getByUuid(String uuid);
    public PageOfAccount<Account> getAll(Pageable pageable, String uuuid);
    public void update(String uuid, Long dtUpdate, Account account);
}

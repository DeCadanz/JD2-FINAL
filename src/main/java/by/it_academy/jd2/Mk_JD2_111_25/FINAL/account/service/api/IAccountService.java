package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.Account;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.PageOfAccount;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface IAccountService {
    public String add(Account account, String uuid);
    public Account getByUuid(String uuid);
    public PageOfAccount<Account> getPage(Pageable pageable, String uuuid);
    public String update(String uuid, Long dtUpdate, Account account);
    public void updateBalance(String uuid, BigDecimal value);
    public void checkByUuid(String uuid);
}

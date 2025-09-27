package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.Account;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.Operation;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.PageOfOperation;
import org.springframework.data.domain.Pageable;

public interface IOperationService  {
    public void add(Operation operation, String uuid);
    public PageOfOperation<Operation> getAll(Pageable pageable);
    public void update(String auuid, String uuid,Long dtUpdate, Operation operation);
    public void delete(String auuid, String uuid,Long dtUpdate, Operation operation);
}

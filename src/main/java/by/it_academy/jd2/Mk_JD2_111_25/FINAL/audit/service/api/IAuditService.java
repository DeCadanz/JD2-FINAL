package by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.Audit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.PageOfAudit;
import org.springframework.data.domain.Pageable;

public interface IAuditService {
    public void add(Audit audit, String uuid);
    public Audit getByUuid(String uuid);
    public PageOfAudit<Audit> getAll(Pageable pageable);
}

package by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.Audit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.AuditUser;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.PageOfAudit;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.UserAudit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.enums.EEssenceType;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


public interface IAuditService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void add(Audit audit);

    public AuditUser getByUuid(String uuid);

    public UserAudit getUserAuditByUuid(String uuid);

    public PageOfAudit<AuditUser> getPage(Pageable pageable);
}

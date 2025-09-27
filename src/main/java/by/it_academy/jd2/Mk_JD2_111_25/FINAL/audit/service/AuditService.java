package by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.Audit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.PageOfAudit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.repository.api.IAuditRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service.api.IAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService implements IAuditService {

    private final IAuditRepository ar;

    @Override
    public void add(Audit audit, String uuid) {

    }

    @Override
    public Audit getByUuid(String uuid) {
        return null;
    }

    @Override
    public PageOfAudit<Audit> getAll(Pageable pageable) {
        return null;
    }
}

package by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.Audit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.PageOfAudit;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.repository.AuditEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.repository.api.IAuditRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service.api.IAuditService;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

@Service
@RequiredArgsConstructor
public class AuditService implements IAuditService {

    private final IAuditRepository ar;

    @Override

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void add(Audit audit) {
        System.out.println(audit.toString());
        AuditEntity ae = new AuditEntity();
        ae.setUuid(audit.getUuid());
        ae.setDtCreate(audit.getDt_create());
        ae.setUuuid(audit.getUserId());
        ae.setText(audit.getText());
        ae.setType(audit.getType());
        ae.setEuuid(audit.getId());
        System.out.println(ae.toString());
        ar.save(ae);
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

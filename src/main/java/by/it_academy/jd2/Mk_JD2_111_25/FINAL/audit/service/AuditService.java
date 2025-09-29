package by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.Audit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.PageOfAudit;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.repository.AuditEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.repository.api.IAuditRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service.api.IAuditService;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditService implements IAuditService {

    private final IAuditRepository auditRepository;

    @Override
    @Async
    @Transactional
    public void add(Audit audit) {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setUuid(UUID.randomUUID().toString());
        auditEntity.setDtCreate(Instant.now().getEpochSecond());
        auditEntity.setUuuid(audit.getUserId());
        auditEntity.setText(audit.getText());
        auditEntity.setType(audit.getType());
        auditEntity.setEuuid(audit.getId());
        auditRepository.save(auditEntity);
    }

    @Override
    public Audit getByUuid(String uuid) {
        return null;
    }

    @Override
    public PageOfAudit<Audit> getPage(Pageable pageable) {
        return null;
    }
}

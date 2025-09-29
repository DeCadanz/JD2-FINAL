package by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.Audit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.AuditUser;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.PageOfAudit;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.UserAudit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.repository.AuditEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.repository.api.IAuditRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service.api.IAuditService;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.exceptions.AuditNotFoundException;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.ERole;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditService implements IAuditService {

    private final IAuditRepository auditRepository;
    private final IUserService userService;

    @Override
    @Async
    @Transactional
    public void add(Audit audit) {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setUuid(UUID.randomUUID().toString());
        auditEntity.setDtCreate(Instant.now().getEpochSecond());
        if (audit.getUserId() == "anonymousUser") {
            auditEntity.setUserId(null);
        } else {
            auditEntity.setUserId(audit.getUserId());
        }
        auditEntity.setText(audit.getText());
        auditEntity.setType(audit.getType());
        auditEntity.setId(audit.getEntityId());
        auditRepository.save(auditEntity);
    }

    @Override
    public AuditUser getByUuid(String uuid) {
        AuditEntity auditEntity = auditRepository.findByUuid(uuid)
                .orElseThrow(() -> new AuditNotFoundException());
        return getAuditUser(auditEntity);
    }

    @Override
    public UserAudit getUserAuditByUuid(String uuid) {
        UserAudit userAudit = new UserAudit();
        User user = userService.getByUuid(uuid);
        userAudit.setUuid(user.getUuid());
        userAudit.setMail(user.getMail());
        userAudit.setFio(user.getFio());
        userAudit.setRole(user.getRole());
        return userAudit;
    }

    @Override
    public PageOfAudit<AuditUser> getPage(Pageable pageable) {
        Page<AuditEntity> page = auditRepository.findAll(pageable);
        List<AuditUser> content = new ArrayList<>();
        for (AuditEntity auditEntity : page.getContent()) {
            content.add(getAuditUser(auditEntity));
        }
        Page<AuditUser> pageOfAudit = new PageImpl<>(content, pageable, page.getTotalElements());
        return new PageOfAudit<>(pageOfAudit);
    }

    private AuditUser getAuditUser(AuditEntity auditEntity) {
        AuditUser auditUser = new AuditUser();
        auditUser.setUuid(auditEntity.getUuid());
        auditUser.setDtCreate(auditEntity.getDtCreate());
        UserAudit userAudit = null;
        if (auditEntity.getUserId() != null) {
            try {
                userAudit = getUserAuditByUuid(auditEntity.getUserId());
            } catch (Exception e) {
            }
        }
        if (userAudit == null) {
            userAudit = new UserAudit();
            userAudit.setUuid("00000000-0000-0000-0000-000000000000");
            userAudit.setMail("anonymous@example.com");
            userAudit.setFio("Anonymous");
            userAudit.setRole(ERole.USER);
        }
        auditUser.setUserAudit(userAudit);
        auditUser.setText(auditEntity.getText());
        auditUser.setType(auditEntity.getType());
        auditUser.setId(auditEntity.getId());
        return auditUser;
    }
}

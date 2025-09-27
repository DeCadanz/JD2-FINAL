package by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.repository.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.repository.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAuditRepository  extends JpaRepository<AuditEntity, String> {
    Optional<AuditEntity> findByUuid(String uuid);
}

package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.entity.AccountEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.entity.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOperationRepository extends JpaRepository<OperationEntity, String> {
    Optional<OperationEntity> findByUuid(String uuid);
    Page<OperationEntity> findByAccount(Pageable pageable, String uuid);
}

package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAccountRepository extends JpaRepository<AccountEntity, String> {
    Optional<AccountEntity> findByUuid(String uuid);
    Page<AccountEntity> findByUser(Pageable pageable, String uuid);
}

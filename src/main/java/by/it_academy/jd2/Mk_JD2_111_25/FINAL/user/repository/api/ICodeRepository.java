package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICodeRepository extends JpaRepository<CodeEntity, String> {
    Optional<CodeEntity> findByUuid(String uuid);
}

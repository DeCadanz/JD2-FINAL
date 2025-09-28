package by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.api;


import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.entity.CurrencyEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICurrencyRepository extends JpaRepository<CurrencyEntity, String> {
    Optional<CurrencyEntity> findByUuid(String uuid);
}

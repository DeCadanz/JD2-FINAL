package by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.api;


import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICurrencyRepository extends JpaRepository<CurrencyEntity, String> {
}

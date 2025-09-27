package by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.service.api;


import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.Currency;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.PageOfCurrency;
import org.springframework.data.domain.Pageable;

public interface ICurrencyService {
    public void add(Currency currency);
    public PageOfCurrency<Currency> getAll(Pageable pageable);
}

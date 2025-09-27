package by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.Currency;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.PageOfCurrency;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.api.ICurrencyRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.entity.CurrencyEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.service.api.ICurrencyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurrencyService implements ICurrencyService {

    private final ICurrencyRepository cr;

    @Override
    @Transactional
    public void add(Currency currency) {

        CurrencyEntity ce = new CurrencyEntity();
        String uuid = UUID.randomUUID().toString();
        Long utime = Instant.now().getEpochSecond();
        ce.setUuid(uuid);
        ce.setDtCreate(utime);
        ce.setDtUpdate(utime);
        ce.setTitle(currency.getTitle());
        ce.setDescription(currency.getDescription());
        cr.save(ce);
    }

    @Override
    public PageOfCurrency<Currency> getAll(Pageable pageable) {
        Page<CurrencyEntity> page = cr.findAll(pageable);

        List<Currency> content = new ArrayList<>();
        for(CurrencyEntity ce : page.getContent()) {
            Currency currency = new Currency();
            currency.setUuid(ce.getUuid());
            currency.setDtCreate(ce.getDtCreate());
            currency.setDtUpdate(ce.getDtUpdate());
            currency.setTitle(ce.getTitle());
            currency.setDescription(ce.getDescription());
            content.add(currency);
        }
        Page<Currency> pp = new PageImpl<>(content, pageable, page.getTotalElements());
        return new  PageOfCurrency<>(pp);
    }
}

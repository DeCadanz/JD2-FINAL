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

    private final ICurrencyRepository currencyRepository;

    @Override
    @Transactional

    public String add(Currency currency) {
        CurrencyEntity currencyEntity = new CurrencyEntity();
        String uuid = UUID.randomUUID().toString();
        Long utime = Instant.now().getEpochSecond();
        currencyEntity.setUuid(uuid);
        currencyEntity.setDtCreate(utime);
        currencyEntity.setDtUpdate(utime);
        currencyEntity.setTitle(currency.getTitle());
        currencyEntity.setDescription(currency.getDescription());
        currencyRepository.save(currencyEntity);
        return uuid;
    }

    @Override
    public PageOfCurrency<Currency> getPage(Pageable pageable) {
        Page<CurrencyEntity> page = currencyRepository.findAll(pageable);

        List<Currency> content = new ArrayList<>();
        for(CurrencyEntity currencyEntity : page.getContent()) {
            Currency currency = new Currency();
            currency.setUuid(currencyEntity.getUuid());
            currency.setDtCreate(currencyEntity.getDtCreate());
            currency.setDtUpdate(currencyEntity.getDtUpdate());
            currency.setTitle(currencyEntity.getTitle());
            currency.setDescription(currencyEntity.getDescription());
            content.add(currency);
        }
        Page<Currency> pageOfCurrency = new PageImpl<>(content, pageable, page.getTotalElements());
        return new  PageOfCurrency<>(pageOfCurrency);
    }
}

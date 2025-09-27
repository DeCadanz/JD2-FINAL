package by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.Currency;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.OperationCategory;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.PageOfCurrency;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.PageOfOperationCategory;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.api.IOperationCategoryRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.entity.CurrencyEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.entity.OperationCategoryEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.service.api.ICategoryService;
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
public class CategoryService implements ICategoryService {

    private final IOperationCategoryRepository or;

    @Override
    @Transactional
    public void add(OperationCategory category) {

        OperationCategoryEntity oe = new OperationCategoryEntity();
        String uuid = UUID.randomUUID().toString();
        Long utime = Instant.now().getEpochSecond();
        oe.setUuid(uuid);
        oe.setDtCreate(utime);
        oe.setDtUpdate(utime);
        oe.setTitle(category.getTitle());
        or.save(oe);
    }

    @Override
    public PageOfOperationCategory<OperationCategory> getAll(Pageable pageable) {
        Page<OperationCategoryEntity> page = or.findAll(pageable);

        List<OperationCategory> content = new ArrayList<>();
        for(OperationCategoryEntity oe : page.getContent()) {
            OperationCategory category = new OperationCategory();
            category.setUuid(oe.getUuid());
            category.setDtCreate(oe.getDtCreate());
            category.setDtUpdate(oe.getDtUpdate());
            category.setTitle(oe.getTitle());
            content.add(category);
        }
        Page<OperationCategory> op = new PageImpl<>(content, pageable, page.getTotalElements());
        return new PageOfOperationCategory<>(op);
    }
}

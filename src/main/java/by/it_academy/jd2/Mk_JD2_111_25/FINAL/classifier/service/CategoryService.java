package by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.OperationCategory;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.PageOfOperationCategory;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.api.IOperationCategoryRepository;
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

    private final IOperationCategoryRepository operationCategoryRepository;

    @Override
    @Transactional

    public String add(OperationCategory category) {
        OperationCategoryEntity operationCategoryEntity = new OperationCategoryEntity();
        String uuid = UUID.randomUUID().toString();
        Long utime = Instant.now().getEpochSecond();
        operationCategoryEntity.setUuid(uuid);
        operationCategoryEntity.setDtCreate(utime);
        operationCategoryEntity.setDtUpdate(utime);
        operationCategoryEntity.setTitle(category.getTitle());
        operationCategoryRepository.save(operationCategoryEntity);
        return uuid;
    }

    @Override
    public PageOfOperationCategory<OperationCategory> getPage(Pageable pageable) {
        Page<OperationCategoryEntity> page = operationCategoryRepository.findAll(pageable);

        List<OperationCategory> content = new ArrayList<>();
        for(OperationCategoryEntity operationCategoryEntity : page.getContent()) {
            OperationCategory category = new OperationCategory();
            category.setUuid(operationCategoryEntity.getUuid());
            category.setDtCreate(operationCategoryEntity.getDtCreate());
            category.setDtUpdate(operationCategoryEntity.getDtUpdate());
            category.setTitle(operationCategoryEntity.getTitle());
            content.add(category);
        }
        Page<OperationCategory> pageOfOperationCategory = new PageImpl<>(content, pageable, page.getTotalElements());
        return new PageOfOperationCategory<>(pageOfOperationCategory);
    }
}

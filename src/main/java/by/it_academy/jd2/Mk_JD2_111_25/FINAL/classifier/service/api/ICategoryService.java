package by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.OperationCategory;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.PageOfOperationCategory;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {
    public void add(OperationCategory category);
    public PageOfOperationCategory<OperationCategory> getAll(Pageable pageable);
}

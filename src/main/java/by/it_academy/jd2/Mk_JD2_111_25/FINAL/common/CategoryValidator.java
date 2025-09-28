package by.it_academy.jd2.Mk_JD2_111_25.FINAL.common;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.api.IOperationCategoryRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.annotations.ValidCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {

    private final IOperationCategoryRepository operationRepository;

    @Override
    public boolean isValid(String categoryUuid, ConstraintValidatorContext context) {
        if (categoryUuid == null || categoryUuid.isBlank()) {
            return true;
        }
        try {
            return operationRepository.existsById(categoryUuid);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
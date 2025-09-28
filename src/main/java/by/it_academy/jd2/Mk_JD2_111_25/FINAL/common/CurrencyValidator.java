package by.it_academy.jd2.Mk_JD2_111_25.FINAL.common;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.api.ICurrencyRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.annotations.ValidCurrency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {

    private final ICurrencyRepository currencyRepository;

    @Override
    public boolean isValid(String currencyUuid, ConstraintValidatorContext context) {
        if (currencyUuid == null || currencyUuid.isBlank()) {
            return true;
        }
        try {
            return currencyRepository.existsById(currencyUuid);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
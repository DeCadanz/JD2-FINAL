package by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.annotations;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.CurrencyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CurrencyValidator.class)  // Ссылка на валидатор
public @interface ValidCurrency {
    String message() default "Currency с таким UUID не существует";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.annotations;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.enums.EEssenceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditPoint {
    //String value() default "";
    EEssenceType essenceType();
}
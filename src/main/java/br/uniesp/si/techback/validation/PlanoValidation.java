package br.uniesp.si.techback.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PlanoValidationValidator.class)
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PlanoValidation {

    String message() default "O plano informado não é válido ou não está disponível";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

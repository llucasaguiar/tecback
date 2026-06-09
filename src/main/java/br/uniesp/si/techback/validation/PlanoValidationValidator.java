package br.uniesp.si.techback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class PlanoValidationValidator implements ConstraintValidator<PlanoValidation, String> {

    private static final List<String> METODOS_VALIDOS = Arrays.asList(
            "Básico",
            "Padrão",
            "Premium"
    );

    @Override
    public boolean isValid(String plano, ConstraintValidatorContext context) {
        if (plano == null || plano.isBlank()) {
            return true;
        }

        return METODOS_VALIDOS.contains(plano.trim());
    }
}

package br.uniesp.si.techback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class MetodoPagamentoValidationValidator implements ConstraintValidator<MetodoPagamentoValidation, String> {

    // Lista com as opções de pagamento válidas aceitas pela aplicação
    private static final List<String> METODOS_VALIDOS = Arrays.asList(
            "Pix",
            "Cartão de Crédito",
            "Cartão de Débito",
            "Boleto",
            "Boleto Bancário",
            "Transferência Bancária",
            "PicPay",
            "Mercado Pago",
            "PayPal"
    );

    @Override
    public boolean isValid(String metodoPagamento, ConstraintValidatorContext context) {
        // Se o campo for nulo ou vazio, a validação passa (deixando a responsabilidade para o @NotBlank se necessário)
        if (metodoPagamento == null || metodoPagamento.isBlank()) {
            return true;
        }

        // Verifica se o valor enviado (removendo espaços extras) está contido na lista permitida
        return METODOS_VALIDOS.contains(metodoPagamento.trim());
    }
}
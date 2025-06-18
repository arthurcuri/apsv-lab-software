package APSV.Controller.Validacao.validadores;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNPJValidator implements ConstraintValidator<CNPJ, String> {

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null) {
            return false;
        }

        cnpj = cnpj.replaceAll("[^\\d]", "");

        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        try {
            int[] pesosPrimeiro = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] pesosSegundo = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += (cnpj.charAt(i) - '0') * pesosPrimeiro[i];
            }
            int primeiroDigito = soma % 11;
            primeiroDigito = (primeiroDigito < 2) ? 0 : 11 - primeiroDigito;

            if (primeiroDigito != (cnpj.charAt(12) - '0')) {
                return false;
            }

            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += (cnpj.charAt(i) - '0') * pesosSegundo[i];
            }
            int segundoDigito = soma % 11;
            segundoDigito = (segundoDigito < 2) ? 0 : 11 - segundoDigito;

            return segundoDigito == (cnpj.charAt(13) - '0');

        } catch (NumberFormatException e) {
            return false;
        }
    }
}

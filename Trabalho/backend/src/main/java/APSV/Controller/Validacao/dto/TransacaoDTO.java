package APSV.Controller.Validacao.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO {

        @NotNull(message = "O ID do usuário de origem é obrigatório")
        private Long origemId;

        @NotNull(message = "O ID do usuário de destino é obrigatório")
        private Long destinoId;

        private LocalDateTime data;

        @NotNull(message = "A quantidade é obrigatória")
        @Min(value = 1, message = "A quantidade de moedas deve ser no mínimo 1")
        @Digits(integer = 10, fraction = 0, message = "A quantidade de moedas deve ser um número inteiro")
        private Integer quantidade;

        @NotBlank(message = "O motivo é obrigatório")
        private String motivo;
}

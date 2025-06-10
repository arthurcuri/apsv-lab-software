package APSV.Controller.Validacao.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoResponseDTO{
        private Long id;

        private Long origemId;

        private Long destinoId;

        private String motivo;

        private LocalDateTime data;
        
        private Integer quantidade;
}

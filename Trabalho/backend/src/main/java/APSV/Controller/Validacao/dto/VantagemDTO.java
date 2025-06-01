package APSV.Controller.Validacao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VantagemDTO {
    private Long id;

    private String nome;

    private String descricao;

    private String imagem;
    
    private Integer custo;
}


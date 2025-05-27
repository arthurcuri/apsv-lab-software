package APSV.Controller.Validacao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {
    private String nome;
    private String email;
    private String senha;
    private String cpf;
}


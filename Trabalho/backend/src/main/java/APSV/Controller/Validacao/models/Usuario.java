package APSV.Controller.Validacao.models;

import APSV.Controller.Validacao.validadores.CPF;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 4, max = 50, message = "A senha deve ter entre 4 e 50 caracteres")
    private String senha;
    
    @CPF(message = "CPF inválido")
    private String cpf;

    private String cnpj;

    @NotBlank(message = "O tipo é obrigatório")
    private String tipo = "ALUNO";

    @NotNull(message = "O campo moedas é obrigatório")
    @Min(value = 0, message = "A quantidade de moedas não pode ser negativa")
    private Integer moedas = 0;
}

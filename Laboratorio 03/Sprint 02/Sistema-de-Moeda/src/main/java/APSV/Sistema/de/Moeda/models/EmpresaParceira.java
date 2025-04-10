package APSV.Sistema.de.Moeda.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = EmpresaParceira.TABLE_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaParceira {
    public static final String TABLE_NAME = "empresa_parceira";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "cnpj", nullable = false)
    private String cnpj;

    @Column(name = "telefone", nullable = false)
    private String telefone;
    
}

package APSV.LabProjSoftware.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {
    @Id
    private Long id;
    private String nome;
    private String email;
    private String senha;

    public void efetuarLogin(String email, String senha) {}
}
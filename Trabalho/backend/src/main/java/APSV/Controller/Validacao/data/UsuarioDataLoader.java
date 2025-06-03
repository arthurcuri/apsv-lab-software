package APSV.Controller.Validacao.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import APSV.Controller.Validacao.repositories.UsuarioRepository;
import APSV.Controller.Validacao.models.Usuario;

@Component
public class UsuarioDataLoader implements CommandLineRunner {

private final UsuarioRepository usuarioRepository;

public UsuarioDataLoader(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
}

@Override
public void run(String... args) throws Exception {
    // Verifica se já existem usuários cadastrados
    if (usuarioRepository.count() == 0) {
        // Cria usuários iniciais
        Usuario c1 = new Usuario();
        c1.setNome("José Silva");
        c1.setCpf("123.456.789-01");
        c1.setSenha("1234");
        c1.setEmail("jose@gmail.com");
        c1.setTipo("ALUNO");
        c1.setMoedas(150);
        usuarioRepository.save(c1);

        Usuario c2 = new Usuario();
        c2.setNome("Maria Oliveira");
        c2.setCpf("987.654.321-00");
        c2.setSenha("1234");
        c2.setEmail("maria@gmail.com");
        c2.setTipo("ALUNO");
        c2.setMoedas(100);
        usuarioRepository.save(c2);

        Usuario c3 = new Usuario();
        c3.setNome("Carlos Pereira");
        c3.setCpf("111.222.333-44");
        c3.setSenha("1234");
        c3.setEmail("carlos@gmail.com");
        c3.setTipo("PROFESSOR");
        c3.setMoedas(300);
        usuarioRepository.save(c3);

        Usuario c4 = new Usuario();
        c4.setNome("Ana Costa");
        c4.setCpf("555.666.777-88");
        c4.setSenha("1234");
        c4.setEmail("ana@gmail.com");
        c4.setTipo("PROFESSOR");
        c4.setMoedas(200);
        usuarioRepository.save(c4);

        Usuario c5 = new Usuario();
        c5.setNome("Microsft Corporation");
        c5.setCnpj("12.345.678/0001-90");
        c5.setSenha("1234");
        c5.setEmail("microsoft@gmail.com");
        c5.setTipo("EMPRESA");
        c5.setMoedas(0);
        usuarioRepository.save(c5);

        Usuario c6 = new Usuario();
        c6.setNome("Google LLC");
        c6.setCnpj("98.765.432/0001-01");
        c6.setSenha("1234");
        c6.setEmail("google@gmail.com");
        c6.setTipo("EMPRESA");
        c6.setMoedas(0);
        usuarioRepository.save(c6);
        
    }
}
}

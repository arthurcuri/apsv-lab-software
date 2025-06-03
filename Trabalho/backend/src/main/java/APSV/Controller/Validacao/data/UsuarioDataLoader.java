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
        c1.setMoedas(50);
        usuarioRepository.save(c1);

        Usuario c2 = new Usuario();
        c2.setNome("Maria Oliveira");
        c2.setCpf("987.654.321-00");
        c2.setSenha("1234");
        c2.setEmail("maria@gmail.com");
        c2.setTipo("ALUNO");
        c2.setMoedas(30);
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
    }
}
}

package APSV.LabProjSoftware.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import APSV.LabProjSoftware.models.Aluno;
import APSV.LabProjSoftware.repositories.AlunoRepository;

@Component
public class AlunoDataLoader implements CommandLineRunner {

    private final AlunoRepository usuarioRepository;

    public AlunoDataLoader(AlunoRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(usuarioRepository.count() == 0){
            Aluno c1 = new Aluno();
            c1.setNome(("Arthur Curi"));
            c1.setSenha("1234");
            c1.setEmail("arthur@gmail.com");
            usuarioRepository.save(c1);

            Aluno c2 = new Aluno();
            c2.setNome(("Petrius Arturo"));
            c2.setSenha("1234");
            c2.setEmail("petrius@gmail.com");
            usuarioRepository.save(c2);

            Aluno c3 = new Aluno();
            c3.setNome(("Sthel Torres"));
            c3.setSenha("1234");
            c3.setEmail("sthel@gmail.com");
            usuarioRepository.save(c3);

            Aluno c4 = new Aluno();
            c4.setNome(("Vinicius Xavier"));
            c4.setSenha("1234");
            c4.setEmail("vinicius@gmail.com");
            usuarioRepository.save(c4);

        }
    }
}

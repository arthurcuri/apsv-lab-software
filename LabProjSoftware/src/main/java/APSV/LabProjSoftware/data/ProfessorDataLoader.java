package APSV.LabProjSoftware.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import APSV.LabProjSoftware.models.Professor;
import APSV.LabProjSoftware.repositories.ProfessorRepository;

@Component
public class ProfessorDataLoader implements CommandLineRunner {

    private final ProfessorRepository professorRepository;

    public ProfessorDataLoader(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(professorRepository.count() == 0){
            Professor p1 = new Professor();
            p1.setNome(("Eveline Alonso"));
            p1.setSenha("1234");
            p1.setEmail("eveline@gmail.com");
            professorRepository.save(p1);

            Professor p2 = new Professor();
            p2.setNome(("João Caram"));
            p2.setSenha("1234");
            p2.setEmail("joao@gmail.com");
            professorRepository.save(p2);

            Professor p3 = new Professor();
            p3.setNome(("Lesandro Ponciano"));
            p3.setSenha("1234");
            p3.setEmail("lesandro@gmail.com");
            professorRepository.save(p3);

            Professor p4 = new Professor();
            p4.setNome(("Rodrigo de Carvalho"));
            p4.setSenha("1234");
            p4.setEmail("rodrigo@gmail.com");
            professorRepository.save(p4);

        }
    }
}

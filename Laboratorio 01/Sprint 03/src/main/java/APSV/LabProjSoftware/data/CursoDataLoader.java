package APSV.LabProjSoftware.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import APSV.LabProjSoftware.entities.Curso;
import APSV.LabProjSoftware.repositories.CursoRepository;

@Component
public class CursoDataLoader implements CommandLineRunner {

    private final CursoRepository cursoRepository;

    public CursoDataLoader(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(cursoRepository.count() == 0){
            Curso c1 = new Curso();
            c1.setNome(("Ciencia da Computacao"));
            cursoRepository.save(c1);

            Curso c2 = new Curso();
            c2.setNome(("Engenharia de Computação"));
            cursoRepository.save(c2);

            Curso c3 = new Curso();
            c3.setNome(("Engenharia de Software"));
            cursoRepository.save(c3);

            Curso c4 = new Curso();
            c4.setNome(("Sistemas de Informação"));
            cursoRepository.save(c4);

        }
    }
}

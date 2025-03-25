package APSV.LabProjSoftware.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import APSV.LabProjSoftware.entities.Disciplina;
import APSV.LabProjSoftware.repositories.DisciplinaRepository;

@Component
public class DisciplinaDataLoader implements CommandLineRunner {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaDataLoader(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(disciplinaRepository.count() == 0){
            Disciplina d1 = new Disciplina();
            d1.setNome(("Algoritmos e Estruturas de Dados I"));
            d1.setCreditos(5);
            disciplinaRepository.save(d1);

            Disciplina d2 = new Disciplina();
            d2.setNome(("Cálculo I"));
            d2.setCreditos(4);
            disciplinaRepository.save(d2);

            Disciplina d3 = new Disciplina();
            d3.setNome(("Programação Modular"));
            d3.setCreditos(5);
            disciplinaRepository.save(d3);

            Disciplina d4 = new Disciplina();
            d4.setNome(("Banco de Dados"));
            d4.setCreditos(4);
            disciplinaRepository.save(d4);

            Disciplina d5 = new Disciplina();
            d5.setNome(("Algoritmos e Estruturas de Dados II"));
            d5.setCreditos(5);
            disciplinaRepository.save(d5);

            Disciplina d6 = new Disciplina();
            d6.setNome(("Engenharia de Requisitos de Software"));
            d6.setCreditos(5);
            disciplinaRepository.save(d6);

            Disciplina d7 = new Disciplina();
            d7.setNome(("Sistemas Operacionais"));
            d7.setCreditos(3);
            disciplinaRepository.save(d7);

            Disciplina d8 = new Disciplina();
            d8.setNome(("Projeto de Software"));
            d8.setCreditos(5);
            disciplinaRepository.save(d8);

            Disciplina d9 = new Disciplina();
            d9.setNome(("Teoria dos Grafos"));
            d9.setCreditos(5);
            disciplinaRepository.save(d9);

            Disciplina d10 = new Disciplina();
            d10.setNome(("Redes de Computadores"));
            d10.setCreditos(5);
            disciplinaRepository.save(d10);

        }
    }
}

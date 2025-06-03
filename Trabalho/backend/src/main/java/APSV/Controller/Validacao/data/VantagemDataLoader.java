package APSV.Controller.Validacao.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import APSV.Controller.Validacao.models.Vantagem;
import APSV.Controller.Validacao.repositories.VantagemRepository;

@Component
public class VantagemDataLoader implements CommandLineRunner {

    private final VantagemRepository vantagemRepository;

    public VantagemDataLoader(VantagemRepository vantagemRepository) {
        this.vantagemRepository = vantagemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existem vantagens cadastradas
        if (vantagemRepository.count() == 0) {
            // Cria vantagens iniciais
            Vantagem v1 = new Vantagem();
            v1.setNome("Desconto em Livros");
            v1.setDescricao("10% de desconto em livros na livraria parceira.");
            v1.setImagem("https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
            v1.setCusto(20);
            vantagemRepository.save(v1);

            Vantagem v2 = new Vantagem();
            v2.setNome("Acesso a Cursos Online");
            v2.setDescricao("Acesso gratuito a cursos online de diversas áreas.");
            v2.setImagem("https://www.tudoemcursos.com/files/cursos-gratis.png");
            v2.setCusto(50);
            vantagemRepository.save(v2);

            Vantagem v3 = new Vantagem();
            v3.setNome("Participação em Eventos");
            v3.setDescricao("Entrada gratuita em eventos e palestras organizados pela instituição.");
            v3.setImagem("https://minasumluxo.com.br/wp-content/uploads/2025/04/eventos.jpg");
            v3.setCusto(30);
            vantagemRepository.save(v3);
        }
    }
    
}

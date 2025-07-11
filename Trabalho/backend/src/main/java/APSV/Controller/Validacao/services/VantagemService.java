package APSV.Controller.Validacao.services;

import APSV.Controller.Validacao.dto.VantagemCreateDTO;
import APSV.Controller.Validacao.dto.VantagemDTO;
import APSV.Controller.Validacao.models.Usuario;
import APSV.Controller.Validacao.models.Vantagem;
import APSV.Controller.Validacao.repositories.UsuarioRepository;
import APSV.Controller.Validacao.repositories.VantagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VantagemService {

    @Autowired
    private VantagemRepository vantagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // Importe e injete seu repository de usuário

    // Criar
    public VantagemDTO criarVantagem(VantagemCreateDTO dto) {
        Vantagem vantagem = new Vantagem();
        vantagem.setNome(dto.getNome());
        vantagem.setDescricao(dto.getDescricao());
        vantagem.setImagem(dto.getImagem());
        vantagem.setCusto(dto.getCusto());

        Vantagem salva = vantagemRepository.save(vantagem);

        return toDTO(salva);
    }

    // Listar todas
    public List<VantagemDTO> listarVantagens() {
        return vantagemRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public Optional<VantagemDTO> buscarPorId(Long id) {
        return vantagemRepository.findById(id).map(this::toDTO);
    }

    // Atualizar
    public VantagemDTO atualizarVantagem(Long id, VantagemCreateDTO dto) {
        Vantagem vantagem = vantagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vantagem não encontrada"));

        vantagem.setNome(dto.getNome());
        vantagem.setDescricao(dto.getDescricao());
        vantagem.setImagem(dto.getImagem());
        vantagem.setCusto(dto.getCusto());

        Vantagem atualizada = vantagemRepository.save(vantagem);

        return toDTO(atualizada);
    }

    // Deletar
    public void deletarVantagem(Long id) {
        vantagemRepository.deleteById(id);
    }

    // Conversão para DTO
    private VantagemDTO toDTO(Vantagem v) {
        return new VantagemDTO(
                v.getId(),
                v.getNome(),
                v.getDescricao(),
                v.getImagem(),
                v.getCusto()
        );
    }

    public void resgatarVantagem(Long usuarioId, Long vantagemId) {
        // Buscar usuário
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Buscar vantagem
        Vantagem vantagem = vantagemRepository.findById(vantagemId)
            .orElseThrow(() -> new RuntimeException("Vantagem não encontrada"));

        // Verificar saldo
        if (usuario.getMoedas() < vantagem.getCusto()) {
            throw new RuntimeException("Saldo insuficiente para resgatar esta vantagem.");
        }

        // Debitar saldo
        usuario.setMoedas(usuario.getMoedas() - vantagem.getCusto());
        usuarioRepository.save(usuario);

        // Aqui você pode adicionar lógica para registrar o resgate, se desejar
    }
}

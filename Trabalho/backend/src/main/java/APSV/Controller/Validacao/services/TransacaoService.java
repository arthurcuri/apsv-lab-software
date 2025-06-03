package APSV.Controller.Validacao.services;

import APSV.Controller.Validacao.dto.TransacaoResponseDTO;
import APSV.Controller.Validacao.models.Transacao;
import APSV.Controller.Validacao.models.Usuario;
import APSV.Controller.Validacao.repositories.TransacaoRepository;
import APSV.Controller.Validacao.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Transacao salvar(Transacao transacao) {

        Usuario origem = transacao.getOrigem();
        Usuario destino = transacao.getDestino();
        Integer quantidade = transacao.getQuantidade();

        // Valida se origem tem saldo suficiente
        if (origem.getMoedas() < quantidade) {
            throw new RuntimeException("Saldo insuficiente na conta de origem.");
        }

        origem.setMoedas(origem.getMoedas() - quantidade);
        destino.setMoedas(destino.getMoedas() + quantidade);

        usuarioRepository.save(origem);
        usuarioRepository.save(destino);

        // Define a data da transação (se desejar)
        if (transacao.getData() == null) {
            transacao.setData(LocalDateTime.now());
        }

        return transacaoRepository.save(transacao);
    }

    public void salvarTransacao(Usuario origem, Usuario destino, int quantidade, String motivo) {
        Transacao transacao = new Transacao();
        transacao.setOrigem(origem);
        transacao.setDestino(destino);
        transacao.setQuantidade(quantidade);
        transacao.setMotivo(motivo);
        transacao.setData(LocalDateTime.now());
        transacaoRepository.save(transacao);
    }

    public List<TransacaoResponseDTO> listarPorOrigem(Long origemId) {
        return transacaoRepository.findByOrigemId(origemId)
                .stream()
                .map(t -> new TransacaoResponseDTO(
                        t.getId(),
                        t.getOrigem().getId(),
                        t.getDestino().getId(),
                        t.getMotivo(),
                        t.getData(),
                        t.getQuantidade()))
                .toList();
    }

    public List<TransacaoResponseDTO> listarPorDestino(Long destinoId) {
        return transacaoRepository.findByDestinoId(destinoId)
                .stream()
                .map(t -> new TransacaoResponseDTO(
                        t.getId(),
                        t.getOrigem().getId(),
                        t.getDestino().getId(),
                        t.getMotivo(),
                        t.getData(),
                        t.getQuantidade()))
                .toList();
    }

    public List<Transacao> listarTodas() {
        return transacaoRepository.findAll();
    }

    public Optional<Transacao> buscarPorId(Long id) {
        return transacaoRepository.findById(id);
    }

    public void deletar(Long id) {
        transacaoRepository.deleteById(id);
    }
}

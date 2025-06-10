package APSV.Controller.Validacao.controllers;

import APSV.Controller.Validacao.dto.TransacaoDTO;
import APSV.Controller.Validacao.dto.TransacaoResponseDTO;
import APSV.Controller.Validacao.models.Transacao;
import APSV.Controller.Validacao.models.Usuario;
import APSV.Controller.Validacao.models.Vantagem;
import APSV.Controller.Validacao.repositories.UsuarioRepository;
import APSV.Controller.Validacao.repositories.VantagemRepository;
import APSV.Controller.Validacao.services.TransacaoService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private VantagemRepository vantagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> criar(@RequestBody @Valid TransacaoDTO dto) {

        Usuario origem = usuarioRepository.findById(dto.getOrigemId())
                .orElseThrow(() -> new RuntimeException("Usuário de origem não encontrado"));

        Usuario destino = usuarioRepository.findById(dto.getDestinoId())
                .orElseThrow(() -> new RuntimeException("Usuário de destino não encontrado"));

        Transacao transacao = new Transacao();
        transacao.setOrigem(origem);
        transacao.setDestino(destino);
        transacao.setData(dto.getData());
        transacao.setMotivo(dto.getMotivo());
        transacao.setQuantidade(dto.getQuantidade());

        Transacao salva = service.salvar(transacao);

        TransacaoResponseDTO response = new TransacaoResponseDTO(
                salva.getId(),
                salva.getOrigem().getId(),
                salva.getDestino().getId(),
                salva.getMotivo(),
                salva.getData(),
                salva.getQuantidade());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/resgatar")
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

        // Debitar saldo do usuário
        usuario.setMoedas(usuario.getMoedas() - vantagem.getCusto());
        usuarioRepository.save(usuario);

        // Creditar saldo ao admin (ID 1)
        Usuario admin = usuarioRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Admin não encontrado"));
        admin.setMoedas(admin.getMoedas() + vantagem.getCusto());
        usuarioRepository.save(admin);

        // Criar transação
        transacaoService.salvarTransacao(
                usuario, // origem
                admin, // destino
                vantagem.getCusto(),
                "Resgate de vantagem: " + vantagem.getNome());

    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> buscarPorId(@PathVariable Long id) {
        Optional<Transacao> transacao = service.buscarPorId(id);
        return transacao.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.buscarPorId(id).isPresent()) {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

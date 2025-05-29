package APSV.Controller.Validacao.controllers;

import APSV.Controller.Validacao.dto.TransacaoDTO;
import APSV.Controller.Validacao.dto.TransacaoResponseDTO;
import APSV.Controller.Validacao.models.Transacao;
import APSV.Controller.Validacao.models.Usuario;
import APSV.Controller.Validacao.repositories.UsuarioRepository;
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

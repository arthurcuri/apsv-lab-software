package APSV.Controller.Validacao.controllers;

import APSV.Controller.Validacao.dto.VantagemCreateDTO;
import APSV.Controller.Validacao.dto.VantagemDTO;
import APSV.Controller.Validacao.services.VantagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vantagens")
@CrossOrigin(origins = "*")
public class VantagemController {

    @Autowired
    private VantagemService vantagemService;

    // Criar
    @PostMapping
    public ResponseEntity<VantagemDTO> criarVantagem(@RequestBody @Valid VantagemCreateDTO dto) {
        VantagemDTO novaVantagem = vantagemService.criarVantagem(dto);
        return ResponseEntity.ok(novaVantagem);
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<List<VantagemDTO>> listarVantagens() {
        return ResponseEntity.ok(vantagemService.listarVantagens());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<VantagemDTO> buscarPorId(@PathVariable Long id) {
        return vantagemService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<VantagemDTO> atualizarVantagem(@PathVariable Long id, @RequestBody @Valid VantagemCreateDTO dto) {
        try {
            VantagemDTO atualizada = vantagemService.atualizarVantagem(id, dto);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVantagem(@PathVariable Long id) {
        vantagemService.deletarVantagem(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/resgatar")
    public ResponseEntity<?> resgatarVantagem(@RequestBody Map<String, Long> payload) {
        Long usuarioId = payload.get("usuarioId");
        Long vantagemId = payload.get("vantagemId");
        try {
            vantagemService.resgatarVantagem(usuarioId, vantagemId);
            return ResponseEntity.ok().body("Vantagem resgatada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

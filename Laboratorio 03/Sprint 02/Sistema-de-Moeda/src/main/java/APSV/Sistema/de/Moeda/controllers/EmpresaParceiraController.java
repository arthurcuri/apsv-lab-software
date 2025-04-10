package APSV.Sistema.de.Moeda.controllers;

import APSV.Sistema.de.Moeda.models.EmpresaParceira;
import APSV.Sistema.de.Moeda.services.EmpresaParceiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empresas")
public class EmpresaParceiraController {

    @Autowired
    private EmpresaParceiraService empresaParceiraService;

    // Listar todas as empresas parceiras
    @GetMapping
    public List<EmpresaParceira> listarTodas() {
        return empresaParceiraService.listarTodas();
    }

    // Buscar empresa parceira por ID
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaParceira> buscarPorId(@PathVariable Long id) {
        Optional<EmpresaParceira> empresaParceira = empresaParceiraService.buscarPorId(id);
        return empresaParceira.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar nova empresa parceira
    @PostMapping
    public EmpresaParceira criar(@RequestBody EmpresaParceira empresaParceira) {
        return empresaParceiraService.criar(empresaParceira);
    }

    // Atualizar empresa parceira existente
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaParceira> atualizar(@PathVariable Long id, @RequestBody EmpresaParceira empresaAtualizada) {
        Optional<EmpresaParceira> empresa = empresaParceiraService.atualizar(id, empresaAtualizada);
        return empresa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar empresa parceira
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (empresaParceiraService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

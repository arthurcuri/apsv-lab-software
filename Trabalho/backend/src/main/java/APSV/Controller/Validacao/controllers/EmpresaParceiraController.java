package APSV.Controller.Validacao.controllers;

import APSV.Controller.Validacao.models.EmpresaParceira;
import APSV.Controller.Validacao.services.EmpresaParceiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaParceiraController {

    @Autowired
    private EmpresaParceiraService service;

    @GetMapping
    public List<EmpresaParceira> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaParceira> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmpresaParceira create(@RequestBody EmpresaParceira empresaParceira) {
        return service.save(empresaParceira);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaParceira> update(@PathVariable Long id, @RequestBody EmpresaParceira empresaParceira) {
        return service.findById(id)
                .map(existing -> {
                    empresaParceira.setId(id);
                    return ResponseEntity.ok(service.save(empresaParceira));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
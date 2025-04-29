package com.coinsystem.coinsystem.services;


import org.springframework.stereotype.Service;

import com.coinsystem.coinsystem.models.EmpresaParceira;
import com.coinsystem.coinsystem.models.Vantagem;
import com.coinsystem.coinsystem.repositories.EmpresaParceiraRepository;
import com.coinsystem.coinsystem.repositories.VantagemRepository;

@Service
public class EmpresaParceiraService {
    private final EmpresaParceiraRepository empRepo;
    private final VantagemRepository vantRepo;

    public EmpresaParceiraService(EmpresaParceiraRepository empRepo,
                                  VantagemRepository vantRepo) {
        this.empRepo = empRepo;
        this.vantRepo = vantRepo;
    }

    public EmpresaParceira cadastrar(EmpresaParceira emp) {
        emp.setMoedas(0);
        return empRepo.save(emp);
    }

    public Vantagem cadastrarVantagem(Long empId, Vantagem v) {
        EmpresaParceira emp = empRepo.findById(empId)
            .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        v.setEmpresa(emp);
        return vantRepo.save(v);
    }
}

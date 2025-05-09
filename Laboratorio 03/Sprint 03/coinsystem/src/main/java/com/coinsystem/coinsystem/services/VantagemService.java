package com.coinsystem.coinsystem.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coinsystem.coinsystem.models.Vantagem;
import com.coinsystem.coinsystem.repositories.VantagemRepository;

@Service
public class VantagemService {
    private final VantagemRepository vantRepo;

    public VantagemService(VantagemRepository vantRepo) {
        this.vantRepo = vantRepo;
    }

    public List<Vantagem> listarTodas() {
        return vantRepo.findAll();
    }
}

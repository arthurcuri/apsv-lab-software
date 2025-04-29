package com.coinsystem.coinsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coinsystem.coinsystem.models.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {}
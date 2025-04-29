package com.coinsystem.coinsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coinsystem.coinsystem.models.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> { }
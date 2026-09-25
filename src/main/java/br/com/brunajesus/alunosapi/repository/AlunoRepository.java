package br.com.brunajesus.alunosapi.repository;

import br.com.brunajesus.alunosapi.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
package br.com.brunajesus.alunosapi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.brunajesus.alunosapi.entity.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    List<Aluno> findByAtivoTrue();

    Optional<Aluno> findByIdAndAtivoTrue(Long id);
}
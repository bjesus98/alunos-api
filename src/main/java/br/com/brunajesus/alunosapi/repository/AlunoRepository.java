package br.com.brunajesus.alunosapi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.brunajesus.alunosapi.entity.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    List<Aluno> findByAtivoTrue();

    Optional<Aluno> findByIdAndAtivoTrue(Long id);

    @Query("""
    SELECT aluno
    FROM Aluno aluno
    WHERE aluno.ativo = true
      AND (
        :status IS NULL
        OR aluno.status = :status
      )
      AND (
        :busca IS NULL
        OR LOWER(aluno.nome) LIKE LOWER(CONCAT('%', :busca, '%'))
        OR aluno.matricula LIKE CONCAT(:busca, '%')
      )
    """)
    Page<Aluno> buscarAtivos(
        @Param("busca") String busca,
        @Param("status") String status,
        Pageable pageable
);
}
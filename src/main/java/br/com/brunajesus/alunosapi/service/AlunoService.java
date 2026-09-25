package br.com.brunajesus.alunosapi.service;

import java.util.List;
import br.com.brunajesus.alunosapi.repository.AlunoRepository;
import br.com.brunajesus.alunosapi.dto.AlunoListagemDTO;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
    this.alunoRepository = alunoRepository;
}

public List<AlunoListagemDTO> listarTodos() {
    return alunoRepository.findAll().stream()
        .map(aluno -> new AlunoListagemDTO(
            aluno.getId(),
            aluno.getNome(),
            aluno.getMatricula(),
            aluno.getStatus()
        ))
        .toList();
}

}

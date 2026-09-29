package br.com.brunajesus.alunosapi.service;

import java.time.Year;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brunajesus.alunosapi.dto.AlunoAtualizacaoDTO;
import br.com.brunajesus.alunosapi.dto.AlunoCadastroDTO;
import br.com.brunajesus.alunosapi.dto.AlunoCadastroRespostaDTO;
import br.com.brunajesus.alunosapi.dto.AlunoDetalhesDTO;
import br.com.brunajesus.alunosapi.dto.AlunoListagemDTO;
import br.com.brunajesus.alunosapi.entity.Aluno;
import br.com.brunajesus.alunosapi.exception.AlunoNaoEncontradoException;
import br.com.brunajesus.alunosapi.exception.CpfJaCadastradoException;
import br.com.brunajesus.alunosapi.exception.EmailJaCadastradoException;
import br.com.brunajesus.alunosapi.repository.AlunoRepository;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<AlunoListagemDTO> listarTodos() {
        return alunoRepository.findByAtivoTrue()
            .stream()
            .map(aluno -> new AlunoListagemDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getMatricula(),
                aluno.getStatus()
            ))
            .toList();
    }

    public AlunoDetalhesDTO buscarPorId(Long id) {
        Aluno aluno = alunoRepository.findByIdAndAtivoTrue(id)
            .orElseThrow(() -> new AlunoNaoEncontradoException(id));

        return new AlunoDetalhesDTO(
            aluno.getId(),
            aluno.getNome(),
            aluno.getCpf(),
            aluno.getEmail(),
            aluno.getTelefone(),
            aluno.getMatricula(),
            aluno.getStatus()
        );
    }

    @Transactional
    public AlunoCadastroRespostaDTO cadastrar(AlunoCadastroDTO dados) {
        String nome = dados.nome().trim();
        String cpf = dados.cpf().replaceAll("\\D", "");
        String email = dados.email().trim().toLowerCase();
        String telefone = dados.telefone().replaceAll("\\D", "");

        if (alunoRepository.existsByCpf(cpf)) {
            throw new CpfJaCadastradoException(cpf);
        }

        if (alunoRepository.existsByEmail(email)) {
            throw new EmailJaCadastradoException(email);
        }

        Aluno aluno = new Aluno();

        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setCpf(cpf);
        aluno.setTelefone(telefone);
        aluno.setStatus("ATIVO");
        aluno.setAtivo(true);

        Aluno alunoSalvo = alunoRepository.save(aluno);

        String matricula = gerarMatricula(alunoSalvo.getId());
        alunoSalvo.setMatricula(matricula);

        alunoRepository.save(alunoSalvo);

        return new AlunoCadastroRespostaDTO(
            alunoSalvo.getId(),
            alunoSalvo.getMatricula(),
            "Aluno cadastrado com sucesso."
        );
    }

    @Transactional
    public AlunoDetalhesDTO atualizar(
        Long id,
        AlunoAtualizacaoDTO dados
    ) {
        Aluno aluno = alunoRepository.findByIdAndAtivoTrue(id)
            .orElseThrow(() -> new AlunoNaoEncontradoException(id));

        String nome = dados.nome().trim();
        String email = dados.email().trim().toLowerCase();
        String telefone = dados.telefone().replaceAll("\\D", "");
        String status = dados.status().trim().toUpperCase();

        if (alunoRepository.existsByEmailAndIdNot(email, id)) {
            throw new EmailJaCadastradoException(email);
        }

        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setTelefone(telefone);
        aluno.setStatus(status);

        Aluno alunoAtualizado = alunoRepository.save(aluno);

        return new AlunoDetalhesDTO(
            alunoAtualizado.getId(),
            alunoAtualizado.getNome(),
            alunoAtualizado.getCpf(),
            alunoAtualizado.getEmail(),
            alunoAtualizado.getTelefone(),
            alunoAtualizado.getMatricula(),
            alunoAtualizado.getStatus()
        );
    }

    @Transactional
    public void excluir(Long id) {
        Aluno aluno = alunoRepository.findByIdAndAtivoTrue(id)
            .orElseThrow(() -> new AlunoNaoEncontradoException(id));

        aluno.setAtivo(false);

        alunoRepository.save(aluno);
    }

    private String gerarMatricula(Long id) {
        int anoAtual = Year.now().getValue();

        return anoAtual + String.format("%04d", id);
    }
}
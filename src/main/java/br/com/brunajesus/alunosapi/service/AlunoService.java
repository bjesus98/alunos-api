package br.com.brunajesus.alunosapi.service;

import java.time.Year;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brunajesus.alunosapi.dto.AlunoAtualizacaoDTO;
import br.com.brunajesus.alunosapi.dto.AlunoCadastroDTO;
import br.com.brunajesus.alunosapi.dto.AlunoCadastroRespostaDTO;
import br.com.brunajesus.alunosapi.dto.AlunoDetalhesDTO;
import br.com.brunajesus.alunosapi.dto.AlunoListagemDTO;
import br.com.brunajesus.alunosapi.dto.PaginaRespostaDTO;
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

    public PaginaRespostaDTO<AlunoListagemDTO> listarTodos(
        String busca,
        String status,
        int pagina,
        int tamanho
    ) {
        if (pagina < 0) {
            throw new IllegalArgumentException(
                "A página não pode ser negativa."
            );
        }

        if (tamanho < 1 || tamanho > 100) {
            throw new IllegalArgumentException(
                "O tamanho da página deve estar entre 1 e 100."
            );
        }

        String buscaNormalizada = normalizarBusca(busca);
        String statusNormalizado = normalizarStatus(status);

        Pageable paginacao = PageRequest.of(
            pagina,
            tamanho,
            Sort.by(
                Sort.Order.asc("nome").ignoreCase()
            )
        );

        Page<Aluno> resultado = alunoRepository.buscarAtivos(
            buscaNormalizada,
            statusNormalizado,
            paginacao
        );

        List<AlunoListagemDTO> alunos = resultado
            .getContent()
            .stream()
            .map(aluno -> new AlunoListagemDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getMatricula(),
                aluno.getStatus()
            ))
            .toList();

        return new PaginaRespostaDTO<>(
            alunos,
            resultado.getNumber(),
            resultado.getSize(),
            resultado.getTotalElements(),
            resultado.getTotalPages(),
            resultado.isFirst(),
            resultado.isLast()
        );
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
    public AlunoCadastroRespostaDTO cadastrar(
        AlunoCadastroDTO dados
    ) {
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

        String matricula = gerarMatricula(
            alunoSalvo.getId()
        );

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

    private String normalizarBusca(String busca) {
        if (busca == null || busca.isBlank()) {
            return null;
        }

        return busca.trim();
    }

    private String normalizarStatus(String status) {
        if (status == null || status.isBlank()) {
            return "ATIVO";
        }

        String statusNormalizado = status
            .trim()
            .toUpperCase();

        if (statusNormalizado.equals("TODOS")) {
            return null;
        }

        if (
            !statusNormalizado.equals("ATIVO") &&
            !statusNormalizado.equals("INATIVO")
        ) {
            throw new IllegalArgumentException(
                "O status deve ser TODOS, ATIVO ou INATIVO."
            );
        }

        return statusNormalizado;
    }

    private String gerarMatricula(Long id) {
        int anoAtual = Year.now().getValue();

        return anoAtual + String.format("%04d", id);
    }
}
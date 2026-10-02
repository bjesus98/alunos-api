package br.com.brunajesus.alunosapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.brunajesus.alunosapi.dto.AlunoAtualizacaoDTO;
import br.com.brunajesus.alunosapi.dto.AlunoCadastroDTO;
import br.com.brunajesus.alunosapi.dto.AlunoCadastroRespostaDTO;
import br.com.brunajesus.alunosapi.dto.AlunoDetalhesDTO;
import br.com.brunajesus.alunosapi.dto.AlunoListagemDTO;
import br.com.brunajesus.alunosapi.dto.PaginaRespostaDTO;
import br.com.brunajesus.alunosapi.service.AlunoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public PaginaRespostaDTO<AlunoListagemDTO> listarTodos(
        @RequestParam(required = false)
        String busca,

        @RequestParam(required = false)
        String status,

        @RequestParam(defaultValue = "0")
        int page,

        @RequestParam(defaultValue = "10")
        int size
    ) {
        return alunoService.listarTodos(
            busca,
            status,
            page,
            size
        );
    }

    @GetMapping("/{id}")
    public AlunoDetalhesDTO buscarPorId(
        @PathVariable Long id
    ) {
        return alunoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<AlunoCadastroRespostaDTO> cadastrar(
        @Valid @RequestBody AlunoCadastroDTO dados
    ) {
        AlunoCadastroRespostaDTO resposta =
            alunoService.cadastrar(dados);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDetalhesDTO> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody AlunoAtualizacaoDTO dados
    ) {
        AlunoDetalhesDTO resposta =
            alunoService.atualizar(id, dados);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
        @PathVariable Long id
    ) {
        alunoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
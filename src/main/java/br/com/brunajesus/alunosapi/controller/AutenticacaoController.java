package br.com.brunajesus.alunosapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brunajesus.alunosapi.dto.LoginDTO;
import br.com.brunajesus.alunosapi.dto.LoginRespostaDTO;
import br.com.brunajesus.alunosapi.service.AutenticacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(
        AutenticacaoService autenticacaoService
    ) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRespostaDTO> login(
        @Valid @RequestBody LoginDTO dados
    ) {
        LoginRespostaDTO resposta =
            autenticacaoService.autenticar(dados);

        return ResponseEntity.ok(resposta);
    }
}
package br.com.brunajesus.alunosapi.dto;

import br.com.brunajesus.alunosapi.security.Perfil;

public record LoginRespostaDTO(
    String token,
    String tipo,
    Perfil perfil,
    Long expiracaoEmSegundos
) {
}
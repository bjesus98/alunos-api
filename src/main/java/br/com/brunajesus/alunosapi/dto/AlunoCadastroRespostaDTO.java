package br.com.brunajesus.alunosapi.dto;

public record AlunoCadastroRespostaDTO(
    Long id,
    String matricula,
    String mensagem
) {
}
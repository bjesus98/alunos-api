package br.com.brunajesus.alunosapi.dto;

public record ErroRespostaDTO(
    int status,
    String erro,
    String mensagem,
    String caminho
) {
}
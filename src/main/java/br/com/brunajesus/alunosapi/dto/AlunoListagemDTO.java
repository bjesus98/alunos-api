package br.com.brunajesus.alunosapi.dto;

public record AlunoListagemDTO(
    Long id,
    String nome,
    String matricula,
    String status
) {
}

package br.com.brunajesus.alunosapi.dto;

public record AlunoDetalhesDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    String telefone,
    String matricula,
    String status
) {

}

package br.com.brunajesus.alunosapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AlunoCadastroDTO(

    @NotBlank(message = "O nome é obrigatório.")
    @Size(
        min = 3,
        max = 120,
        message = "O nome deve possuir entre 3 e 120 caracteres."
    )
    String nome,

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail informado é inválido.")
    @Size(
        max = 150,
        message = "O e-mail deve possuir no máximo 150 caracteres."
    )
    String email,

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(
        regexp = "\\d{11}",
        message = "O CPF deve possuir 11 números."
    )
    String cpf,

    @NotBlank(message = "O telefone é obrigatório.")
    @Pattern(
        regexp = "\\d{10,11}",
        message = "O telefone deve possuir 10 ou 11 números."
    )
    String telefone

) {
}
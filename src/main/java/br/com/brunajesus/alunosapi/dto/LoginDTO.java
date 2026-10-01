package br.com.brunajesus.alunosapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(

    @NotBlank(message = "O login é obrigatório.")
    @Size(
        max = 100,
        message = "O login deve possuir no máximo 100 caracteres."
    )
    String login,

    @NotBlank(message = "A senha é obrigatória.")
    String senha

) {
}
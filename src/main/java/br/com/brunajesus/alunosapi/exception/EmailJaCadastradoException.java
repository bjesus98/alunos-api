package br.com.brunajesus.alunosapi.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email) {
        super("Já existe um aluno cadastrado com o e-mail informado: " + email);
    }
}
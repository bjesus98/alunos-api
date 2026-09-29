package br.com.brunajesus.alunosapi.exception;

public class CpfJaCadastradoException extends RuntimeException {

    public CpfJaCadastradoException(String cpf) {
        super("Já existe um aluno cadastrado com o CPF informado: " + cpf);
    }
}
package br.com.brunajesus.alunosapi.exception;

public class AlunoNaoEncontradoException extends RuntimeException {

    public AlunoNaoEncontradoException(Long id) {
        super("Aluno não encontrado com o ID: " + id);
    }
}
package br.com.brunajesus.alunosapi.exception;

public class CredenciaisInvalidasException
    extends RuntimeException {

    public CredenciaisInvalidasException() {
        super("Login ou senha inválidos.");
    }
}
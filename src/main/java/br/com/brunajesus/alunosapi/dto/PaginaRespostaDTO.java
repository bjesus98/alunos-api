package br.com.brunajesus.alunosapi.dto;

import java.util.List;

public record PaginaRespostaDTO<T>(
    List<T> conteudo,
    int pagina,
    int tamanho,
    long totalElementos,
    int totalPaginas,
    boolean primeira,
    boolean ultima
) {
}
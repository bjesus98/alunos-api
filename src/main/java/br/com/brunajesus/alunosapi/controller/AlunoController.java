package br.com.brunajesus.alunosapi.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.brunajesus.alunosapi.service.AlunoService;
import br.com.brunajesus.alunosapi.dto.AlunoListagemDTO;

@RestController 
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;
    
    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping 
    public List<AlunoListagemDTO> listarTodos() {
        return alunoService.listarTodos();
    }

}

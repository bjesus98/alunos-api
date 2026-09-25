package br.com.brunajesus.alunosapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.brunajesus.alunosapi.entity.Aluno;
import br.com.brunajesus.alunosapi.repository.AlunoRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final AlunoRepository alunoRepository;

    public DataLoader(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public void run(String... args) {

        if (alunoRepository.count() == 0) {
            Aluno aluno = new Aluno();

            aluno.setNome("Maria Silva");
            aluno.setEmail("maria.silva@email.com");
            aluno.setCpf("12345678909");
            aluno.setTelefone("21999999999");
            aluno.setMatricula("20260001");
            aluno.setStatus("ATIVO");
            aluno.setAtivo(true);

            alunoRepository.save(aluno);
        }
    }
}
package br.com.brunajesus.alunosapi.config;

import java.time.Year;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.brunajesus.alunosapi.entity.Aluno;
import br.com.brunajesus.alunosapi.repository.AlunoRepository;

@Configuration
public class AlunoDataInitializer {

    @Bean
    public CommandLineRunner criarAlunosIniciais(
        AlunoRepository alunoRepository
    ) {
        return args -> {
            List<DadosAluno> alunos = List.of(
                new DadosAluno(
                    "Maria Silva",
                    "10000000001",
                    "maria.silva@email.com",
                    "81990000001",
                    "ATIVO"
                ),
                new DadosAluno(
                    "João Santos",
                    "10000000002",
                    "joao.santos@email.com",
                    "81990000002",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Ana Oliveira",
                    "10000000003",
                    "ana.oliveira@email.com",
                    "81990000003",
                    "INATIVO"
                ),
                new DadosAluno(
                    "Carlos Lima",
                    "10000000004",
                    "carlos.lima@email.com",
                    "81990000004",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Fernanda Costa",
                    "10000000005",
                    "fernanda.costa@email.com",
                    "81990000005",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Lucas Almeida",
                    "10000000006",
                    "lucas.almeida@email.com",
                    "81990000006",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Juliana Ferreira",
                    "10000000007",
                    "juliana.ferreira@email.com",
                    "81990000007",
                    "INATIVO"
                ),
                new DadosAluno(
                    "Rafael Souza",
                    "10000000008",
                    "rafael.souza@email.com",
                    "81990000008",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Patricia Rocha",
                    "10000000009",
                    "patricia.rocha@email.com",
                    "81990000009",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Gabriel Martins",
                    "10000000010",
                    "gabriel.martins@email.com",
                    "81990000010",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Camila Ribeiro",
                    "10000000011",
                    "camila.ribeiro@email.com",
                    "81990000011",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Eduardo Barros",
                    "10000000012",
                    "eduardo.barros@email.com",
                    "81990000012",
                    "INATIVO"
                ),
                new DadosAluno(
                    "Larissa Cardoso",
                    "10000000013",
                    "larissa.cardoso@email.com",
                    "81990000013",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Bruno Carvalho",
                    "10000000014",
                    "bruno.carvalho@email.com",
                    "81990000014",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Beatriz Nascimento",
                    "10000000015",
                    "beatriz.nascimento@email.com",
                    "81990000015",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Matheus Correia",
                    "10000000016",
                    "matheus.correia@email.com",
                    "81990000016",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Renata Melo",
                    "10000000017",
                    "renata.melo@email.com",
                    "81990000017",
                    "INATIVO"
                ),
                new DadosAluno(
                    "Felipe Araujo",
                    "10000000018",
                    "felipe.araujo@email.com",
                    "81990000018",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Amanda Freitas",
                    "10000000019",
                    "amanda.freitas@email.com",
                    "81990000019",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Diego Monteiro",
                    "10000000020",
                    "diego.monteiro@email.com",
                    "81990000020",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Isabela Teixeira",
                    "10000000021",
                    "isabela.teixeira@email.com",
                    "81990000021",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Thiago Barbosa",
                    "10000000022",
                    "thiago.barbosa@email.com",
                    "81990000022",
                    "INATIVO"
                ),
                new DadosAluno(
                    "Vanessa Moura",
                    "10000000023",
                    "vanessa.moura@email.com",
                    "81990000023",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Rodrigo Gomes",
                    "10000000024",
                    "rodrigo.gomes@email.com",
                    "81990000024",
                    "ATIVO"
                ),
                new DadosAluno(
                    "Mariana Lopes",
                    "10000000025",
                    "mariana.lopes@email.com",
                    "81990000025",
                    "ATIVO"
                )
            );

            alunos.forEach(dados ->
                criarAlunoSeNaoExistir(
                    alunoRepository,
                    dados
                )
            );
        };
    }

    private void criarAlunoSeNaoExistir(
        AlunoRepository alunoRepository,
        DadosAluno dados
    ) {
        boolean cpfJaExiste =
            alunoRepository.existsByCpf(dados.cpf());

        boolean emailJaExiste =
            alunoRepository.existsByEmail(dados.email());

        if (cpfJaExiste || emailJaExiste) {
            return;
        }

        Aluno aluno = new Aluno();

        aluno.setNome(dados.nome());
        aluno.setCpf(dados.cpf());
        aluno.setEmail(dados.email());
        aluno.setTelefone(dados.telefone());
        aluno.setStatus(dados.status());
        aluno.setAtivo(true);

        Aluno alunoSalvo = alunoRepository.save(aluno);

        alunoSalvo.setMatricula(
            gerarMatricula(alunoSalvo.getId())
        );

        alunoRepository.save(alunoSalvo);
    }

    private String gerarMatricula(Long id) {
        int anoAtual = Year.now().getValue();

        return anoAtual + String.format("%04d", id);
    }

    private record DadosAluno(
        String nome,
        String cpf,
        String email,
        String telefone,
        String status
    ) {
    }
}
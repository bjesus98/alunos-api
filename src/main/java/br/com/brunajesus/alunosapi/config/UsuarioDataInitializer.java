package br.com.brunajesus.alunosapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.brunajesus.alunosapi.entity.Usuario;
import br.com.brunajesus.alunosapi.repository.UsuarioRepository;
import br.com.brunajesus.alunosapi.security.Perfil;

@Configuration
public class UsuarioDataInitializer {

    @Bean
    public CommandLineRunner criarUsuariosIniciais(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder
    ) {
        return args -> {
            criarUsuarioSeNaoExistir(
                usuarioRepository,
                passwordEncoder,
                "admin",
                "Admin@123",
                Perfil.ADMINISTRADOR
            );

            criarUsuarioSeNaoExistir(
                usuarioRepository,
                passwordEncoder,
                "leitura",
                "Leitura@123",
                Perfil.LEITURA
            );
        };
    }

    private void criarUsuarioSeNaoExistir(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder,
        String login,
        String senha,
        Perfil perfil
    ) {
        if (usuarioRepository.findByLogin(login).isPresent()) {
            return;
        }

        String senhaCodificada = passwordEncoder.encode(senha);

        Usuario usuario = new Usuario(
            login,
            senhaCodificada,
            perfil,
            true
        );

        usuarioRepository.save(usuario);
    }
}
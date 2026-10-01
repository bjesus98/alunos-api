package br.com.brunajesus.alunosapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.brunajesus.alunosapi.dto.LoginDTO;
import br.com.brunajesus.alunosapi.dto.LoginRespostaDTO;
import br.com.brunajesus.alunosapi.entity.Usuario;
import br.com.brunajesus.alunosapi.exception.CredenciaisInvalidasException;
import br.com.brunajesus.alunosapi.repository.UsuarioRepository;

@Service
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AutenticacaoService(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder,
        TokenService tokenService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public LoginRespostaDTO autenticar(LoginDTO dados) {
        String login = dados.login()
            .trim()
            .toLowerCase();

        Usuario usuario = usuarioRepository
            .findByLoginAndAtivoTrue(login)
            .orElseThrow(CredenciaisInvalidasException::new);

        boolean senhaCorreta = passwordEncoder.matches(
            dados.senha(),
            usuario.getSenha()
        );

        if (!senhaCorreta) {
            throw new CredenciaisInvalidasException();
        }

        String token = tokenService.gerarToken(usuario);

        return new LoginRespostaDTO(
            token,
            "Bearer",
            usuario.getPerfil(),
            tokenService.getExpiracaoEmSegundos()
        );
    }
}
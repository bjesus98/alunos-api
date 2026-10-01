package br.com.brunajesus.alunosapi.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import br.com.brunajesus.alunosapi.entity.Usuario;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final long expiracaoEmSegundos;

    public TokenService(
        JwtEncoder jwtEncoder,
        @Value("${jwt.expiration-seconds}")
        long expiracaoEmSegundos
    ) {
        this.jwtEncoder = jwtEncoder;
        this.expiracaoEmSegundos = expiracaoEmSegundos;
    }

    public String gerarToken(Usuario usuario) {
        Instant agora = Instant.now();
        Instant expiracao = agora.plusSeconds(expiracaoEmSegundos);

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("alunos-api")
            .issuedAt(agora)
            .expiresAt(expiracao)
            .subject(usuario.getLogin())
            .claim("perfil", usuario.getPerfil().name())
            .build();

        JwsHeader header = JwsHeader
            .with(MacAlgorithm.HS256)
            .type("JWT")
            .build();

        JwtEncoderParameters parametros =
            JwtEncoderParameters.from(header, claims);

        return jwtEncoder
            .encode(parametros)
            .getTokenValue();
    }

    public long getExpiracaoEmSegundos() {
        return expiracaoEmSegundos;
    }
}
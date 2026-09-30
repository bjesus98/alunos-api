package br.com.brunajesus.alunosapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.brunajesus.alunosapi.entity.Usuario;

public interface UsuarioRepository
    extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);

    Optional<Usuario> findByLoginAndAtivoTrue(String login);
}
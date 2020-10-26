package br.com.trabalho1.mateus.repository;

import br.com.trabalho1.mateus.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.login = ?1 and u.senha = ?2")
    Optional<Usuario> findByLoginAndSenha(String login, String senha);

    Optional<Usuario> findByLogin(String login);
}

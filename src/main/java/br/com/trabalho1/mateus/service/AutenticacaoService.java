package br.com.trabalho1.mateus.service;

import br.com.trabalho1.mateus.entity.Usuario;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validarUsuarioAdministrador(String login, String senha) {
        Usuario usuarioAutenticado = usuarioRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Usuário com login " + login + ", não encontrado na base de dados"));
        if (!senha.equalsIgnoreCase(usuarioAutenticado.getSenha())) {
            throw new RuntimeException("Senha incorreta");

        }
        if (!usuarioAutenticado.getIsAdministrador()) {
            throw new RuntimeException("Usuário não tem permissão para fazer essa operação");
        }
    }
}

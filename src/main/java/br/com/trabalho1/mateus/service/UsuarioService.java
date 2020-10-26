package br.com.trabalho1.mateus.service;

import br.com.trabalho1.mateus.dto.UsuarioDtoInput;
import br.com.trabalho1.mateus.entity.Pessoa;
import br.com.trabalho1.mateus.entity.Usuario;
import br.com.trabalho1.mateus.repository.PessoaRepository;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário com id " + id + ", não encontrado na base de dados"));
    }

    public Usuario inserir(String login, String senha, UsuarioDtoInput dtoInput) {
        Pessoa pessoa = validarSalvar(login, senha, dtoInput);
        Usuario newUsuario = Usuario.builder().pessoa(pessoa).isAdministrador(dtoInput.getIsAdministrador()).login(dtoInput.getLogin()).senha(dtoInput.getSenha()).build();
        return usuarioRepository.save(newUsuario);
    }

    public Usuario alterar(Long id, String login, String senha, UsuarioDtoInput dtoInput) {
        Usuario usuario = validarAlterar(id, login, senha, dtoInput);
        usuario.setIsAdministrador(dtoInput.getIsAdministrador());
        usuario.setLogin(dtoInput.getLogin());
        usuario.setSenha(dtoInput.getSenha());
        return usuarioRepository.save(usuario);
    }

    public void deletar(Long id, String login, String senha) {
        validarUsuarioAdministrador(login, senha);
        usuarioRepository.deleteById(id);
    }

    private Pessoa validarSalvar(String login, String senha, UsuarioDtoInput dtoInput) {
        validarUsuarioAdministrador(login, senha);

        if (dtoInput.getIdPessoa() == null) {
            throw new RuntimeException("Informe o id da Pessoa para o cadastro de usuário");
        }

        Usuario buscarPorLogin = usuarioRepository.findByLogin(dtoInput.getLogin()).orElse(null);
        if (buscarPorLogin != null) {
            throw new RuntimeException("Login já se encontra cadastrado na base de dados");
        }

        Pessoa pessoa = pessoaRepository.findById(dtoInput.getIdPessoa()).orElseThrow(() -> new RuntimeException("Informe um id de uma pessoa existente para o cadastro de usuário"));
        if (pessoa.getUsuario() != null) {
            throw new RuntimeException("Pessoa já possui um Usuário");
        }

        return pessoa;
    }

    private Usuario validarAlterar(Long id, String login, String senha, UsuarioDtoInput dtoInput) {
        validarUsuarioAdministrador(login, senha);

        Usuario usuario = buscarPorId(id);

        Usuario buscarPorLogin = usuarioRepository.findByLogin(dtoInput.getLogin()).orElse(null);
        if (buscarPorLogin != null && buscarPorLogin.getId() != id) {
            throw new RuntimeException("Login já se encontra cadastrado na base de dados");
        }
        return usuario;
    }

    private void validarUsuarioAdministrador(String login, String senha) {
        Usuario usuarioAutenticado = usuarioRepository.findByLoginAndSenha(login, senha).orElseThrow(() -> new RuntimeException("Usuário com login " + login + ", não encontrado na base de dados"));
        if (!usuarioAutenticado.getIsAdministrador()) {
            throw new RuntimeException("Usuário não tem permissão para fazer essa operação");
        }
    }
}

package br.com.trabalho1.mateus.dto.output;

import br.com.trabalho1.mateus.entity.Pessoa;
import br.com.trabalho1.mateus.entity.Usuario;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UsuarioDtoOutput {

    private Long id;
    private String login;
    private String senha;
    private Boolean isAdministrador;
    private PessoaDtoOutput pessoa;

    public UsuarioDtoOutput(Usuario usuario) {
        this.id = usuario.getId();
        this.login = usuario.getLogin();
        this.senha = usuario.getSenha();
        this.isAdministrador = usuario.getIsAdministrador();
        this.pessoa = new PessoaDtoOutput(usuario.getPessoa());
    }

    public static List<UsuarioDtoOutput> listFromUsuario(List<Usuario> list) {
        return list.stream().map(usuario -> new UsuarioDtoOutput(usuario)).collect(Collectors.toList());
    }
}

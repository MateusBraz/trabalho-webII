package br.com.trabalho1.mateus.dto.output;

import br.com.trabalho1.mateus.entity.Usuario;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UsuarioAutenticadoDtoOutput {

    private Long id;
    private String login;
    private String senha;
    private Boolean isAdministrador;
    private PessoaDtoOutput pessoa;

    public UsuarioAutenticadoDtoOutput(Usuario usuario) {
        this.id = usuario.getId();
        this.login = usuario.getLogin();
        this.senha = usuario.getSenha();
        this.isAdministrador = usuario.getIsAdministrador();
        this.pessoa = new PessoaDtoOutput(usuario.getPessoa());
    }

}

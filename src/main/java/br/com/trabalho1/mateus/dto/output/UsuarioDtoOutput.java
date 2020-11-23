package br.com.trabalho1.mateus.dto.output;

import br.com.trabalho1.mateus.entity.Usuario;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UsuarioDtoOutput {

    private Long id;
    private String login;
    private Boolean isAdministrador;
    private Long pessoaId;
    private String pessoaNome;

    public UsuarioDtoOutput(Usuario usuario){
        this.id = usuario.getId();
        this.login = usuario.getLogin();
        this.isAdministrador = usuario.getIsAdministrador();
        this.pessoaId = usuario.getPessoa().getId();
        this.pessoaNome = usuario.getPessoa().getNome();
    }

    public static List<UsuarioDtoOutput> listFromUsuario(List<Usuario> list) {
        return list.stream().map(usuario -> new UsuarioDtoOutput(usuario)).collect(Collectors.toList());
    }
}

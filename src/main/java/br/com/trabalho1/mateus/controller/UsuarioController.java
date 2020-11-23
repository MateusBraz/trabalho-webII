package br.com.trabalho1.mateus.controller;

import br.com.trabalho1.mateus.dto.input.UsuarioDtoInput;
import br.com.trabalho1.mateus.dto.output.UsuarioAutenticadoDtoOutput;
import br.com.trabalho1.mateus.dto.output.UsuarioDtoOutput;
import br.com.trabalho1.mateus.entity.Usuario;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import br.com.trabalho1.mateus.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/autenticar")
    public ResponseEntity<?> autenticar(@RequestHeader("login") String login,
                                         @RequestHeader("senha") String senha) {
        Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new ResponseEntity(new UsuarioAutenticadoDtoOutput(usuario), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> buscarTodos(@RequestHeader("login") String login,
                                         @RequestHeader("senha") String senha) {
        Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return new ResponseEntity(UsuarioDtoOutput.listFromUsuario(usuarios), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@RequestHeader("login") String login,
                                         @RequestHeader("senha") String senha,
                                         @PathVariable Long id) {
        Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Usuario usuarioBusca = usuarioService.buscarPorId(id);
        return new ResponseEntity(new UsuarioDtoOutput(usuarioBusca), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha,
                                     @Valid @RequestBody UsuarioDtoInput dtoInput) {
        Usuario usuario = usuarioService.inserir(login, senha, dtoInput);
        return new ResponseEntity(new UsuarioDtoOutput(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id,
                                     @RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha,
                                     @Valid @RequestBody UsuarioDtoInput dtoInput) {
        Usuario usuario = usuarioService.alterar(id, login, senha, dtoInput);
        return new ResponseEntity(new UsuarioDtoOutput(usuario), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable(name = "id") Long id,
                                     @RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha) {
        usuarioService.deletar(id, login, senha);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

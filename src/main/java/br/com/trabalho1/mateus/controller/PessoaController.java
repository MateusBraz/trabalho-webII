package br.com.trabalho1.mateus.controller;

import br.com.trabalho1.mateus.dto.input.PessoaDtoInput;
import br.com.trabalho1.mateus.entity.Usuario;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import br.com.trabalho1.mateus.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/pessoa")
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/buscarTodos")
    public ResponseEntity<?> buscarTodos(@RequestHeader("login") String login,
                                         @RequestHeader("senha") String senha) {
        Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new ResponseEntity(pessoaService.buscarTodos(), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarPessoas(@RequestHeader("login") String login,
                                         @RequestHeader("senha") String senha,
                                         @RequestParam(name = "idResponsavel", required = false) Long idResponsavel,
                                         @RequestParam(name = "nomeResponsavel", required = false) String nomeResponsavel,
                                         @RequestParam(name = "tipo", required = false) String tipo,
                                         @RequestParam(name = "situacao", required = false) String situacao) {
        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if(!senha.equalsIgnoreCase(usuario.getSenha())){
            throw new RuntimeException("Senha incorreta");
        }
        return new ResponseEntity(pessoaService.buscarTodosLambda(idResponsavel, nomeResponsavel, tipo, situacao), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@RequestHeader("login") String login,
                                         @RequestHeader("senha") String senha,
                                         @PathVariable Long id) {
        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if(!senha.equalsIgnoreCase(usuario.getSenha())){
            throw new RuntimeException("Senha incorreta");
        }
        return new ResponseEntity(pessoaService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha,
                                     @Valid @RequestBody PessoaDtoInput pessoaDtoInput) {
        return new ResponseEntity(pessoaService.inserir(pessoaDtoInput, login, senha), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id,
                                     @RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha,
                                     @Valid @RequestBody PessoaDtoInput pessoaDtoInput) {
        return new ResponseEntity(pessoaService.alterar(id, pessoaDtoInput, login, senha), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable(name = "id") Long id,
                                     @RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha) {
        pessoaService.deletar(id, login, senha);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}

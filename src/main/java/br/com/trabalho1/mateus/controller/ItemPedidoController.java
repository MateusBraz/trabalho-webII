package br.com.trabalho1.mateus.controller;

import br.com.trabalho1.mateus.dto.input.ItemPedidoDtoInput;
import br.com.trabalho1.mateus.dto.output.ItemPedidoDtoOutput;
import br.com.trabalho1.mateus.entity.ItemPedido;
import br.com.trabalho1.mateus.entity.Usuario;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import br.com.trabalho1.mateus.service.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-pedido")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService itemPedidoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> buscarTodos(@RequestHeader("login") String login,
                                         @RequestHeader("senha") String senha) {
        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if(!senha.equalsIgnoreCase(usuario.getSenha())){
            throw new RuntimeException("Senha incorreta");
        }
        List<ItemPedido> itemPedidoList = itemPedidoService.buscarTodos();
        return new ResponseEntity(ItemPedidoDtoOutput.listFromItemPedido(itemPedidoList), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> buscarPorId(@RequestHeader("login") String login,
                                         @RequestHeader("senha") String senha,
                                         @PathVariable("id") Long id) {
        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if(!senha.equalsIgnoreCase(usuario.getSenha())){
            throw new RuntimeException("Senha incorreta");
        }
        ItemPedido itemPedido = itemPedidoService.buscarPorId(id);
        return new ResponseEntity(new ItemPedidoDtoOutput(itemPedido), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> salvar(@RequestHeader("login") String login,
                                    @RequestHeader("senha") String senha,
                                    @RequestBody ItemPedidoDtoInput itemPedidoDtoInput) {
        ItemPedido itemPedido = itemPedidoService.salvar(login, senha, itemPedidoDtoInput);
        return new ResponseEntity(new ItemPedidoDtoOutput(itemPedido), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<?> deletar(@RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha,
                                     @PathVariable("id") Long id) {
        itemPedidoService.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

//    @PutMapping("{id}")
//    @ResponseBody
//    public ResponseEntity<?> alterar(@RequestHeader("login") String login,
//                                     @RequestHeader("senha") String senha,
//                                     @PathVariable("id") Long id,
//                                     @RequestBody ItemPedidoDtoInput itemPedidoDtoInput) {
//
//        return new ResponseEntity(itemPedidoService.alterar(login, senha, id, itemPedidoDtoInput), HttpStatus.ACCEPTED);
//    }
}

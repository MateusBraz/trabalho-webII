package br.com.trabalho1.mateus.controller;

import br.com.trabalho1.mateus.entity.ItemPedido;
import br.com.trabalho1.mateus.service.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/itemPedido")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService itemPedidoService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> buscarTodos() {
        return new ResponseEntity(itemPedidoService.buscarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        return new ResponseEntity(itemPedidoService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> salvar(@RequestBody ItemPedido body) {
        return new ResponseEntity(itemPedidoService.salvar(body), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
        itemPedidoService.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity<?> alterar(@PathVariable("id") Long id,
                                     @RequestBody ItemPedido body) {

        return new ResponseEntity(itemPedidoService.alterar(body), HttpStatus.ACCEPTED);
    }
}

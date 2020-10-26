package br.com.trabalho1.mateus.service;

import br.com.trabalho1.mateus.entity.ItemPedido;
import br.com.trabalho1.mateus.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public List<ItemPedido> buscarTodos() {
        return itemPedidoRepository.findAll();
    }

    public Object buscarPorId(Long id) {
        return itemPedidoRepository.findById(id);
    }

    public ItemPedido salvar(ItemPedido itemPedido) {
        return itemPedidoRepository.save(itemPedido);
    }

    public ItemPedido alterar(ItemPedido itemPedido) {
        return itemPedidoRepository.save(itemPedido);
    }

    public void deletar(Long id) {
        itemPedidoRepository.deleteById(id);
    }
}

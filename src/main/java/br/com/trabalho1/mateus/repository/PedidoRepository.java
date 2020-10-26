package br.com.trabalho1.mateus.repository;

import br.com.trabalho1.mateus.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}

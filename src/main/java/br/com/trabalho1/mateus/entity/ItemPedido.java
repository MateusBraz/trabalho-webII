package br.com.trabalho1.mateus.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "itemPedidoBuilder")
@Entity
@Table(name = "TB_ITEM_PEDIDO")
@SequenceGenerator(name = "seq_itempedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_itempedido")
    @Column(name = "IP_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PED_ID", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "PRO_ID", nullable = false)
    private Produto produto;

    @Column(name = "IP_QUANTIDADE")
    private Integer quantidade;
}

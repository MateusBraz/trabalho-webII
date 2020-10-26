package br.com.trabalho1.mateus.entity;

import br.com.trabalho1.mateus.enuns.EStatusPedido;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "pedidoBuilder")
@Entity
@Table(name = "TB_PEDIDO")
@SequenceGenerator(name = "seq_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_pedido")
    @Column(name = "PED_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PES_ID", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private Pessoa pessoa;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPedido> itemPedidoList;

    @Enumerated(EnumType.STRING)
    @Column(name = "PED_STATUS")
    private EStatusPedido status;

    @Column(name = "PED_DATECOMPRA")
    private LocalDate dataCompra;

    @Column(name = "PED_DATEENTREGA")
    private LocalDate dataEntrega;

    @Column(name = "PED_DESCONTO")
    private BigDecimal percentualDesconto;

}

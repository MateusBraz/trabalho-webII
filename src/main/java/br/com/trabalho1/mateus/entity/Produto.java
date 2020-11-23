package br.com.trabalho1.mateus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_PRODUTO")
@SequenceGenerator(name = "seq_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_produto")
    @Column(name = "PRO_ID")
    private Long id;

    @Column(name = "PRO_DESCRICAO")
    private String descricao;

    @Column(name = "PRO_QUANTIDADEESTOQUE")
    private Integer quantidadeEstoque;

    @Column(name = "PRO_IDADEPERMITIDA")
    private Integer idadePermitida;

    @Column(name = "PRO_PRECOCOMPRA", precision = 20, scale = 2)
    private BigDecimal precoCompra;

    @Column(name = "PRO_PRECOVENDAFISICA", precision = 20, scale = 2)
    private BigDecimal precoVendaFisica;

    @Column(name = "PRO_PRECOVENDAJURIDICA")
    private BigDecimal precoVendaJuridica;

    public Produto(Long id) {
        this.id = id;
    }
}

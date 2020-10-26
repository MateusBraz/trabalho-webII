package br.com.trabalho1.mateus.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_USUARIO")
@SequenceGenerator(name = "seq_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_usuario")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PES_ID", nullable = false)
    @JsonIgnore
    private Pessoa pessoa;

    @Column(name = "USU_ISADMINISTRADOR")
    private Boolean isAdministrador;

    @Column(name = "USU_LOGIN", unique = true)
    private String login;

    @Column(name = "USU_SENHA")
    private String senha;
}

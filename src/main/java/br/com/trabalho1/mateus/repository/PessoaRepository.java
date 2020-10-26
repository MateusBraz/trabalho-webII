package br.com.trabalho1.mateus.repository;

import br.com.trabalho1.mateus.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Override
    @Query("select p from Pessoa p where p.id = ?1")
    Optional<Pessoa> findById(Long aLong);

    @Query("select p from Pessoa p where p.idResponsavel.id = ?1")
    Pessoa findByIdResponsavelAndId(Long id);

    @Query(value = "select * from TB_PESSOA where PES_RG = ?1", nativeQuery = true)
    Pessoa findByRg(String rg);

    @Query(value = "select * from TB_PESSOA where PES_CPF = ?1", nativeQuery = true)
    Pessoa findByCpf(String cpf);

    @Query(value = "select * from TB_PESSOA where PES_CNPJ = ?1", nativeQuery = true)
    Pessoa findByCnpj(String cnpj);
}

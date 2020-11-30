package br.com.trabalho1.mateus.service;

import br.com.trabalho1.mateus.dto.input.PessoaDtoInput;
import br.com.trabalho1.mateus.entity.Fisica;
import br.com.trabalho1.mateus.entity.Juridica;
import br.com.trabalho1.mateus.entity.Pessoa;
import br.com.trabalho1.mateus.entity.Usuario;
import br.com.trabalho1.mateus.enuns.ESituacaoPessoa;
import br.com.trabalho1.mateus.repository.PessoaRepository;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private PessoaRepository pessoaRepository;

    Pessoa responsavel;

    public List<Pessoa> buscarTodos() {
        return pessoaRepository.findAll();
    }

    public List<Pessoa> buscarTodosLambda(Long idResponsavel, String nomeResponsavel, String tipo, String situacao) {
//        List<Pessoa> listaLambda = buscarTodos()
//                .stream()
//                .filter(p ->  p.getIdResponsavel() != null && p.getIdResponsavel().getId().equals(idResponsavel))
//                .filter(p ->  p.getIdResponsavel() != null && p.getIdResponsavel().getNome().equals(nomeResponsavel))
//                .filter(p ->  p.toString().equals(tipo))
//                .filter(p ->  p.getSituacao().toString().equals(situacao))
//                .collect(Collectors.toList());
//        return listaLambda;
        return buscarTodos();
    }

    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.findById(id).orElseThrow(() -> new RuntimeException("Pessoa com id " + id + ", não encontrado na base de dados"));
    }

    public Pessoa inserir(PessoaDtoInput pessoaDtoInput, String login, String senha) {
        Integer idade = validar(null, pessoaDtoInput.getTipo(), pessoaDtoInput, login, senha);
        Pessoa newPessoa;
        if (pessoaDtoInput.getTipo().equalsIgnoreCase("FISICA")) {
            newPessoa = Fisica.fisicaBuilder()
                    .id(null)
                    .pedidos(new ArrayList<>())
                    .usuario(null)
                    .idResponsavel(idade >= 18 ? null : responsavel)
                    .situacao(pessoaDtoInput.getSituacao().equalsIgnoreCase("ATIVO") ? ESituacaoPessoa.ATIVO : ESituacaoPessoa.INATIVO)
                    .nome(pessoaDtoInput.getNome())
                    .apelido(pessoaDtoInput.getApelido())
                    .dataNascimento(pessoaDtoInput.getDataNascimento())
                    .cpf(pessoaDtoInput.getCpf())
                    .rg(pessoaDtoInput.getRg())
                    .build();
        } else {
            newPessoa = Juridica.juridicaBuilder()
                    .id(null)
                    .pedidos(new ArrayList<>())
                    .usuario(null)
                    .idResponsavel(null)
                    .situacao(pessoaDtoInput.getSituacao().equalsIgnoreCase("ATIVO") ? ESituacaoPessoa.ATIVO : ESituacaoPessoa.INATIVO)
                    .nome(pessoaDtoInput.getNome())
                    .apelido(pessoaDtoInput.getApelido())
                    .dataNascimento(pessoaDtoInput.getDataNascimento())
                    .cnpj(pessoaDtoInput.getCnpj())
                    .build();
        }
        return pessoaRepository.save(newPessoa);
    }

    public Pessoa alterar(Long id, PessoaDtoInput pessoaDtoInput, String login, String senha) {
        Pessoa pessoaAlterar = buscarPorId(id);
        String tipo = pessoaAlterar.toString();
        Integer idade = validar(id, tipo, pessoaDtoInput, login, senha);
        pessoaAlterar.setNome(pessoaDtoInput.getNome());
        pessoaAlterar.setApelido(pessoaDtoInput.getApelido());
        pessoaAlterar.setSituacao(pessoaDtoInput.getSituacao().equalsIgnoreCase("ATIVO") ? ESituacaoPessoa.ATIVO : ESituacaoPessoa.INATIVO);
        pessoaAlterar.setDataNascimento(pessoaDtoInput.getDataNascimento());
        if (tipo.equalsIgnoreCase("FISICA")) {
            Fisica fisica = (Fisica) pessoaAlterar;
            fisica.setIdResponsavel(idade >= 18 ? null : responsavel);
            fisica.setCpf(pessoaDtoInput.getCpf());
            fisica.setRg(pessoaDtoInput.getRg());
            return pessoaRepository.save(fisica);
        } else {
            Juridica juridica = (Juridica) pessoaAlterar;
            juridica.setCnpj(pessoaDtoInput.getCnpj());
            return pessoaRepository.save(juridica);
        }
    }

    public void deletar(Long id, String login, String senha) {
        autenticacaoService.validarUsuarioAdministrador(login, senha);
        Pessoa pessoa = pessoaRepository.findByIdResponsavelAndId(id);
        if (pessoa != null) {
            throw new IllegalArgumentException("Pessoa com id " + id + " é responsável por outra pessoa, não é possível excluir");
        }
        pessoaRepository.deleteById(id);
    }

    private Integer validar(Long id, String tipo, PessoaDtoInput pessoaDtoInput, String login, String senha) {
        autenticacaoService.validarUsuarioAdministrador(login, senha);
        LocalDate dataAtual = LocalDate.now();
        Period idade = Period.between(pessoaDtoInput.getDataNascimento(), dataAtual);

        if (id == null && tipo == null) {
            throw new RuntimeException("Tipo de pessoa não pode ser nulo");
        }

        if (tipo.equalsIgnoreCase("FISICA")) {
            if (idade.getYears() < 18 && pessoaDtoInput.getIdResponsavel() == null) {
                throw new RuntimeException("Pessoa possui idade menor que 18 anos, insira o id do Responsável");
            }

            if (idade.getYears() < 18) {
                Pessoa menorIdade = pessoaRepository.findByIdResponsavelAndId(pessoaDtoInput.getIdResponsavel());
                if ((id == null && menorIdade != null) || (menorIdade != null && !id.equals(menorIdade.getId()))) {
                    throw new RuntimeException("Pessoa com id " + pessoaDtoInput.getIdResponsavel() + " já é responsável por uma pessoa");
                }
                responsavel = pessoaRepository.findById(pessoaDtoInput.getIdResponsavel()).orElseThrow(() -> new RuntimeException("Responsável com id " + pessoaDtoInput.getIdResponsavel() + " não encontrado na base de dados"));
                Period idadeResponsavel = Period.between(responsavel.getDataNascimento(), dataAtual);
                if (idadeResponsavel.getYears() < 18) {
                    throw new RuntimeException("Responsável precisa ser maior de idade");
                }
            }

            if (!pessoaDtoInput.getSituacao().equalsIgnoreCase("ATIVO") && !pessoaDtoInput.getSituacao().equalsIgnoreCase("INATIVO")) {
                throw new RuntimeException("Situação inválida");
            }

            if (id == null && !tipo.equalsIgnoreCase("FISICA") && !tipo.equalsIgnoreCase("JURIDICA")) {
                throw new RuntimeException("Tipo de pessoa inválido");
            }

            if (pessoaDtoInput.getCpf() == null || pessoaDtoInput.getRg() == null) {
                throw new RuntimeException("Pessoa do tipo Física, mas os dados do cpf ou rg se encontra nulo");
            }
            Pessoa pessoaCpf = pessoaRepository.findByCpf(pessoaDtoInput.getCpf());
            if ((id == null && pessoaCpf != null) || (pessoaCpf != null && !id.equals(pessoaCpf.getId()))) {
                throw new RuntimeException("CPF " + pessoaDtoInput.getCpf() + ", já se encontra cadastrado na base de dados");
            }
            Pessoa pessoaRg = pessoaRepository.findByRg(pessoaDtoInput.getRg());
            if ((id == null && pessoaRg != null) || (pessoaRg != null && !id.equals(pessoaRg.getId()))) {
                throw new RuntimeException("RG " + pessoaDtoInput.getRg() + ", já se encontra cadastrado na base de dados");
            }
        } else {
            if (idade.getYears() < 18) {
                throw new RuntimeException("Pessoa do tipo Jurídica não pode ter menos de 18 anos.");
            }
            if (pessoaDtoInput.getCnpj() == null) {
                throw new RuntimeException("Pessoa do tipo Jurídica, mas o cnpj se encontra nulo");
            }
            Pessoa pessoaCnpj = pessoaRepository.findByCnpj(pessoaDtoInput.getCnpj());
            if ((id == null && pessoaCnpj != null) || (pessoaCnpj != null && !id.equals(pessoaCnpj.getId()))) {
                throw new RuntimeException("CNPJ " + pessoaDtoInput.getCnpj() + ", já se encontra cadastrado na base de dados");
            }
        }

        return idade.getYears();
    }

}

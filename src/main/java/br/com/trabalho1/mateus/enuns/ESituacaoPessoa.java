package br.com.trabalho1.mateus.enuns;

public enum ESituacaoPessoa {
    ATIVO("Ativo", "A"),
    INATIVO("Inativo", "I");

    private String nome;
    private String sigla;

    ESituacaoPessoa(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }
}

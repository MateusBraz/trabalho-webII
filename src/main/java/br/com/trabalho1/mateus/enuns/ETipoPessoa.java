package br.com.trabalho1.mateus.enuns;

public enum ETipoPessoa {
    FISICA("Fisica", "F"),
    JURIDICA("Juridica", "J");

    private String nome;
    private String sigla;

    ETipoPessoa(String nome, String sigla){
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

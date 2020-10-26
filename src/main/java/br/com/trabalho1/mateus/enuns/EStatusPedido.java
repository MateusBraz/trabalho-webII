package br.com.trabalho1.mateus.enuns;

public enum EStatusPedido {
    REALIZADO("Realizado", "R"),
    CANCELADO("Cancelado", "C");

    private String nome;
    private String sigla;

    EStatusPedido(String nome, String sigla){
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

package com.example.postagensredesocial.model;

public class Postagem {
    private String nome, descricao;
    private String fotoUrl;

    public Postagem(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Postagem(){

    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setFotourl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getNome(){
        return this.nome;
    }

    public String getDescricao(){
        return this.descricao;
    }

    public String getFotourl(){
        return this.fotoUrl;
    }
}

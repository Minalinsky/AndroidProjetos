package com.example.postagensredesocial.model;

public class Postagem {
    private String nome, descricao;
    private int foto;


    public Postagem(){

    }

    public Postagem(String nome, String descricao, int foto) {
        this.nome = nome;
        this.descricao = descricao;
        this.foto = foto;
    }

    public String getNome(){
        return this.nome;
    }

    public String getDescricao(){
        return this.descricao;
    }

    public int getFoto(){
        return this.foto;
    }
}

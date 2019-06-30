package com.example.listadetarefas.model;

import java.io.Serializable;

public class Tarefa implements Serializable {
    private Long id; //identificador do Banco de Dados
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

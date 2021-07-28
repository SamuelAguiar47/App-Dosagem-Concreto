package com.cursoandroid.appdosagemconcreto.model;

import java.io.Serializable;

public class ItemPontoCimento implements Serializable {
    //Atributos
    private Long Id;
    private String nomeDoPonto;

    //Métodos
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNomeDoPonto() {
        return nomeDoPonto;
    }

    public void setNomeDoPonto(String nomeDoPonto) {
        this.nomeDoPonto = nomeDoPonto;
    }
}

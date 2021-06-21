package com.cursoandroid.appdosagemconcreto.model;

import java.io.Serializable;

public class ItemTracoSalvo implements Serializable {

    private Long id;
    private String nomeTraco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTraco() {
        return nomeTraco;
    }

    public void setNomeTraco(String nomeTraco) {
        this.nomeTraco = nomeTraco;
    }
}

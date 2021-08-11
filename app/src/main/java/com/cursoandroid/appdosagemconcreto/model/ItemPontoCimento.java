package com.cursoandroid.appdosagemconcreto.model;

import java.io.Serializable;

public class ItemPontoCimento implements Serializable {
    //Atributos
    private Long Id;
    private String nomeDoPonto;
    private Double ValorDeAC;
    private Double ValorDeFcj;

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

    public Double getValorDeAC() {
        return ValorDeAC;
    }

    public void setValorDeAC(Double valorDeAC) {
        ValorDeAC = valorDeAC;
    }

    public Double getValorDeFcj() {
        return ValorDeFcj;
    }

    public void setValorDeFcj(Double valorDeFcj) {
        ValorDeFcj = valorDeFcj;
    }
}

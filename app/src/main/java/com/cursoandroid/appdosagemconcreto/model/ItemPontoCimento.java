package com.cursoandroid.appdosagemconcreto.model;

import java.io.Serializable;

public class ItemPontoCimento implements Serializable {
    //Atributos
    private Long Id;
    private String nomeDoPonto;
    private Double ValorDeAC;
    private Double ValorDeFck;

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

    public Double getValorDeFck() {
        return ValorDeFck;
    }

    public void setValorDeFck(Double valorDeFck) {
        ValorDeFck = valorDeFck;
    }
}

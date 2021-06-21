package com.cursoandroid.appdosagemconcreto.materiais;

import java.io.Serializable;

public class Concreto implements Serializable {

    // Atributos

    private Double fck;
    private Double abatimento;
    private Double fcj;
    private Double fatorAguaCimento;
    private Double desvioPadrao;
    private Double traco[];

    // Métodos


    public Concreto() {
    }

    public Concreto(Double fck, Double abatimento, Double desvioPadrao) {
        this.fck = fck;
        this.abatimento = abatimento;
        this.desvioPadrao = desvioPadrao;
    }

    public void calcularTraco() {

    }

    //Getters e Setters

    public Double getFck() {
        return fck;
    }

    public void setFck(Double fck) {
        this.fck = fck;
    }

    public Double getAbatimento() {
        return abatimento;
    }

    public void setAbatimento(Double abatimento) {
        this.abatimento = abatimento;
    }

    public Double getFcj() {
        return fcj;
    }

    public void setFcj(Double fcj) {
        this.fcj = fcj;
    }

    public Double getFatorAguaCimento() {
        return fatorAguaCimento;
    }

    public void setFatorAguaCimento(Double fatorAguaCimento) {
        this.fatorAguaCimento = fatorAguaCimento;
    }

    public Double getDesvioPadrao() {
        return desvioPadrao;
    }

    public void setDesvioPadrao(Double desvioPadrao) {
        this.desvioPadrao = desvioPadrao;
    }

    public Double[] getTraco() {
        return traco;
    }

    public void setTraco(Double[] traco) {
        this.traco = traco;
    }

}

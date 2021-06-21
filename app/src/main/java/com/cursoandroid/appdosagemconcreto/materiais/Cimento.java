package com.cursoandroid.appdosagemconcreto.materiais;

import java.io.Serializable;

public class Cimento implements Serializable {

    // Atributos

    private String especificacoes;
    private Double massaEspecifica;
    private Double consumoDeCimento;
    private Double volumeDeCimento;

    //Métodos


    public Cimento() {
    }

    public Cimento(String especificacoes, Double massaEspecifica) {
        this.especificacoes = especificacoes;
        this.massaEspecifica = massaEspecifica;
    }

    public void calcularVc() {

    }

    //Getters e Setters


    public String getEspecificacoes() {
        return especificacoes;
    }

    public void setEspecificacoes(String especificacoes) {
        this.especificacoes = especificacoes;
    }

    public Double getMassaEspecifica() {
        return massaEspecifica;
    }

    public void setMassaEspecifica(Double massaEspecifica) {
        this.massaEspecifica = massaEspecifica;
    }

    public Double getConsumoDeCimento() {
        return consumoDeCimento;
    }

    public void setConsumoDeCimento(Double consumoDeCimento) {
        this.consumoDeCimento = consumoDeCimento;
    }

    public Double getVolumeDeCimento() {
        return volumeDeCimento;
    }

    public void setVolumeDeCimento(Double volumeDeCimento) {
        this.volumeDeCimento = volumeDeCimento;
    }

}

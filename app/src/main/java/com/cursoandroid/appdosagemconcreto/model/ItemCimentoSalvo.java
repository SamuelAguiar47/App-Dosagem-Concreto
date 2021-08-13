package com.cursoandroid.appdosagemconcreto.model;

import java.io.Serializable;

public class ItemCimentoSalvo implements Serializable {

    // Atributos
    private Long Id;
    private String nomeDoCimento, tempoDeCura;
    private int QtdeDePontos;
    private Double K1, K2;

    //Métodos

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNomeDoCimento() {
        return nomeDoCimento;
    }

    public void setNomeDoCimento(String nomeDoCimento) {
        this.nomeDoCimento = nomeDoCimento;
    }

    public String getTempoDeCura() {
        return tempoDeCura;
    }

    public void setTempoDeCura(String tempoDeCura) {
        this.tempoDeCura = tempoDeCura;
    }

    public int getQtdeDePontos() {
        return QtdeDePontos;
    }

    public void setQtdeDePontos(int qtdeDePontos) {
        QtdeDePontos = qtdeDePontos;
    }

    public Double getK1() {
        return K1;
    }

    public void setK1(Double k1) {
        K1 = k1;
    }

    public Double getK2() {
        return K2;
    }

    public void setK2(Double k2) {
        K2 = k2;
    }

}

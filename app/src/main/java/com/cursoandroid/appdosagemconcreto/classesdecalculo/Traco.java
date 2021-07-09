package com.cursoandroid.appdosagemconcreto.classesdecalculo;

import java.io.Serializable;

public class Traco implements Serializable {

    // Atributos
    private String nomeDoTraco;
    private Double[] tracoPara1M3DeConcretoEmMassa = new Double[4];
    private Double[] tracoUnitarioEmMassa = new Double[4];
    private Double[] tracoPara1Saco50KgDeCimentoEmMassa = new Double[4];
    private Double[] tracoPara1Saco50KgDeCimentoEmVolume = new Double[4];
    private Double[] tracoPara1Saco50KgDeCimentoEmPadiolas = new Double[4];
    private String tipoDeTraco;
    private String tracoExibido;
    private String dataDoTraco;

    // Métodos


    public String getNomeDoTraco() {
        return nomeDoTraco;
    }

    public void setNomeDoTraco(String nomeDoTraco) {
        this.nomeDoTraco = nomeDoTraco;
    }

    public Double[] getTracoUnitarioEmMassa() {
        return tracoUnitarioEmMassa;
    }

    public void setTracoUnitarioEmMassa(Double[] tracoUnitarioEmMassa) {
        this.tracoUnitarioEmMassa = tracoUnitarioEmMassa;
    }

    public String getDataDoTraco() {
        return dataDoTraco;
    }

    public void setDataDoTraco(String dataDotraco) {
        this.dataDoTraco = dataDotraco;
    }

    public String getTracoExibido() {
        return tracoExibido;
    }

    public void setTracoExibido(String tracoExibido) {
        this.tracoExibido = tracoExibido;
    }

    public String getTipoDeTraco() {
        return tipoDeTraco;
    }

    public void setTipoDeTraco(String tipoDeTraco) {
        this.tipoDeTraco = tipoDeTraco;
    }
}

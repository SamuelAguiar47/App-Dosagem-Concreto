package com.cursoandroid.appdosagemconcreto.classesdecalculo;

import java.io.Serializable;

public class Traco implements Serializable {

    // Atributos
    private String nomeDoTraco;
    protected Double[] tracoPara1M3DeConcretoEmMassa = new Double[4];
    protected Double[] tracoUnitarioEmMassa = new Double[4];
    protected Double[] tracoPara1Saco50KgDeCimentoEmMassa = new Double[4];
    protected Double[] tracoPara1Saco50KgDeCimentoEmVolume = new Double[4];
    protected Double[] tracoPara1Saco50KgDeCimentoEmPadiolas = new Double[4];
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

    public Double[] getTracoPara1M3DeConcretoEmMassa() {
        return tracoPara1M3DeConcretoEmMassa;
    }

    public Double[] getTracoUnitarioEmMassa() {
        return tracoUnitarioEmMassa;
    }

    public Double[] getTracoPara1Saco50KgDeCimentoEmMassa() {
        return tracoPara1Saco50KgDeCimentoEmMassa;
    }

    public Double[] getTracoPara1Saco50KgDeCimentoEmVolume() {
        return tracoPara1Saco50KgDeCimentoEmVolume;
    }

    public Double[] getTracoPara1Saco50KgDeCimentoEmPadiolas() {
        return tracoPara1Saco50KgDeCimentoEmPadiolas;
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

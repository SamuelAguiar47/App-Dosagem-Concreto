package com.cursoandroid.appdosagemconcreto.classesdecalculo;

import java.io.Serializable;

public class Traco implements Serializable {

    // Atributos
    private String nomeDoTraco;
    private Double[] tracoEmMassa = new Double[4];
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

    public Double[] getTracoEmMassa() {
        return tracoEmMassa;
    }

    public void setTracoEmMassa(Double[] tracoEmMassa) {
        this.tracoEmMassa = tracoEmMassa;
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

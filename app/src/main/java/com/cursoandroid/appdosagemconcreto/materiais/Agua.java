package com.cursoandroid.appdosagemconcreto.materiais;

import java.io.Serializable;

public class Agua implements Serializable {

    // Atributos

    private final int MASSAESPECIFICA = 1;
    private Double consumoDeAgua;
    private Double volumeDeAgua;

    // Métodos

    public Agua() {
    }

    public void calcularCa() {

    }

    public void calcularVa() {

    }

    // Getters e Setters


    public int getMassaEspecifica() {
        return MASSAESPECIFICA;
    }

    public Double getConsumoDeAgua() {
        return consumoDeAgua;
    }

    public void setConsumoDeAgua(Double consumoDeAgua) {
        this.consumoDeAgua = consumoDeAgua;
    }

    public Double getVolumeDeAgua() {
        return volumeDeAgua;
    }

    public void setVolumeDeAgua(Double volumeDeAgua) {
        this.volumeDeAgua = volumeDeAgua;
    }

}

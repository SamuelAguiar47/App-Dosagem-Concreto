package com.cursoandroid.appdosagemconcreto.materiais;

import java.io.Serializable;

public class Areia implements Serializable {

    // Atributos

    private Double moduloDefinura;
    private Double massaEspecifica;
    private Double massaUnitaria;
    private Double umidadeDaAreia;
    private Double inchamentoDaAreia;
    private Double consumoDeAreia;
    private Double volumeDeAreia;

    // Métodos


    public Areia() {
    }

    public Areia(Double moduloDefinura, Double massaEspecifica, Double massaUnitaria) {
        this.moduloDefinura = moduloDefinura;
        this.massaEspecifica = massaEspecifica;
        this.massaUnitaria = massaUnitaria;
    }

    public void calcularCareia() {

    }

    public void calcularVareia() {

    }

    // Getters e Setters

    public Double getModuloDefinura() {
        return moduloDefinura;
    }

    public void setModuloDefinura(Double moduloDefinura) {
        this.moduloDefinura = moduloDefinura;
    }

    public Double getMassaEspecifica() {
        return massaEspecifica;
    }

    public void setMassaEspecifica(Double massaEspecifica) {
        this.massaEspecifica = massaEspecifica;
    }

    public Double getMassaUnitaria() {
        return massaUnitaria;
    }

    public void setMassaUnitaria(Double massaUnitaria) {
        this.massaUnitaria = massaUnitaria;
    }

    public Double getUmidadeDaAreia() {
        return umidadeDaAreia;
    }

    public void setUmidadeDaAreia(Double umidadeDaAreia) {
        this.umidadeDaAreia = umidadeDaAreia;
    }

    public Double getInchamentoDaAreia() {
        return inchamentoDaAreia;
    }

    public void setInchamentoDaAreia(Double inchamentoDaAreia) {
        this.inchamentoDaAreia = inchamentoDaAreia;
    }

    public Double getConsumoDeAreia() {
        return consumoDeAreia;
    }

    public void setConsumoDeAreia(Double consumoDeAreia) {
        this.consumoDeAreia = consumoDeAreia;
    }

    public Double getVolumeDeAreia() {
        return volumeDeAreia;
    }

    public void setVolumeDeAreia(Double volumeDeAreia) {
        this.volumeDeAreia = volumeDeAreia;
    }

}

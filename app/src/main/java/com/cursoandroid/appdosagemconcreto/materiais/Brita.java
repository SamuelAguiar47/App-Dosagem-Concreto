package com.cursoandroid.appdosagemconcreto.materiais;

import java.io.Serializable;

public class Brita implements Serializable {

    // Atributos

    private Double massaEspecifica;
    private Double massaUnitariaComp;
    private Double massaUnitaria;
    private Double diametroMaximo;
    private Double consumoDeBrita;
    private Double volumeDeBritaTabela;
    private Double volumeDeBrita;

    // Métodos


    public Brita() {
    }

    public Brita(Double massaEspecifica, Double massaUnitariaComp, Double massaUnitaria, Double diametroMaximo) {
        this.massaEspecifica = massaEspecifica;
        this.massaUnitariaComp = massaUnitariaComp;
        this.massaUnitaria = massaUnitaria;
        this.diametroMaximo = diametroMaximo;
    }

    // Getters e Setters


    public Double getMassaEspecifica() {
        return massaEspecifica;
    }

    public void setMassaEspecifica(Double massaEspecifica) {
        this.massaEspecifica = massaEspecifica;
    }

    public Double getMassaUnitariaComp() {
        return massaUnitariaComp;
    }

    public void setMassaUnitariaComp(Double massaUnitariaComp) {
        this.massaUnitariaComp = massaUnitariaComp;
    }

    public Double getMassaUnitaria() {
        return massaUnitaria;
    }

    public void setMassaUnitaria(Double massaUnitaria) {
        this.massaUnitaria = massaUnitaria;
    }

    public Double getDiametroMaximo() {
        return diametroMaximo;
    }

    public void setDiametroMaximo(Double diametroMaximo) {
        this.diametroMaximo = diametroMaximo;
    }

    public Double getConsumoDeBrita() {
        return consumoDeBrita;
    }

    public void setConsumoDeBrita(Double consumoDeBrita) {
        this.consumoDeBrita = consumoDeBrita;
    }

    public Double getVolumeDeBritaTabela() {
        return volumeDeBritaTabela;
    }

    public void setVolumeDeBritaTabela(Double volumeDeBritaTabela) {
        this.volumeDeBritaTabela = volumeDeBritaTabela;
    }

    public Double getVolumeDeBrita() {
        return volumeDeBrita;
    }

    public void setVolumeDeBrita(Double volumeDeBrita) {
        this.volumeDeBrita = volumeDeBrita;
    }

}

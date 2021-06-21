package com.cursoandroid.appdosagemconcreto.tabelaseabacos;

import java.io.Serializable;

public class TabelaConsumoDeAgua implements Serializable {

    // Atributos

    private Double abatimento;
    private Double diametroMaximo;
    private Double consumoDeAguaObtido;
    private Double[][] tabelaDeConsumoDeAguaAprox = new Double[3][5];

    // Métodos


    public TabelaConsumoDeAgua() {
    }

    public TabelaConsumoDeAgua(Double abatimento, Double diametroMaximo) {
        this.abatimento = abatimento;
        this.diametroMaximo = diametroMaximo;
    }

    public void calcularConsumoDeAgua() {

        int linha = 0;
        int coluna = 0;

        tabelaDeConsumoDeAguaAprox[0][0] = 220.00;
        tabelaDeConsumoDeAguaAprox[0][1] = 195.00;
        tabelaDeConsumoDeAguaAprox[0][2] = 190.00;
        tabelaDeConsumoDeAguaAprox[0][3] = 185.00;
        tabelaDeConsumoDeAguaAprox[0][4] = 180.00;

        tabelaDeConsumoDeAguaAprox[1][0] = 225.00;
        tabelaDeConsumoDeAguaAprox[1][1] = 200.00;
        tabelaDeConsumoDeAguaAprox[1][2] = 195.00;
        tabelaDeConsumoDeAguaAprox[1][3] = 190.00;
        tabelaDeConsumoDeAguaAprox[1][4] = 185.00;

        tabelaDeConsumoDeAguaAprox[2][0] = 230.00;
        tabelaDeConsumoDeAguaAprox[2][1] = 205.00;
        tabelaDeConsumoDeAguaAprox[2][2] = 200.00;
        tabelaDeConsumoDeAguaAprox[2][3] = 195.00;
        tabelaDeConsumoDeAguaAprox[2][4] = 190.00;

        if (this.abatimento >= 40 && abatimento <= 60) {
            linha = 0;
        } else if (this.abatimento > 60 && abatimento <= 80) {
            linha = 1;
        } else if (this.abatimento > 80 && abatimento <= 100) {
            linha = 2;
        }

        if (this.diametroMaximo <= 9.5) {
            coluna = 0;
        } else if (this.diametroMaximo <= 19.0) {
            coluna = 1;
        } else if (this.diametroMaximo <= 25.0) {
            coluna = 2;
        } else if (this.diametroMaximo <= 32.0) {
            coluna = 3;
        } else if (diametroMaximo <= 38.0) {
            coluna = 4;
        }

        this.setConsumoDeAguaObtido(tabelaDeConsumoDeAguaAprox[linha][coluna]);

    }
    // Getters e Setters


    public Double getAbatimento() {
        return abatimento;
    }

    public void setAbatimento(Double abatimento) {
        this.abatimento = abatimento;
    }

    public Double getDiametroMaximo() {
        return diametroMaximo;
    }

    public void setDiametroMaximo(Double diametroMaximo) {
        this.diametroMaximo = diametroMaximo;
    }

    public Double getConsumoDeAguaObtido() {
        return consumoDeAguaObtido;
    }

    public void setConsumoDeAguaObtido(Double consumoDeAguaObtido) {
        this.consumoDeAguaObtido = consumoDeAguaObtido;
    }

}

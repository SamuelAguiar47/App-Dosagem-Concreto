package com.cursoandroid.appdosagemconcreto.tabelaseabacos;

import java.io.Serializable;

public class TabelaVolumeDeBrita implements Serializable {

    // Atributos

    private Double moduloDeFinura;
    private Double diametroMaximo;
    private Double volumeDeBritaObtido;
    private Double[][] tabelaVolumeDeBrita = new Double[10][5];

    // Métodos

    public TabelaVolumeDeBrita(Double moduloDeFinura, Double diametroMaximo) {
        this.moduloDeFinura = moduloDeFinura;
        this.diametroMaximo = diametroMaximo;
    }

    public void calcularVolumeDeBrita() {

        int linha = 0;
        int coluna = 0;

        tabelaVolumeDeBrita[0][0] = 0.645;
        tabelaVolumeDeBrita[0][1] = 0.770;
        tabelaVolumeDeBrita[0][2] = 0.795;
        tabelaVolumeDeBrita[0][3] = 0.820;
        tabelaVolumeDeBrita[0][4] = 0.845;

        tabelaVolumeDeBrita[1][0] = 0.625;
        tabelaVolumeDeBrita[1][1] = 0.750;
        tabelaVolumeDeBrita[1][2] = 0.775;
        tabelaVolumeDeBrita[1][3] = 0.800;
        tabelaVolumeDeBrita[1][4] = 0.825;

        tabelaVolumeDeBrita[2][0] = 0.605;
        tabelaVolumeDeBrita[2][1] = 0.730;
        tabelaVolumeDeBrita[2][2] = 0.755;
        tabelaVolumeDeBrita[2][3] = 0.780;
        tabelaVolumeDeBrita[2][4] = 0.805;

        tabelaVolumeDeBrita[3][0] = 0.585;
        tabelaVolumeDeBrita[3][1] = 0.710;
        tabelaVolumeDeBrita[3][2] = 0.735;
        tabelaVolumeDeBrita[3][3] = 0.760;
        tabelaVolumeDeBrita[3][4] = 0.785;

        tabelaVolumeDeBrita[4][0] = 0.565;
        tabelaVolumeDeBrita[4][1] = 0.690;
        tabelaVolumeDeBrita[4][2] = 0.715;
        tabelaVolumeDeBrita[4][3] = 0.740;
        tabelaVolumeDeBrita[4][4] = 0.765;

        tabelaVolumeDeBrita[5][0] = 0.545;
        tabelaVolumeDeBrita[5][1] = 0.670;
        tabelaVolumeDeBrita[5][2] = 0.695;
        tabelaVolumeDeBrita[5][3] = 0.720;
        tabelaVolumeDeBrita[5][4] = 0.745;

        tabelaVolumeDeBrita[6][0] = 0.525;
        tabelaVolumeDeBrita[6][1] = 0.650;
        tabelaVolumeDeBrita[6][2] = 0.675;
        tabelaVolumeDeBrita[6][3] = 0.700;
        tabelaVolumeDeBrita[6][4] = 0.725;

        tabelaVolumeDeBrita[7][0] = 0.505;
        tabelaVolumeDeBrita[7][1] = 0.630;
        tabelaVolumeDeBrita[7][2] = 0.655;
        tabelaVolumeDeBrita[7][3] = 0.680;
        tabelaVolumeDeBrita[7][4] = 0.705;

        tabelaVolumeDeBrita[8][0] = 0.485;
        tabelaVolumeDeBrita[8][1] = 0.610;
        tabelaVolumeDeBrita[8][2] = 0.635;
        tabelaVolumeDeBrita[8][3] = 0.660;
        tabelaVolumeDeBrita[8][4] = 0.685;

        tabelaVolumeDeBrita[9][0] = 0.465;
        tabelaVolumeDeBrita[9][1] = 0.590;
        tabelaVolumeDeBrita[9][2] = 0.615;
        tabelaVolumeDeBrita[9][3] = 0.640;
        tabelaVolumeDeBrita[9][4] = 0.665;

        if (this.moduloDeFinura == 1.8) {
            linha = 0;
        } else if (this.moduloDeFinura == 2.0) {
            linha = 1;
        } else if (this.moduloDeFinura == 2.2) {
            linha = 2;
        } else if (this.moduloDeFinura == 2.4) {
            linha = 3;
        } else if (this.moduloDeFinura == 2.6) {
            linha = 4;
        } else if (this.moduloDeFinura == 2.8) {
            linha = 5;
        } else if (this.moduloDeFinura == 3.0) {
            linha = 6;
        } else if (this.moduloDeFinura == 3.2) {
            linha = 7;
        } else if (this.moduloDeFinura == 3.4) {
            linha = 8;
        } else if (this.moduloDeFinura == 3.6) {
            linha = 9;
        }

        if (this.diametroMaximo <= 9.5) {
            coluna = 0;
        } else if (this.diametroMaximo > 9.5 && this.diametroMaximo <= 19.5) {
            coluna = 1;
        } else if (this.diametroMaximo > 19.5 && this.diametroMaximo <= 25.0) {
            coluna = 2;
        } else if (this.diametroMaximo > 25.0 && this.diametroMaximo <= 32.0) {
            coluna = 3;
        } else if (this.diametroMaximo > 32.0 && this.diametroMaximo <= 38.0) {
            coluna = 4;
        }

        //Interpolação

                if (this.moduloDeFinura != 1.8 &&
                this.moduloDeFinura != 2.0 &&
                this.moduloDeFinura != 2.2 &&
                this.moduloDeFinura != 2.4 &&
                this.moduloDeFinura != 2.6 &&
                this.moduloDeFinura != 2.8 &&
                this.moduloDeFinura != 3.0 &&
                this.moduloDeFinura != 3.2 &&
                this.moduloDeFinura != 3.4 &&
                this.moduloDeFinura != 3.6
        ) {
            int linhaMfMenor = 0;
            int linhaMfMaior = 0;
            double mfMenor = 0.0;
            double mfMaior = 0.0;
            double vbParaMfMenor = 0.0;
            double vbParaMfMaior = 0.0;
            double difInterpolacao = 0.0;
            double difMfMaioreMenor = 0.0;
            double difVbMaioreMenor = 0.0;
            double vbInterpolado = 0.0;

            if (this.moduloDeFinura > 1.8 && this.moduloDeFinura < 2.0) {
                linhaMfMenor = 0;
                linhaMfMaior = 1;
                mfMenor = 1.8;
                mfMaior = 2.0;
            } else if (this.moduloDeFinura > 2.0 && this.moduloDeFinura < 2.2) {
                linhaMfMenor = 1;
                linhaMfMaior = 2;
                mfMenor = 2.0;
                mfMaior = 2.2;
            } else if (this.moduloDeFinura > 2.2 && this.moduloDeFinura < 2.4) {
                linhaMfMenor = 2;
                linhaMfMaior = 3;
                mfMenor = 2.2;
                mfMaior = 2.4;
            } else if (this.moduloDeFinura > 2.4 && this.moduloDeFinura < 2.6) {
                linhaMfMenor = 3;
                linhaMfMaior = 4;
                mfMenor = 2.4;
                mfMaior = 2.6;
            } else if (this.moduloDeFinura > 2.6 && this.moduloDeFinura < 2.8) {
                linhaMfMenor = 4;
                linhaMfMaior = 5;
                mfMenor = 2.6;
                mfMaior = 2.8;
            } else if (this.moduloDeFinura > 2.8 && this.moduloDeFinura < 3.0) {
                linhaMfMenor = 5;
                linhaMfMaior = 6;
                mfMenor = 2.8;
                mfMaior = 3.0;
            } else if (this.moduloDeFinura > 3.0 && this.moduloDeFinura < 3.2) {
                linhaMfMenor = 6;
                linhaMfMaior = 7;
                mfMenor = 3.0;
                mfMaior = 3.2;
            } else if (this.moduloDeFinura > 3.2 && this.moduloDeFinura < 3.4) {
                linhaMfMenor = 7;
                linhaMfMaior = 8;
                mfMenor = 3.2;
                mfMaior = 3.4;
            } else if (this.moduloDeFinura > 3.4 && this.moduloDeFinura < 3.6) {
                linhaMfMenor = 8;
                linhaMfMaior = 9;
                mfMenor = 3.4;
                mfMaior = 3.6;
            }

            vbParaMfMenor = tabelaVolumeDeBrita[linhaMfMenor][coluna];
            vbParaMfMaior = tabelaVolumeDeBrita[linhaMfMaior][coluna];
            difInterpolacao = this.moduloDeFinura - mfMenor;
            difMfMaioreMenor = mfMaior - mfMenor;
            difVbMaioreMenor = vbParaMfMaior - vbParaMfMenor;
            vbInterpolado = ((difInterpolacao/difMfMaioreMenor)*difVbMaioreMenor)+vbParaMfMenor;

            this.setVolumeDeBritaObtido(vbInterpolado);

        } else {

            this.setVolumeDeBritaObtido(tabelaVolumeDeBrita[linha][coluna]);
        }
    }

    //Getters e Setters


    public Double getModuloDeFinura() {
        return moduloDeFinura;
    }

    public void setModuloDeFinura(Double moduloDeFinura) {
        this.moduloDeFinura = moduloDeFinura;
    }

    public Double getDiametroMaximo() {
        return diametroMaximo;
    }

    public void setDiametroMaximo(Double diametroMaximo) {
        this.diametroMaximo = diametroMaximo;
    }

    public Double getVolumeDeBritaObtido() {
        return volumeDeBritaObtido;
    }

    public void setVolumeDeBritaObtido(Double volumeDeBrita) {
        this.volumeDeBritaObtido = volumeDeBrita;
    }

}

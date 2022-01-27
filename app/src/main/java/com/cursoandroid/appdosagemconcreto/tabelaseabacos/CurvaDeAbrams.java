package com.cursoandroid.appdosagemconcreto.tabelaseabacos;

import android.content.Context;
import android.util.Log;

import com.cursoandroid.appdosagemconcreto.activities.ResultadosActivity;
import com.cursoandroid.appdosagemconcreto.classesdecalculo.RegressaoLinear;
import com.cursoandroid.appdosagemconcreto.helper.CurvaCimentoDAO;
import com.cursoandroid.appdosagemconcreto.model.ItemPontoCimento;

import java.io.Serializable;
import java.util.List;

public class CurvaDeAbrams implements Serializable {

    // Atributos
    private Double fcj;
    private Double fatorAguaCimentoObtido;
    private RegressaoLinear regressaoLinear;

    //Curvas
    private Double[][] curvaCimento;

    private Double[][] curva28DiasCP29 = new Double[9][2];
    private Double[][] curva28DiasCP32 = new Double[9][2];
    private Double[][] curva28DiasCP35 = new Double[9][2];
    private Double[][] curva28DiasCP38 = new Double[9][2];
    private Double[][] curva28DiasCP41 = new Double[9][2];
    private Double[][] curva28DiasCP44 = new Double[9][2];
    private Double[][] curva28DiasCP47 = new Double[9][2];
    private Double[][] curva28DiasCP50 = new Double[9][2];

    //Coeficientes
    Double K1, K2;


    public Double[][] getCurva(String nomeDoCimento, Context context) {
        CurvaCimentoDAO curvaCimentoDAO = new CurvaCimentoDAO(context);
        List<ItemPontoCimento> listaDePontosCimento = curvaCimentoDAO.listar(nomeDoCimento);

        curvaCimento = new Double[listaDePontosCimento.size()][2];

        int cont = 0;
        while (cont < listaDePontosCimento.size()) {
            curvaCimento[cont][0] = listaDePontosCimento.get(cont).getValorDeFcj();
            curvaCimento[cont][1] = listaDePontosCimento.get(cont).getValorDeAC();
            cont += 1;
        }

        return curvaCimento;
    }

    /*
    public Double[][] getCurva28DiasCP29() {
        curva28DiasCP29[0][0] = 31.8;
        curva28DiasCP29[0][1] = 0.4;
        curva28DiasCP29[1][0] = 28.0;
        curva28DiasCP29[1][1] = 0.45;
        curva28DiasCP29[2][0] = 24.2;
        curva28DiasCP29[2][1] = 0.5;
        curva28DiasCP29[3][0] = 21.2;
        curva28DiasCP29[3][1] = 0.55;
        curva28DiasCP29[4][0] = 18.7;
        curva28DiasCP29[4][1] = 0.6;
        curva28DiasCP29[5][0] = 16.2;
        curva28DiasCP29[5][1] = 0.65;
        curva28DiasCP29[6][0] = 14.0;
        curva28DiasCP29[6][1] = 0.7;
        curva28DiasCP29[7][0] = 12.2;
        curva28DiasCP29[7][1] = 0.75;
        curva28DiasCP29[8][0] = 10.8;
        curva28DiasCP29[8][1] = 0.8;

        return curva28DiasCP29;
    }

    public Double[][] getCurva28DiasCP32() {
        curva28DiasCP32[0][0] = 35.1;
        curva28DiasCP32[0][1] = 0.4;
        curva28DiasCP32[1][0] = 31.0;
        curva28DiasCP32[1][1] = 0.45;
        curva28DiasCP32[2][0] = 26.7;
        curva28DiasCP32[2][1] = 0.5;
        curva28DiasCP32[3][0] = 23.4;
        curva28DiasCP32[3][1] = 0.55;
        curva28DiasCP32[4][0] = 20.6;
        curva28DiasCP32[4][1] = 0.6;
        curva28DiasCP32[5][0] = 17.9;
        curva28DiasCP32[5][1] = 0.65;
        curva28DiasCP32[6][0] = 15.5;
        curva28DiasCP32[6][1] = 0.7;
        curva28DiasCP32[7][0] = 13.5;
        curva28DiasCP32[7][1] = 0.75;
        curva28DiasCP32[8][0] = 11.9;
        curva28DiasCP32[8][1] = 0.8;

        return curva28DiasCP32;
    }

    public Double[][] getCurva28DiasCP35() {
        curva28DiasCP35[0][0] = 38.5;
        curva28DiasCP35[0][1] = 0.4;
        curva28DiasCP35[1][0] = 33.5;
        curva28DiasCP35[1][1] = 0.45;
        curva28DiasCP35[2][0] = 29.2;
        curva28DiasCP35[2][1] = 0.5;
        curva28DiasCP35[3][0] = 25.6;
        curva28DiasCP35[3][1] = 0.55;
        curva28DiasCP35[4][0] = 22.5;
        curva28DiasCP35[4][1] = 0.6;
        curva28DiasCP35[5][0] = 19.6;
        curva28DiasCP35[5][1] = 0.65;
        curva28DiasCP35[6][0] = 17.0;
        curva28DiasCP35[6][1] = 0.7;
        curva28DiasCP35[7][0] = 14.8;
        curva28DiasCP35[7][1] = 0.75;
        curva28DiasCP35[8][0] = 13.0;
        curva28DiasCP35[8][1] = 0.8;

        return curva28DiasCP35;
    }

    public Double[][] getCurva28DiasCP38() {
        curva28DiasCP38[0][0] = 41.9;
        curva28DiasCP38[0][1] = 0.4;
        curva28DiasCP38[1][0] = 36.4;
        curva28DiasCP38[1][1] = 0.45;
        curva28DiasCP38[2][0] = 31.8;
        curva28DiasCP38[2][1] = 0.5;
        curva28DiasCP38[3][0] = 27.9;
        curva28DiasCP38[3][1] = 0.55;
        curva28DiasCP38[4][0] = 24.4;
        curva28DiasCP38[4][1] = 0.6;
        curva28DiasCP38[5][0] = 21.2;
        curva28DiasCP38[5][1] = 0.65;
        curva28DiasCP38[6][0] = 18.5;
        curva28DiasCP38[6][1] = 0.7;
        curva28DiasCP38[7][0] = 16.1;
        curva28DiasCP38[7][1] = 0.75;
        curva28DiasCP38[8][0] = 14.1;
        curva28DiasCP38[8][1] = 0.8;

        return curva28DiasCP38;
    }

    public Double[][] getCurva28DiasCP41() {
        curva28DiasCP41[0][0] = 45.3;
        curva28DiasCP41[0][1] = 0.4;
        curva28DiasCP41[1][0] = 39.3;
        curva28DiasCP41[1][1] = 0.45;
        curva28DiasCP41[2][0] = 34.3;
        curva28DiasCP41[2][1] = 0.5;
        curva28DiasCP41[3][0] = 30.0;
        curva28DiasCP41[3][1] = 0.55;
        curva28DiasCP41[4][0] = 26.3;
        curva28DiasCP41[4][1] = 0.6;
        curva28DiasCP41[5][0] = 22.9;
        curva28DiasCP41[5][1] = 0.65;
        curva28DiasCP41[6][0] = 20.0;
        curva28DiasCP41[6][1] = 0.7;
        curva28DiasCP41[7][0] = 17.4;
        curva28DiasCP41[7][1] = 0.75;
        curva28DiasCP41[8][0] = 15.3;
        curva28DiasCP41[8][1] = 0.8;

        return curva28DiasCP41;
    }

    public Double[][] getCurva28DiasCP44() {
        curva28DiasCP44[0][0] = 48.6;
        curva28DiasCP44[0][1] = 0.4;
        curva28DiasCP44[1][0] = 42.1;
        curva28DiasCP44[1][1] = 0.45;
        curva28DiasCP44[2][0] = 36.8;
        curva28DiasCP44[2][1] = 0.5;
        curva28DiasCP44[3][0] = 32.3;
        curva28DiasCP44[3][1] = 0.55;
        curva28DiasCP44[4][0] = 28.1;
        curva28DiasCP44[4][1] = 0.6;
        curva28DiasCP44[5][0] = 24.5;
        curva28DiasCP44[5][1] = 0.65;
        curva28DiasCP44[6][0] = 21.5;
        curva28DiasCP44[6][1] = 0.7;
        curva28DiasCP44[7][0] = 18.7;
        curva28DiasCP44[7][1] = 0.75;
        curva28DiasCP44[8][0] = 16.4;
        curva28DiasCP44[8][1] = 0.8;

        return curva28DiasCP44;
    }

    public Double[][] getCurva28DiasCP47() {
        curva28DiasCP47[0][0] = 52.0;
        curva28DiasCP47[0][1] = 0.4;
        curva28DiasCP47[1][0] = 45.0;
        curva28DiasCP47[1][1] = 0.45;
        curva28DiasCP47[2][0] = 39.3;
        curva28DiasCP47[2][1] = 0.5;
        curva28DiasCP47[3][0] = 34.5;
        curva28DiasCP47[3][1] = 0.55;
        curva28DiasCP47[4][0] = 30.0;
        curva28DiasCP47[4][1] = 0.6;
        curva28DiasCP47[5][0] = 26.2;
        curva28DiasCP47[5][1] = 0.65;
        curva28DiasCP47[6][0] = 23.0;
        curva28DiasCP47[6][1] = 0.7;
        curva28DiasCP47[7][0] = 20.0;
        curva28DiasCP47[7][1] = 0.75;
        curva28DiasCP47[8][0] = 17.5;
        curva28DiasCP47[8][1] = 0.8;

        return curva28DiasCP47;
    }

    public Double[][] getCurva28DiasCP50() {
        curva28DiasCP50[0][0] = 55.0;
        curva28DiasCP50[0][1] = 0.4;
        curva28DiasCP50[1][0] = 47.9;
        curva28DiasCP50[1][1] = 0.45;
        curva28DiasCP50[2][0] = 41.8;
        curva28DiasCP50[2][1] = 0.5;
        curva28DiasCP50[3][0] = 36.7;
        curva28DiasCP50[3][1] = 0.55;
        curva28DiasCP50[4][0] = 31.9;
        curva28DiasCP50[4][1] = 0.6;
        curva28DiasCP50[5][0] = 27.7;
        curva28DiasCP50[5][1] = 0.65;
        curva28DiasCP50[6][0] = 24.3;
        curva28DiasCP50[6][1] = 0.7;
        curva28DiasCP50[7][0] = 21.3;
        curva28DiasCP50[7][1] = 0.75;
        curva28DiasCP50[8][0] = 18.6;
        curva28DiasCP50[8][1] = 0.8;

        return curva28DiasCP50;
    }
    */

    // Métodos


    public CurvaDeAbrams() {
    }

    public CurvaDeAbrams(Double fcj, String especific, Context context) {
        this.fcj = fcj;

        this.fatorAguaCimentoObtido = calculoRegressaoLinear(fcj, especific, context);
        /*
        if (especific.equals("CP29")) {
            this.fatorAguaCimentoObtido = calculoCP29_28Dias(fcj);
        } else if (especific.equals("CP32")) {
            this.fatorAguaCimentoObtido = calculoCP32_28Dias(fcj);
        } else if (especific.equals("CP35")) {
            this.fatorAguaCimentoObtido = calculoCP35_28Dias(fcj);
        } else if (especific.equals("CP38")) {
            this.fatorAguaCimentoObtido = calculoCP38_28Dias(fcj);
        } else if (especific.equals("CP41")) {
            this.fatorAguaCimentoObtido = calculoCP41_28Dias(fcj);
        } else if (especific.equals("CP44")) {
            this.fatorAguaCimentoObtido = calculoCP44_28Dias(fcj);
        } else if (especific.equals("CP47")) {
            this.fatorAguaCimentoObtido = calculoCP47_28Dias(fcj);
        }  else if (especific.equals("CP50")) {
            this.fatorAguaCimentoObtido = calculoCP50_28Dias(fcj);
        }*/
    }

    public Double calculoRegressaoLinear(Double fcj, String nomeDoCimento, Context context) {
        Double fatorAC = calcularRegressaoLinear(fcj, getCurva(nomeDoCimento, context));
        return (fatorAC);
    }

    /*
    public Double calculoCP29_28Dias(Double fcj) {
        this.fcj = fcj;
        int c = 0;
        Double fcjAnt = getCurva28DiasCP32()[0][0];
        Double fcjPost = getCurva28DiasCP32()[1][0];
        while (fcjPost > this.fcj) {
            c = c + 1;
            fcjAnt = getCurva28DiasCP32()[c][0];
            fcjPost = getCurva28DiasCP32()[c+1][0];
        }
        Double fatACAnt = getCurva28DiasCP32()[c][1];
        Double fatACPost = getCurva28DiasCP32()[c+1][1];
        return ((this.fcj - fcjAnt)/(fcjPost - fcjAnt))*(fatACPost - fatACAnt) + fatACAnt;
        Double fatorAC = calcularRegressaoLinear(fcj,getCurva28DiasCP29());
        return (fatorAC);
    }

    public Double calculoCP32_28Dias(Double fcj) {
        Double fatorAC = calcularRegressaoLinear(fcj,getCurva28DiasCP32());
        return (fatorAC);
    }

    public Double calculoCP35_28Dias(Double fcj) {
        Double fatorAC = calcularRegressaoLinear(fcj,getCurva28DiasCP35());
        return (fatorAC);
    }

    public Double calculoCP38_28Dias(Double fcj) {
        Double fatorAC = calcularRegressaoLinear(fcj,getCurva28DiasCP38());
        return (fatorAC);
    }

    public Double calculoCP41_28Dias(Double fcj) {
        Double fatorAC = calcularRegressaoLinear(fcj,getCurva28DiasCP41());
        return (fatorAC);
    }

    public Double calculoCP44_28Dias(Double fcj) {
        Double fatorAC = calcularRegressaoLinear(fcj,getCurva28DiasCP44());
        return (fatorAC);
    }

    public Double calculoCP47_28Dias(Double fcj) {
        Double fatorAC = calcularRegressaoLinear(fcj,getCurva28DiasCP47());
        return (fatorAC);
    }

    public Double calculoCP50_28Dias(Double fcj) {
        Double fatorAC = calcularRegressaoLinear(fcj,getCurva28DiasCP50());
        return (fatorAC);
    }
    */

    public Double calcularRegressaoLinear(Double fcj, Double[][] arrayCurva) {
        this.fcj = fcj;
        regressaoLinear = new RegressaoLinear(fcj, arrayCurva);
        return (regressaoLinear.getFatorAC());

    }

    public Double calcularFcjPeloFatorAC(Double fatorAC) {
        return regressaoLinear.calcularFcjPeloFatorAC(fatorAC);
    }

    // Getters e Setters


    public Double getFcj() {
        return fcj;
    }

    public void setFcj(Double fcj) {
        this.fcj = fcj;
    }

    public Double getFatorAguaCimentoObtido() {
        return fatorAguaCimentoObtido;
    }

    public void setFatorAguaCimentoObtido(Double fatorAguaCimentoObtido) {
        this.fatorAguaCimentoObtido = fatorAguaCimentoObtido;
    }

}

package com.cursoandroid.appdosagemconcreto.classesdecalculo;

import android.util.Log;

public class RegressaoLinear {

    // 1 - Atributos

    private Double fcj,somax, somay, y, yLinha, somayLinha, x, xAoQuadrado,
                   somaxAoQuadrado, yLinhaAoQuadrado, somayLinhaAoQuadrado, xyLinha,
                   somaxyLinha, Sxx, Syy, Sxy, b, a,  K1, K2, fatorAC;
    private Double[][] arrayCurva;
    private int n, c;

    //===========================================================
    // 2 - Métodos
    // 2.1 - Construtor

    public RegressaoLinear(Double fcj, Double[][] arrayCurva) {
        this.fcj = fcj;
        this.arrayCurva = arrayCurva;
        n = arrayCurva.length;

        calcularSomaX();
        calcularSomaY();
        calcularSomaYLinha();
        calcularSomaXAoQuadrado();
        calcularSomaYLinhaAoQuadrado();
        calcularSomaXYLinha();
        calcularFatorAC();
    }

    //------------------------------------------------------------
    // 2.2 - Métodos do cálculo
    public void calcularSomaX() {
        somax = 0.0;
        c = 0;
        while (c < n) {
            somax += arrayCurva[c][1];
            c += 1;
        }
    }

    public void calcularSomaY() {
        somay = 0.0;
        c = 0;
        while (c < n) {
            somay += arrayCurva[c][0];
            c += 1;
        }
    }

    public void calcularSomaYLinha() {
        somayLinha = 0.0;
        c = 0;
        while (c < n) {
            y = arrayCurva[c][0];
            yLinha = Math.log10(y);
            somayLinha += yLinha;
            Log.i("SOMAYLINHA", "y= " + y + " y'= " + yLinha);
            c += 1;
        }
        Log.i("SOMAYLINHA", "Somay'= " + somayLinha);
    }

    public void calcularSomaXAoQuadrado() {
        somaxAoQuadrado = 0.0;
        c = 0;
        while (c < n) {
            x = arrayCurva[c][1];
            xAoQuadrado = Math.pow(x, 2);
            somaxAoQuadrado += xAoQuadrado;
            Log.i("SOMAYLINHA", "x= " + x + " x²= " + xAoQuadrado);
            c += 1;
        }
        Log.i("SOMAYLINHA", "Somax'= " + somaxAoQuadrado);
    }

    public void calcularSomaYLinhaAoQuadrado() {
        somayLinhaAoQuadrado = 0.0;
        c = 0;
        while (c < n) {
            y = arrayCurva[c][0];
            yLinha = Math.log10(y);
            yLinhaAoQuadrado = Math.pow(yLinha, 2);
            somayLinhaAoQuadrado += yLinhaAoQuadrado;
            Log.i("SOMAYLINHA", "y'= " + yLinha + " y'²= " + yLinhaAoQuadrado);
            c += 1;
        }
        Log.i("SOMAYLINHA", "Somay'²= " + somayLinhaAoQuadrado);
    }

    public void calcularSomaXYLinha() {
        somaxyLinha = 0.0;
        c = 0;
        while (c < n) {
            x = arrayCurva[c][1];
            y = arrayCurva[c][0];
            yLinha = Math.log10(y);
            xyLinha = x * yLinha;
            somaxyLinha += xyLinha;
            Log.i("SOMAYLINHA", "x= " + x + "y'= " + yLinha + " xy'= " + xyLinha);
            c += 1;
        }
        Log.i("SOMAYLINHA", "Somaxy'= " + somaxyLinha);
    }

    public void calcularFatorAC() {
        Sxx = somaxAoQuadrado - Math.pow(somax, 2) / n;
        Syy = somayLinhaAoQuadrado - Math.pow(somayLinha, 2) / n;
        Sxy = somaxyLinha - (somayLinha * somax) / n;
        b = Sxy / Sxx;
        a = (somayLinha / n) - (b * (somax / n));
        K1 = Math.pow(10, a);
        K2 = 1 / Math.pow(10, b);
        fatorAC = (-Math.log10(fcj) + Math.log10(K1)) / Math.log10(K2);
    }

    public Double calcularFcjPeloFatorAC(Double fatorAC) {
        Double fcjObtido = K1/Math.pow(K2, fatorAC);
        return fcjObtido;
    }

    //------------------------------------------------------------------------
    // 2.3 - Métodos Getters e Setters


    public Double getFcj() {
        return fcj;
    }

    public void setFcj(Double fcj) {
        this.fcj = fcj;
    }

    public Double getSomax() {
        return somax;
    }

    public void setSomax(Double somax) {
        this.somax = somax;
    }

    public Double getSomay() {
        return somay;
    }

    public void setSomay(Double somay) {
        this.somay = somay;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getyLinha() {
        return yLinha;
    }

    public void setyLinha(Double yLinha) {
        this.yLinha = yLinha;
    }

    public Double getSomayLinha() {
        return somayLinha;
    }

    public void setSomayLinha(Double somayLinha) {
        this.somayLinha = somayLinha;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getxAoQuadrado() {
        return xAoQuadrado;
    }

    public void setxAoQuadrado(Double xAoQuadrado) {
        this.xAoQuadrado = xAoQuadrado;
    }

    public Double getSomaxAoQuadrado() {
        return somaxAoQuadrado;
    }

    public void setSomaxAoQuadrado(Double somaxAoQuadrado) {
        this.somaxAoQuadrado = somaxAoQuadrado;
    }

    public Double getyLinhaAoQuadrado() {
        return yLinhaAoQuadrado;
    }

    public void setyLinhaAoQuadrado(Double yLinhaAoQuadrado) {
        this.yLinhaAoQuadrado = yLinhaAoQuadrado;
    }

    public Double getSomayLinhaAoQuadrado() {
        return somayLinhaAoQuadrado;
    }

    public void setSomayLinhaAoQuadrado(Double somayLinhaAoQuadrado) {
        this.somayLinhaAoQuadrado = somayLinhaAoQuadrado;
    }

    public Double getXyLinha() {
        return xyLinha;
    }

    public void setXyLinha(Double xyLinha) {
        this.xyLinha = xyLinha;
    }

    public Double getSomaxyLinha() {
        return somaxyLinha;
    }

    public void setSomaxyLinha(Double somaxyLinha) {
        this.somaxyLinha = somaxyLinha;
    }

    public Double getSxx() {
        return Sxx;
    }

    public void setSxx(Double sxx) {
        Sxx = sxx;
    }

    public Double getSyy() {
        return Syy;
    }

    public void setSyy(Double syy) {
        Syy = syy;
    }

    public Double getSxy() {
        return Sxy;
    }

    public void setSxy(Double sxy) {
        Sxy = sxy;
    }

    public Double getB() {
        return b;
    }

    public void setB(Double b) {
        this.b = b;
    }

    public Double getA() {
        return a;
    }

    public void setA(Double a) {
        this.a = a;
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

    public Double getFatorAC() {
        return fatorAC;
    }

    public void setFatorAC(Double fatorAC) {
        this.fatorAC = fatorAC;
    }

    public Double[][] getArrayCurva() {
        return arrayCurva;
    }

    public void setArrayCurva(Double[][] arrayCurva) {
        this.arrayCurva = arrayCurva;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}

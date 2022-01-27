package com.cursoandroid.appdosagemconcreto.classesdecalculo;

import android.content.Context;

import com.cursoandroid.appdosagemconcreto.helper.CurvaCimentoDAO;
import com.cursoandroid.appdosagemconcreto.materiais.Agua;
import com.cursoandroid.appdosagemconcreto.materiais.Areia;
import com.cursoandroid.appdosagemconcreto.materiais.Brita;
import com.cursoandroid.appdosagemconcreto.materiais.Cimento;
import com.cursoandroid.appdosagemconcreto.materiais.Concreto;
import com.cursoandroid.appdosagemconcreto.tabelaseabacos.CurvaDeAbrams;
import com.cursoandroid.appdosagemconcreto.tabelaseabacos.TabelaConsumoDeAgua;
import com.cursoandroid.appdosagemconcreto.tabelaseabacos.TabelaVolumeDeBrita;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Dosagem implements Serializable {

    // Atributos

    private Long id;
    public Traco traco = new Traco();

    public Concreto concreto;
    public Cimento cimento;
    public Areia areia;
    public Brita brita;
    public Agua agua;
    public CurvaDeAbrams curvaDeAbrams;
    public TabelaConsumoDeAgua tabelaCa;
    public TabelaVolumeDeBrita tabelaVb;
    private Double[] tracoUnitarioEmMassa = new Double[4];
    //private String tracoExibido;

    public Double larguraDaPadiola;
    public Double comprimentoDaPadiola;
    public Double alturaDaPadiolaDeAreia;
    public Double alturaDaPadiolaDeBrita;
    public Double volumeDaPadiolaDeAreia;
    public Double volumeDaPadiolaDeBrita;
    public Double quantidadeDePadiolasDeAreia;
    public Double quantidadeDePadiolasDeBrita;


    // Classes de arredondamento
    private DecimalFormat arred0 = new DecimalFormat("####");
    private DecimalFormat arred1 = new DecimalFormat("##0.0");
    private DecimalFormat arred1X = new DecimalFormat("##0.#");
    private DecimalFormat arred2 = new DecimalFormat("##0.00");
    private DecimalFormat arred2x = new DecimalFormat("##0.##");
    private DecimalFormat arred3 = new DecimalFormat("##0.000");
    private DecimalFormat arred3x = new DecimalFormat("##0.###");

    // Métodos

    public void inserirInformacoesIncicias(Concreto concreto, Cimento cimento, Areia areia, Brita brita, Agua agua) {
        this.concreto = concreto;
        this.cimento = cimento;
        this.areia = areia;
        this.brita = brita;
        this.agua = agua;
    }

    public void calcularFcj() {

        this.concreto.setFcj(this.concreto.getFck() + 1.65*this.concreto.getDesvioPadrao());

    }

    public void determinarFatorAguaCimento(Context context) {

        this.curvaDeAbrams = new CurvaDeAbrams(this.concreto.getFcj(), this.cimento.getEspecificacoes(), context);
        this.concreto.setFatorAguaCimento(this.curvaDeAbrams.getFatorAguaCimentoObtido());

    }

    public void determinarConsumoDeAgua() {

        this.tabelaCa = new TabelaConsumoDeAgua(this.concreto.getAbatimento(), this.brita.getDiametroMaximo());
        this.tabelaCa.calcularConsumoDeAgua();
        this.agua.setConsumoDeAgua(this.tabelaCa.getConsumoDeAguaObtido());

    }

    public void determinarConsumoDeCimento() {

        this.cimento.setConsumoDeCimento(this.agua.getConsumoDeAgua() / this.concreto.getFatorAguaCimento());

    }

    public void determinarVolumeDeBrita() {

        this.tabelaVb = new TabelaVolumeDeBrita(this.areia.getModuloDefinura(), this.brita.getDiametroMaximo());
        this.tabelaVb.calcularVolumeDeBrita();
        this.brita.setVolumeDeBritaTabela(this.tabelaVb.getVolumeDeBritaObtido());

    }

    public void determinarConsumoDeBrita() {

        this.brita.setConsumoDeBrita(this.brita.getVolumeDeBritaTabela() * this.brita.getMassaUnitariaComp());

    }

    public void determinarVolumeDeAreia() {

        this.cimento.setVolumeDeCimento(this.cimento.getConsumoDeCimento() / this.cimento.getMassaEspecifica());
        this.brita.setVolumeDeBrita(this.brita.getConsumoDeBrita() / this.cimento.getMassaEspecifica());
        this.agua.setVolumeDeAgua(this.agua.getConsumoDeAgua() / 1000.0);
        this.areia.setVolumeDeAreia( 1.0 - (this.cimento.getVolumeDeCimento() + this.brita.getVolumeDeBrita() + this.agua.getVolumeDeAgua()));

    }

    public void determinarConsumoDeAreia() {

        this.areia.setConsumoDeAreia(this.areia.getVolumeDeAreia() * this.areia.getMassaEspecifica());

    }

    public void determinarTracoPara1M3DeConcretoEmMassa() {
        this.traco.tracoPara1M3DeConcretoEmMassa[0] = this.cimento.getConsumoDeCimento();
        this.traco.tracoPara1M3DeConcretoEmMassa[1] = this.areia.getConsumoDeAreia();
        this.traco.tracoPara1M3DeConcretoEmMassa[2] = this.brita.getConsumoDeBrita();
        this.traco.tracoPara1M3DeConcretoEmMassa[3] = this.agua.getConsumoDeAgua();
    }

    public void determinarTracoUnitarioEmMassa() {

        this.tracoUnitarioEmMassa[0] = 1.0;                                                               // cimento
        this.tracoUnitarioEmMassa[1] = this.areia.getConsumoDeAreia()/this.cimento.getConsumoDeCimento(); // areia
        this.tracoUnitarioEmMassa[2] = this.brita.getConsumoDeBrita()/this.cimento.getConsumoDeCimento(); // brita
        this.tracoUnitarioEmMassa[3] = this.agua.getConsumoDeAgua()/this.cimento.getConsumoDeCimento();   // água

    }

    public void determinarTracoPara1Saco50KGDeCimentoEmMassa() {
        this.traco.tracoPara1Saco50KgDeCimentoEmMassa[0] = 1.0;
        this.traco.tracoPara1Saco50KgDeCimentoEmMassa[1] = this.tracoUnitarioEmMassa[1]*50;
        this.traco.tracoPara1Saco50KgDeCimentoEmMassa[2] = this.tracoUnitarioEmMassa[2]*50;
        this.traco.tracoPara1Saco50KgDeCimentoEmMassa[3] = this.tracoUnitarioEmMassa[3]*50;
    }

    public void determinarTracoPara1Saco50KGDeCimentoEmVolume() {
        this.traco.tracoPara1Saco50KgDeCimentoEmVolume[0] = 1.0;
        this.traco.tracoPara1Saco50KgDeCimentoEmVolume[1] = this.traco.tracoPara1Saco50KgDeCimentoEmMassa[1]/(this.areia.getMassaUnitaria()/1000)*(1 + areia.getInchamentoDaAreia()/100);
        this.traco.tracoPara1Saco50KgDeCimentoEmVolume[2] = this.traco.tracoPara1Saco50KgDeCimentoEmMassa[2]/(this.brita.getMassaUnitaria()/1000);
        this.traco.tracoPara1Saco50KgDeCimentoEmVolume[3] = this.traco.tracoPara1Saco50KgDeCimentoEmMassa[3] - (this.traco.tracoPara1Saco50KgDeCimentoEmMassa[1]*(areia.getUmidadeDaAreia()/100));
    }

    public void determinarTracoPara1Saco50KGDeCimentoEmPadiolas() {
        this.traco.tracoPara1Saco50KgDeCimentoEmPadiolas[0] = 1.0;

        this.areia.setConsumoDeAreiaUmida(this.traco.tracoPara1Saco50KgDeCimentoEmMassa[1]*(1+this.areia.getUmidadeDaAreia()/100));
        if (this.areia.getConsumoDeAreiaUmida()/60 <= 1) {
            quantidadeDePadiolasDeAreia = 1.0;
        } else {
            quantidadeDePadiolasDeAreia = (double) ((int) (this.areia.getConsumoDeAreiaUmida() / 60) + 1);
        }
        volumeDaPadiolaDeAreia = this.traco.getTracoPara1Saco50KgDeCimentoEmVolume()[1] / quantidadeDePadiolasDeAreia;
        alturaDaPadiolaDeAreia = (volumeDaPadiolaDeAreia*1000)/(larguraDaPadiola*comprimentoDaPadiola);
        this.traco.tracoPara1Saco50KgDeCimentoEmPadiolas[1] = quantidadeDePadiolasDeAreia;

        if (this.traco.tracoPara1Saco50KgDeCimentoEmMassa[2]/60 <= 1) {
            quantidadeDePadiolasDeBrita = 1.0;
        } else {
            quantidadeDePadiolasDeBrita = (double) ((int)(this.traco.tracoPara1Saco50KgDeCimentoEmMassa[2] / 60) + 1);
        }
        volumeDaPadiolaDeBrita = this.traco.getTracoPara1Saco50KgDeCimentoEmVolume()[2] / quantidadeDePadiolasDeBrita;
        alturaDaPadiolaDeBrita = (volumeDaPadiolaDeBrita*1000)/(larguraDaPadiola*comprimentoDaPadiola);
        this.traco.tracoPara1Saco50KgDeCimentoEmPadiolas[2] = quantidadeDePadiolasDeBrita;

        this.traco.tracoPara1Saco50KgDeCimentoEmPadiolas[3] = this.traco.tracoPara1Saco50KgDeCimentoEmVolume[3];
    }

    public void recalcularTraco(String unidadeCimento,String unidadeAreia, String unidadeBrita, String unidadeAgua) {

    }

    public void nomearTraco(String nomeDoTraco) {
        this.traco.setNomeDoTraco(nomeDoTraco);
    }

    //Getters e Setters

    public Concreto getConcreto() {
        return concreto;
    }

    public void setConcreto(Concreto concreto) {
        this.concreto = concreto;
    }

    public Cimento getCimento() {
        return cimento;
    }

    public void setCimento(Cimento cimento) {
        this.cimento = cimento;
    }

    public Areia getAreia() {
        return areia;
    }

    public void setAreia(Areia areia) {
        this.areia = areia;
    }

    public Brita getBrita() {
        return brita;
    }

    public void setBrita(Brita brita) {
        this.brita = brita;
    }

    public Agua getAgua() {
        return agua;
    }

    public void setAgua(Agua agua) {
        this.agua = agua;
    }

    public Double getLarguraDaPadiola() {
        return larguraDaPadiola;
    }

    public void setLarguraDaPadiola(Double larguraDaPadiola) {
        this.larguraDaPadiola = larguraDaPadiola;
    }

    public Double getComprimentoDaPadiola() {
        return comprimentoDaPadiola;
    }

    public void setComprimentoDaPadiola(Double comprimentoDaPadiola) {
        this.comprimentoDaPadiola = comprimentoDaPadiola;
    }

    public Double getAlturaDaPadiolaDeAreia() {
        return alturaDaPadiolaDeAreia;
    }

    public void setAlturaDaPadiolaDeAreia(Double alturaDaPadiolaDeAreia) {
        this.alturaDaPadiolaDeAreia = alturaDaPadiolaDeAreia;
    }

    public Double getAlturaDaPadiolaDeBrita() {
        return alturaDaPadiolaDeBrita;
    }

    public void setAlturaDaPadiolaDeBrita(Double alturaDaPadiolaDeBrita) {
        this.alturaDaPadiolaDeBrita = alturaDaPadiolaDeBrita;
    }

    public Double getVolumeDaPadiolaDeAreia() {
        return volumeDaPadiolaDeAreia;
    }

    public void setVolumeDaPadiolaDeAreia(Double volumeDaPadiolaDeAreia) {
        this.volumeDaPadiolaDeAreia = volumeDaPadiolaDeAreia;
    }

    public Double getVolumeDaPadiolaDeBrita() {
        return volumeDaPadiolaDeBrita;
    }

    public void setVolumeDaPadiolaDeBrita(Double volumeDaPadiolaDeBrita) {
        this.volumeDaPadiolaDeBrita = volumeDaPadiolaDeBrita;
    }

    public CurvaDeAbrams getCurvaDeAbrams() {
        return curvaDeAbrams;
    }

    public void setCurvaDeAbrams(CurvaDeAbrams curvaDeAbrams) {
        this.curvaDeAbrams = curvaDeAbrams;
    }

    public TabelaConsumoDeAgua getTabelaCa() {
        return tabelaCa;
    }

    public void setTabelaCa(TabelaConsumoDeAgua tabelaCa) {
        this.tabelaCa = tabelaCa;
    }

    public TabelaVolumeDeBrita getTabelaVb() {
        return tabelaVb;
    }

    public void setTabelaVb(TabelaVolumeDeBrita tabelaVb) {
        this.tabelaVb = tabelaVb;
    }

    public Double[] getTracoUnitarioEmMassa() {
        return tracoUnitarioEmMassa;
    }

    public void setTracoUnitarioEmMassa(Double[] tracoEmMassa) {
        this.tracoUnitarioEmMassa = tracoEmMassa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

package com.cursoandroid.appdosagemconcreto.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cursoandroid.appdosagemconcreto.classesdecalculo.Dosagem;
import com.cursoandroid.appdosagemconcreto.classesdecalculo.Traco;
import com.cursoandroid.appdosagemconcreto.materiais.Agua;
import com.cursoandroid.appdosagemconcreto.materiais.Areia;
import com.cursoandroid.appdosagemconcreto.materiais.Brita;
import com.cursoandroid.appdosagemconcreto.materiais.Cimento;
import com.cursoandroid.appdosagemconcreto.materiais.Concreto;

import java.util.ArrayList;
import java.util.List;

public class TracoDAO implements ITracoDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TracoDAO(Context context) {

        DbHelper db = new DbHelper( context );
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Dosagem dosagem) {

        ContentValues cv = new ContentValues();
        cv.put("nomeDoTraco", dosagem.traco.getNomeDoTraco());
        cv.put("tracoExibido", dosagem.traco.getTracoExibido());
        cv.put("tipoTraco", dosagem.traco.getTipoDeTraco());
        cv.put("dataDoTraco", dosagem.traco.getDataDoTraco());

        cv.put("desvioPadrao", dosagem.concreto.getDesvioPadrao());
        cv.put("fck", dosagem.concreto.getFck());
        cv.put("abatimento", dosagem.concreto.getAbatimento());

        cv.put("tipoDeCimento", dosagem.cimento.getEspecificacoes());
        cv.put("massaEspecificaCimento", dosagem.cimento.getMassaEspecifica());

        cv.put("moduloDeFinuraAreia", dosagem.areia.getModuloDefinura());
        cv.put("massaEspecificaAreia", dosagem.areia.getMassaEspecifica());
        cv.put("massaUnitariaAreia", dosagem.areia.getMassaUnitaria());

        cv.put("diametroMaximoBrita", dosagem.brita.getDiametroMaximo());
        cv.put("massaEspecificaBrita", dosagem.brita.getMassaEspecifica());
        cv.put("massaUnitariaCompBrita", dosagem.brita.getMassaUnitariaComp());
        cv.put("massaUnitariaBrita", dosagem.brita.getMassaUnitaria());

        try {
            escreve.insert(DbHelper.TABELA_TRACOS, null, cv);
            Log.i("INFO", "Sucesso ao salvar traço");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao salvar traço" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Dosagem dosagem) {
        return false;
    }

    @Override
    public boolean deletar(Dosagem dosagem) {

        try {
            String[] args = { dosagem.getId().toString() };
            escreve.delete(DbHelper.TABELA_TRACOS, "id=?", args);
            Log.i("INFO", "Sucesso ao atualizar traço");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao atualizar traço" + e.getMessage());
        }

        return true;
    }

    @Override
    public List<Dosagem> listar() {
        List<Dosagem> listaDeTracos = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_TRACOS + ";";
        Cursor cursor = le.rawQuery(sql, null);

        while ( cursor.moveToNext() ) {

            Dosagem dosagem = new Dosagem();

            Cimento cimento = new Cimento();
            Areia areia = new Areia();
            Brita brita = new Brita();
            Agua agua = new Agua();
            Concreto concreto = new Concreto();

            Long id = cursor.getLong( cursor.getColumnIndex("id") );
            String nomeTraco = cursor.getString( cursor.getColumnIndex("nomeDoTraco") );
            String dataDoTraco = cursor.getString( cursor.getColumnIndex("dataDoTraco") );
            String tipoTraco = cursor.getString( cursor.getColumnIndex("tipoTraco") );
            String tracoExibido = cursor.getString( cursor.getColumnIndex("tracoExibido") );

            Double desvioPadrao = cursor.getDouble( cursor.getColumnIndex("desvioPadrao") );
            Double fck = cursor.getDouble( cursor.getColumnIndex("fck") );
            Double abatimento = cursor.getDouble( cursor.getColumnIndex("abatimento") );

            String tipoDeCimento = cursor.getString( cursor.getColumnIndex("tipoDeCimento") );
            Double massaEspecificaCimento = cursor.getDouble( cursor.getColumnIndex("massaEspecificaCimento") );

            Double moduloDeFinuraAreia = cursor.getDouble( cursor.getColumnIndex("moduloDeFinuraAreia") );
            Double massaEspecificaAreia = cursor.getDouble( cursor.getColumnIndex("massaEspecificaAreia") );
            Double massaUnitariaAreia = cursor.getDouble( cursor.getColumnIndex("massaUnitariaAreia") );

            Double diametroMaximoBrita = cursor.getDouble( cursor.getColumnIndex("diametroMaximoBrita") );
            Double massaEspecificaBrita = cursor.getDouble( cursor.getColumnIndex("massaEspecificaBrita") );
            Double massaUnitariaCompBrita = cursor.getDouble( cursor.getColumnIndex("massaUnitariaCompBrita") );
            Double massaUnitariaBrita = cursor.getDouble( cursor.getColumnIndex("massaUnitariaBrita") );

            dosagem.setId(id);
            dosagem.traco.setNomeDoTraco(nomeTraco);
            dosagem.traco.setDataDoTraco(dataDoTraco);
            dosagem.traco.setTipoDeTraco(tipoTraco);
            dosagem.traco.setTracoExibido(tracoExibido);

            concreto.setDesvioPadrao(desvioPadrao);
            concreto.setFck(fck);
            concreto.setAbatimento(abatimento);

            cimento.setEspecificacoes(tipoDeCimento);
            cimento.setMassaEspecifica(massaEspecificaCimento);

            areia.setModuloDefinura(moduloDeFinuraAreia);
            areia.setMassaEspecifica(massaEspecificaAreia);
            areia.setMassaUnitaria(massaUnitariaAreia);

            brita.setDiametroMaximo(diametroMaximoBrita);
            brita.setMassaEspecifica(massaEspecificaBrita);
            brita.setMassaUnitariaComp(massaUnitariaCompBrita);
            brita.setMassaUnitaria(massaUnitariaBrita);

            dosagem.inserirInformacoesIncicias(concreto, cimento, areia, brita, agua);

            listaDeTracos.add( dosagem );
        }

        return listaDeTracos;
    }
}

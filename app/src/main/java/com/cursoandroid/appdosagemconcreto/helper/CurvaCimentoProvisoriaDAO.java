package com.cursoandroid.appdosagemconcreto.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cursoandroid.appdosagemconcreto.model.ItemPontoCimento;

import java.util.List;

public class CurvaCimentoProvisoriaDAO implements ICurvaCimentoProvisoriaDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public CurvaCimentoProvisoriaDAO(Context context) {
        DbCimentoCurvaProvisoria dbCCP = new DbCimentoCurvaProvisoria( context );
        escreve = dbCCP.getWritableDatabase();
        le = dbCCP.getReadableDatabase();
    }

    @Override
    public boolean salvar(ItemPontoCimento itemPontoCimento) {

        ContentValues cv = new ContentValues();
        cv.put("ac", itemPontoCimento.getValorDeAC());
        cv.put("fck", itemPontoCimento.getValorDeFck());

        try {
            escreve.insert(DbCimentoCurvaProvisoria.TABELA_DADOS_CIMENTO, null, cv);
            Log.i("INFO", "Sucesso ao salvar ponto do cimento.");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao salvar ponto do cimento.");
            return false;
        }


        return true;
    }

    @Override
    public boolean atualizar(ItemPontoCimento itemPontoCimento) {
        return false;
    }

    @Override
    public boolean deletar(ItemPontoCimento itemPontoCimento) {
        return false;
    }

    @Override
    public List<ItemPontoCimento> listar() {

        return null;
    }
}

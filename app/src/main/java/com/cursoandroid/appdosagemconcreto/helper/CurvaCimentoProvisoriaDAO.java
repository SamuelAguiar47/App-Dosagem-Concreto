package com.cursoandroid.appdosagemconcreto.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cursoandroid.appdosagemconcreto.model.ItemPontoCimento;

import java.util.ArrayList;
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
        List<ItemPontoCimento> listaPontosCimento = new ArrayList<>();

        String sql = "SELECT * FROM " + DbCimentoCurvaProvisoria.TABELA_DADOS_CIMENTO + " ;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ) {

            ItemPontoCimento itemPontoCimento = new ItemPontoCimento();

            Long id = c.getLong( c.getColumnIndex("id") );
            Double valorDeAC = c.getDouble( c.getColumnIndex("ac") );
            Double valorDeFck = c.getDouble( c.getColumnIndex("fck") );

            itemPontoCimento.setId(id);
            itemPontoCimento.setValorDeAC(valorDeAC);
            itemPontoCimento.setValorDeFck(valorDeFck);
            itemPontoCimento.setNomeDoPonto("a/c: " + valorDeAC + " ; Fck: " + valorDeFck);

            listaPontosCimento.add(itemPontoCimento);

        }

        return listaPontosCimento;
    }
}

package com.cursoandroid.appdosagemconcreto.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cursoandroid.appdosagemconcreto.model.ItemCimentoSalvo;

import java.util.ArrayList;
import java.util.List;

public class CimentosSalvosDAO implements ICimentosSalvosDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public CimentosSalvosDAO(Context context) {
        DbCimentosSalvos dbCimentosSalvos = new DbCimentosSalvos( context );
        escreve = dbCimentosSalvos.getWritableDatabase();
        le = dbCimentosSalvos.getReadableDatabase();
    }

    @Override
    public boolean salvar(ItemCimentoSalvo itemCimentoSalvo) {

        ContentValues cv = new ContentValues();
        cv.put("nomeDoCimento", itemCimentoSalvo.getNomeDoCimento());
        cv.put("tempoDeCura", itemCimentoSalvo.getTempoDeCura());
        cv.put("data", itemCimentoSalvo.getData());
        cv.put("qtdeDePontos", itemCimentoSalvo.getQtdeDePontos());
        cv.put("K1", itemCimentoSalvo.getK1());
        cv.put("K2", itemCimentoSalvo.getK2());

        try {
            escreve.insert(DbCimentosSalvos.TABELA_CIMENTOS_SALVOS, null, cv);
            Log.i("INFO", "Sucesso ao salvar cimento.");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao salvar cimento.");
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(ItemCimentoSalvo itemCimentoSalvo) {
        return false;
    }

    @Override
    public boolean deletar(ItemCimentoSalvo itemCimentoSalvo) {

        try {
            String[] args = {itemCimentoSalvo.getId().toString()};
            escreve.delete(DbCimentosSalvos.TABELA_CIMENTOS_SALVOS, "id=?", args);
            Log.i("INFO","Sucesso ao deletar cimento.");
        } catch (Exception e) {
            Log.i("INFO","Erro ao deletar cimento.");
        }

        return true;
    }

    @Override
    public List<ItemCimentoSalvo> listar() {

        List<ItemCimentoSalvo> listaCimentosSalvos = new ArrayList<>();

        String sql = "SELECT * FROM " + DbCimentosSalvos.TABELA_CIMENTOS_SALVOS + " ;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ) {

            ItemCimentoSalvo itemCimentoSalvo = new ItemCimentoSalvo();

            Long id = c.getLong( c.getColumnIndex("id") );
            String nomeDoCimento = c.getString( c.getColumnIndex("nomeDoCimento") );
            String tempoDeCura = c.getString( c.getColumnIndex("tempoDeCura") );
            String data = c.getString( c.getColumnIndex("data") );
            int qtdeDePontos = c.getInt( c.getColumnIndex("qtdeDePontos") );

            itemCimentoSalvo.setId(id);
            itemCimentoSalvo.setNomeDoCimento(nomeDoCimento);
            itemCimentoSalvo.setTempoDeCura(tempoDeCura);
            itemCimentoSalvo.setData(data);
            itemCimentoSalvo.setQtdeDePontos(qtdeDePontos);

            listaCimentosSalvos.add(itemCimentoSalvo);
        }

        return listaCimentosSalvos;
    }
}

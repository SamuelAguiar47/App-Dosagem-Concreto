package com.cursoandroid.appdosagemconcreto.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbCimentoCurva extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_CIMENTO_CURVA";
    public String TABELA_DADOS_CIMENTO;

    public DbCimentoCurva(@Nullable Context context, String TABELA_DADOS_CIMENTO) {
        super(context, NOME_DB, null, VERSION);
        this.TABELA_DADOS_CIMENTO = TABELA_DADOS_CIMENTO;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_DADOS_CIMENTO
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ac REAL, fcj REAL); ";

        try {
            sqLiteDatabase.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar tabela de dados do cimento" + TABELA_DADOS_CIMENTO.replace("tabela","") + "definitiva! ");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela de dados do cimento" + TABELA_DADOS_CIMENTO.replace("tabela", "") + "definitiva! " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

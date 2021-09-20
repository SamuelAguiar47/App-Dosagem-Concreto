package com.cursoandroid.appdosagemconcreto.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbCimentoCurva extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_CIMENTO_CURVA";
    public static String TABELA_DADOS_CIMENTO = "TABELA_DADOS_CIMENTO";

    public DbCimentoCurva(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql1 = "DROP TABLE IF EXISTS " + TABELA_DADOS_CIMENTO + " ;";

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_DADOS_CIMENTO
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ac REAL, fcj REAL, nomeCimento String); ";

        try {
            sqLiteDatabase.execSQL(sql1);
            sqLiteDatabase.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar tabela de dados do cimento " + TABELA_DADOS_CIMENTO.replace("tabela","") + " definitiva! ");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela de dados do cimento " + TABELA_DADOS_CIMENTO.replace("tabela", "") + " definitiva! " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql1 = "DROP TABLE IF EXISTS " + TABELA_DADOS_CIMENTO + " ;";

        String sql2 = "CREATE TABLE IF NOT EXISTS " + TABELA_DADOS_CIMENTO
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ac REAL, fcj REAL, nomeCimento String); ";

        try {
            sqLiteDatabase.execSQL(sql1);
            sqLiteDatabase.execSQL(sql2);
            Log.i("INFO DB", "Sucesso ao criar tabela de dados do cimento " + TABELA_DADOS_CIMENTO.replace("tabela","") + " definitiva! ");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela de dados do cimento " + TABELA_DADOS_CIMENTO.replace("tabela", "") + " definitiva! " + e.getMessage());
        }

    }
}

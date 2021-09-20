package com.cursoandroid.appdosagemconcreto.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbCimentosSalvos extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_CIMENTOS_SALVOS";
    public static String TABELA_CIMENTOS_SALVOS = "tabelaCimentosSalvos";

    public DbCimentosSalvos(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_CIMENTOS_SALVOS
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nomeDoCimento TEXT, tempoDeCura TEXT, data TEXT," +
                " qtdeDePontos INTEGER, observacoes TEXT, K1 REAL, K2 REAL); ";

        try {
            sqLiteDatabase.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar tabela de cimentos salvos! ");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela de cimentos salvos! " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql1 = "DROP TABLE IF EXISTS " + TABELA_CIMENTOS_SALVOS + " ;";

        try {
            sqLiteDatabase.execSQL(sql1);
            Log.i("INFO DB", "Sucesso ao criar tabela de cimentos salvos! ");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela de cimentos salvos! " + e.getMessage());
        }

        onCreate(sqLiteDatabase);

    }
}

package com.cursoandroid.appdosagemconcreto.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbCimentoCurvaProvisoriaEditar extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_CIMENTO_CURVA_PROVISORIA_EDITAR";
    public static String TABELA_DADOS_CIMENTO = "tabelaDadosCimento";

    public DbCimentoCurvaProvisoriaEditar(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_DADOS_CIMENTO
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ac REAL, fcj REAL); ";

        try {
            sqLiteDatabase.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar tabela de dados do cimento provisória! ");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela de dados do cimento provisória! " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

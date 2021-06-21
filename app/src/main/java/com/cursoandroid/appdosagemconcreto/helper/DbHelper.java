package com.cursoandroid.appdosagemconcreto.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_TRACOS";
    public static String TABELA_TRACOS = "tracos";

    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_TRACOS
                     + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     " nomeDoTraco TEXT NOT NULL, dataDoTraco TEXT," +
                     " tipoTraco TEXT, tracoExibido TEXT, " +

                     // Dados concreto
                     "desvioPadrao REAL, fck REAL, abatimento REAL, " +

                     // Dados cimento
                     "tipoDeCimento TEXT, massaEspecificaCimento REAL, " +

                     // Areia
                     "moduloDeFinuraAreia REAL, massaEspecificaAreia REAL, massaUnitariaAreia REAL," +

                     // Brita
                     "diametroMaximoBrita REAL, massaEspecificaBrita REAL, massaUnitariaCompBrita REAL, massaUnitariaBrita REAL); ";

        try {
            sqLiteDatabase.execSQL( sql );
            Log.i("INFODB", "Sucesso ao criar tabela");
        }catch (Exception e){
            Log.i("INFODB", "Erro ao criar tabela " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try {
            Log.i("INFO DB", "Sucesso ao atualizar app");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao atualizar app " + e.getMessage());
        }
    }
}

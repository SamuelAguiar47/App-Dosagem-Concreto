package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.helper.CodigosDeActivity;
import com.cursoandroid.appdosagemconcreto.helper.DbHelper;

public class MainActivity extends AppCompatActivity {

    private Button buttonNovoTraco, buttonTracosSalvos, buttonAjuda, buttonSobreOApp;

    // Helper
    CodigosDeActivity codigosDeActivity = new CodigosDeActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNovoTraco = findViewById(R.id.buttonNovoTraco);
        buttonTracosSalvos = findViewById(R.id.buttonTracosSalvos);
        buttonAjuda = findViewById(R.id.buttonAjuda);
        buttonSobreOApp = findViewById(R.id.buttonSobreOApp);

        buttonNovoTraco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAbrirInserirDadosActivity = new Intent(MainActivity.this, InserirDadosActivity.class);
                intentAbrirInserirDadosActivity.putExtra("acao", "calcularNovoTraco");
                startActivity(intentAbrirInserirDadosActivity);
            }
        });

        buttonTracosSalvos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAbrirTracosSalvosActivity = new Intent(getApplicationContext(), TracosSalvosActivity.class);
                startActivity(intentAbrirTracosSalvosActivity);
            }
        });

    }
}

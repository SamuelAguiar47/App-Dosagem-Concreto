package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cursoandroid.appdosagemconcreto.R;

public class CimentosSalvosActivity extends AppCompatActivity {

    Button buttonAdicionarCimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cimentos_salvos);

        buttonAdicionarCimento = findViewById(R.id.buttonAdicionarCimento);

        buttonAdicionarCimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAbrirAdicionarEditarCimentoActivity = new Intent(getApplicationContext(), AdicionarEditarCimentoActivity.class);
                startActivity(intentAbrirAdicionarEditarCimentoActivity);
            }
        });

    }
}

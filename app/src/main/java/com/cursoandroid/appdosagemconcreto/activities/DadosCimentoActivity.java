package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cursoandroid.appdosagemconcreto.R;

public class DadosCimentoActivity extends AppCompatActivity {

    RecyclerView recyClerViewDadosCimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_cimento);

        recyClerViewDadosCimento = findViewById(R.id.recyclerViewDadosCimento);

    }
}

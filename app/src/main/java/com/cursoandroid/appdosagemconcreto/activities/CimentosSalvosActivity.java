package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.model.ItemTipoDeCimento;

import java.util.ArrayList;
import java.util.List;

public class CimentosSalvosActivity extends AppCompatActivity {

    private Button buttonAdicionarCimento;
    private RecyclerView recyclerViewCimentosSalvos;
    private ItemTipoDeCimento itemTipoDeCimento = new ItemTipoDeCimento();
    private List<ItemTipoDeCimento> listaDeCimentos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cimentos_salvos);

        // Carregar elementos de interface
        buttonAdicionarCimento = findViewById(R.id.buttonAdicionarCimento);
        recyclerViewCimentosSalvos = findViewById(R.id.recyclerViewCimentosSalvos);

        buttonAdicionarCimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAbrirAdicionarEditarCimentoActivity = new Intent(getApplicationContext(), AdicionarEditarCimentoActivity.class);
                startActivity(intentAbrirAdicionarEditarCimentoActivity);
            }
        });

    }

    public void carregarListaDeCimentos() {

        //Listar cimentos
        ItemTipoDeCimento cimento1 = new ItemTipoDeCimento();
        cimento1.setNomeDoCimento("Cimento 1");
        cimento1.setTempoDeCura("28");
        cimento1.setQtdeDePontos(36);
        listaDeCimentos.add(cimento1);

        /*
            Exibe pontos do cimento no RecyclerView
         */

        //Configurar um adapter

        //Configurar um RecyclerView

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaDeCimentos();
    }
}

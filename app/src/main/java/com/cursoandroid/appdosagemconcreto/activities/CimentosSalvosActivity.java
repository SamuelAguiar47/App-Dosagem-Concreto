package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.adapter.CimentoAdapter;
import com.cursoandroid.appdosagemconcreto.model.ItemCimentoSalvo;

import java.util.ArrayList;
import java.util.List;

public class CimentosSalvosActivity extends AppCompatActivity {

    private Button buttonAdicionarCimento;
    private RecyclerView recyclerViewCimentosSalvos;
    private CimentoAdapter cimentoAdapter;
    private ItemCimentoSalvo itemTipoDeCimento = new ItemCimentoSalvo();
    private List<ItemCimentoSalvo> listaDeCimentos = new ArrayList<>();

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
        ItemCimentoSalvo cimento1 = new ItemCimentoSalvo();
        cimento1.setNomeDoCimento("Cimento 1");
        cimento1.setTempoDeCura("28");
        cimento1.setData("13/08/2021");
        cimento1.setQtdeDePontos(36);
        listaDeCimentos.add(cimento1);

        ItemCimentoSalvo cimento2 = new ItemCimentoSalvo();
        cimento2.setNomeDoCimento("Cimento 2");
        cimento2.setTempoDeCura("28");
        cimento2.setData("13/08/2021");
        cimento2.setQtdeDePontos(237);
        listaDeCimentos.add(cimento2);

        /*
            Exibe pontos do cimento no RecyclerView
         */

        //Configurar um adapter
        cimentoAdapter = new CimentoAdapter((ArrayList<ItemCimentoSalvo>) listaDeCimentos);

        //Configurar um RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCimentosSalvos.setLayoutManager(layoutManager);
        recyclerViewCimentosSalvos.setHasFixedSize(true);
        recyclerViewCimentosSalvos.setAdapter(cimentoAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaDeCimentos();
    }
}

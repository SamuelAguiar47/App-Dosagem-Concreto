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
import com.cursoandroid.appdosagemconcreto.helper.CimentosSalvosDAO;
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
        CimentosSalvosDAO cimentosSalvosDAO = new CimentosSalvosDAO(getApplicationContext());
        listaDeCimentos = cimentosSalvosDAO.listar();

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

package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.adapter.PontosDoCimentoAdapter;
import com.cursoandroid.appdosagemconcreto.helper.CurvaCimentoProvisoriaDAO;
import com.cursoandroid.appdosagemconcreto.model.ItemPontoCimento;

import java.util.ArrayList;
import java.util.List;

public class DadosCimentoActivity extends AppCompatActivity {

    private TextView textViewRotuloDoCimento, textViewDadosNomeDoCimento, textViewDadosTempoDeCura;
    private Button buttonExibirPontos;
    private RecyclerView recyClerViewDadosCimento;
    private PontosDoCimentoAdapter pontosDoCimentoAdapter;
    private List<ItemPontoCimento> listaPontosCimento = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_cimento);

        Bundle dados = getIntent().getExtras();

        //Configurar elementos de interface
        textViewRotuloDoCimento = findViewById(R.id.textViewRotuloDoCimento);
        textViewDadosNomeDoCimento = findViewById(R.id.textViewDadosNomeDoCimento);
        textViewDadosTempoDeCura = findViewById(R.id.textViewDadosTempoDeCura);
        buttonExibirPontos = findViewById(R.id.buttonExibirPontos);
        recyClerViewDadosCimento = findViewById(R.id.recyclerViewDadosCimento);

        textViewRotuloDoCimento.setText("Cimento " + dados.getString("nome do cimento"));
        textViewDadosNomeDoCimento.setText("Nome do cimento: "  + dados.getString("nome do cimento"));
        textViewDadosTempoDeCura.setText("Tempo de cura: "  + dados.getString("tempo de cura") + " dias");

        buttonExibirPontos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyClerViewDadosCimento.getVisibility() == View.GONE) {
                    recyClerViewDadosCimento.setVisibility(View.VISIBLE);
                    buttonExibirPontos.setText("Ocultar pontos de amostras do cimento");
                } else {
                    recyClerViewDadosCimento.setVisibility(View.GONE);
                    buttonExibirPontos.setText("Exibir pontos de amostras do cimento");
                }
            }
        });




    }

    public void carregarListaDePontosDoCimentos() {

        //Listar Tarefas
        CurvaCimentoProvisoriaDAO curvaCimentoProvisoriaDAO = new CurvaCimentoProvisoriaDAO(getApplicationContext());
        listaPontosCimento = curvaCimentoProvisoriaDAO.listar();

        /*
            Exibe pontos do cimento no RecyclerView
         */

        //Configurar um adapter
        pontosDoCimentoAdapter = new PontosDoCimentoAdapter((ArrayList<ItemPontoCimento>) listaPontosCimento);

        //Configurar um RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyClerViewDadosCimento.setLayoutManager( layoutManager );
        recyClerViewDadosCimento.setHasFixedSize(true);
        //recyclerViewAdicionarEditarCimentos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyClerViewDadosCimento.setAdapter(pontosDoCimentoAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaDePontosDoCimentos();
    }
}

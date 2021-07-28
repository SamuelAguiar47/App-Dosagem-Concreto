package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.adapter.PontosDoCimentoAdapter;
import com.cursoandroid.appdosagemconcreto.model.ItemPontoCimento;

import java.util.ArrayList;
import java.util.List;

public class AdicionarEditarCimentoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAdicionarEditarCimentos;
    private PontosDoCimentoAdapter pontosDoCimentoAdapter;
    private List<ItemPontoCimento> listaPontosCimento = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_editar_cimento);

        recyclerViewAdicionarEditarCimentos = findViewById(R.id.RecyclerViewAdicionarEditarCimentos);

    }

    public void carregarListaDeCimentos() {

        //Listar Tarefas
        ItemPontoCimento ponto1 = new ItemPontoCimento();
        ponto1.setNomeDoPonto("a/c = 0,40 , Fck = 31.8 MPa");
        listaPontosCimento.add( ponto1 );

        ItemPontoCimento ponto2 = new ItemPontoCimento();
        ponto1.setNomeDoPonto("a/c = 0,45 , Fck = 31.8 MPa");
        listaPontosCimento.add( ponto1 );

        ItemPontoCimento ponto3 = new ItemPontoCimento();
        ponto1.setNomeDoPonto("a/c = 0,50 , Fck = 31.8 MPa");
        listaPontosCimento.add( ponto1 );

        ItemPontoCimento ponto4 = new ItemPontoCimento();
        ponto1.setNomeDoPonto("a/c = 0,55 , Fck = 31.8 MPa");
        listaPontosCimento.add( ponto1 );

        ItemPontoCimento ponto5 = new ItemPontoCimento();
        ponto1.setNomeDoPonto("a/c = 0,60 , Fck = 31.8 MPa");
        listaPontosCimento.add( ponto1 );


        /*
            Exibe tarefas no RecyclerView
         */

        //Configurar um adapter
        pontosDoCimentoAdapter = new PontosDoCimentoAdapter((ArrayList<ItemPontoCimento>) listaPontosCimento);

        //Configurar um RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAdicionarEditarCimentos.setLayoutManager( layoutManager );
        recyclerViewAdicionarEditarCimentos.setHasFixedSize(true);
        recyclerViewAdicionarEditarCimentos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerViewAdicionarEditarCimentos.setAdapter(pontosDoCimentoAdapter);

    }
}

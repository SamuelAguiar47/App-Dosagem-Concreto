package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.adapter.PontosDoCimentoAdapter;
import com.cursoandroid.appdosagemconcreto.helper.RecyclerItemClickListener;
import com.cursoandroid.appdosagemconcreto.model.ItemPontoCimento;

import java.util.ArrayList;
import java.util.List;

public class AdicionarEditarCimentoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAdicionarEditarCimentos;
    private PontosDoCimentoAdapter pontosDoCimentoAdapter;
    private List<ItemPontoCimento> listaPontosCimento = new ArrayList<>();
    private Button buttonAdicionarDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_editar_cimento);

        buttonAdicionarDados = findViewById(R.id.buttonAdicionarDados);
        recyclerViewAdicionarEditarCimentos = findViewById(R.id.RecyclerViewAdicionarEditarCimentos);

        buttonAdicionarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRestart();
            }
        });

        //Adicionar evento de clique
        recyclerViewAdicionarEditarCimentos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewAdicionarEditarCimentos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(AdicionarEditarCimentoActivity.this, "clique curto", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Toast.makeText(AdicionarEditarCimentoActivity.this, "clique longo", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

    }

    public void carregarListaDeCimentos() {

        //Listar Tarefas
        ItemPontoCimento ponto1 = new ItemPontoCimento();
        ponto1.setNomeDoPonto("a/c = 0,40 , Fck = 31.8 MPa");
        listaPontosCimento.add( ponto1 );

        ItemPontoCimento ponto2 = new ItemPontoCimento();
        ponto2.setNomeDoPonto("a/c = 0,45 , Fck = 31.8 MPa");
        listaPontosCimento.add( ponto2 );

        ItemPontoCimento ponto3 = new ItemPontoCimento();
        ponto3.setNomeDoPonto("a/c = 0,50 , Fck = 31.8 MPa");
        listaPontosCimento.add( ponto3 );

        ItemPontoCimento ponto4 = new ItemPontoCimento();
        ponto4.setNomeDoPonto("a/c = 0,55 , Fck = 31.8 MPa");
        listaPontosCimento.add( ponto4 );

        ItemPontoCimento ponto5 = new ItemPontoCimento();
        ponto5.setNomeDoPonto("a/c = 0,60 , Fck = 31.8 MPa");
        listaPontosCimento.add( ponto5 );


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

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaDeCimentos();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}

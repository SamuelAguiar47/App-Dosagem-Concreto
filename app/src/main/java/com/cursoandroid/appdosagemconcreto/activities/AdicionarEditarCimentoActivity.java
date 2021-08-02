package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.adapter.PontosDoCimentoAdapter;
import com.cursoandroid.appdosagemconcreto.helper.CurvaCimentoProvisoriaDAO;
import com.cursoandroid.appdosagemconcreto.helper.DbCimentoCurvaProvisoria;
import com.cursoandroid.appdosagemconcreto.helper.RecyclerItemClickListener;
import com.cursoandroid.appdosagemconcreto.model.ItemPontoCimento;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AdicionarEditarCimentoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAdicionarEditarCimentos;
    private PontosDoCimentoAdapter pontosDoCimentoAdapter;
    private List<ItemPontoCimento> listaPontosCimento = new ArrayList<>();
    private ItemPontoCimento itemPontoCimento;
    private TextInputEditText textInputValorDeAC, textInputValorDeFck;
    private Button buttonAdicionarDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_editar_cimento);

        textInputValorDeAC = findViewById(R.id.textInputValorDeAC);
        textInputValorDeFck = findViewById(R.id.textInputValorDeFck);

        buttonAdicionarDados = findViewById(R.id.buttonAdicionarDados);

        buttonAdicionarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemPontoCimento.setValorDeAC(Double.parseDouble(textInputValorDeAC.getText().toString()));
                itemPontoCimento.setValorDeFck(Double.parseDouble(textInputValorDeFck.getText().toString()));
                CurvaCimentoProvisoriaDAO curvaCimentoProvisoriaDAO = new CurvaCimentoProvisoriaDAO(getApplicationContext());
                curvaCimentoProvisoriaDAO.salvar(itemPontoCimento);
                onRestart();
            }
        });

        //Configurar RecyclerView
        recyclerViewAdicionarEditarCimentos = findViewById(R.id.RecyclerViewAdicionarEditarCimentos);

        DbCimentoCurvaProvisoria dbCimentoCurvaProvisoria = new DbCimentoCurvaProvisoria(getApplicationContext());

        ContentValues cv = new ContentValues();
        cv.put("ac", 0.4);
        cv.put("fck", 31.8);
        dbCimentoCurvaProvisoria.getWritableDatabase().insert("tabelaDadosCimento", null, cv);

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
        CurvaCimentoProvisoriaDAO curvaCimentoProvisoriaDAO = new CurvaCimentoProvisoriaDAO(getApplicationContext());
        listaPontosCimento = curvaCimentoProvisoriaDAO.listar();


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

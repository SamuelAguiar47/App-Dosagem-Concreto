package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.adapter.CimentoAdapter;
import com.cursoandroid.appdosagemconcreto.helper.CimentosSalvosDAO;
import com.cursoandroid.appdosagemconcreto.helper.CurvaCimentoDAO;
import com.cursoandroid.appdosagemconcreto.helper.RecyclerItemClickListener;
import com.cursoandroid.appdosagemconcreto.model.ItemCimentoSalvo;

import java.util.ArrayList;
import java.util.List;

public class CimentosSalvosActivity extends AppCompatActivity {

    private Button buttonAdicionarCimento;
    private RecyclerView recyclerViewCimentosSalvos;
    private CimentoAdapter cimentoAdapter;
    private List<ItemCimentoSalvo> listaDeCimentos = new ArrayList<>();
    private ItemCimentoSalvo itemCimentoSelecionado = new ItemCimentoSalvo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cimentos_salvos);

        // Carregar elementos de interface
        buttonAdicionarCimento = findViewById(R.id.buttonAdicionarCimento);
        recyclerViewCimentosSalvos = findViewById(R.id.recyclerViewCimentosSalvos);

        recyclerViewCimentosSalvos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewCimentosSalvos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                itemCimentoSelecionado = listaDeCimentos.get(position);

                                Intent intentAbrirDadosCimentoActivity = new Intent(getApplicationContext(), DadosCimentoActivity.class);
                                intentAbrirDadosCimentoActivity.putExtra("nome do cimento", itemCimentoSelecionado.getNomeDoCimento());
                                intentAbrirDadosCimentoActivity.putExtra("tempo de cura", itemCimentoSelecionado.getTempoDeCura());
                                intentAbrirDadosCimentoActivity.putExtra("observações", itemCimentoSelecionado.getObservacoes());
                                intentAbrirDadosCimentoActivity.putExtra("ação", "abrir cimento salvo");

                                startActivity(intentAbrirDadosCimentoActivity);
                                /*CurvaCimentoDAO curvaCimentoDAO = new CurvaCimentoDAO(getApplicationContext(), itemCimentoSelecionado.getNomeDoCimento(), itemCimentoSelecionado.getTempoDeCura());
                                Toast.makeText(CimentosSalvosActivity.this, curvaCimentoDAO.nomeDaTabela, Toast.LENGTH_SHORT).show();*/
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                //Recuperar cimento para deletar
                                itemCimentoSelecionado = listaDeCimentos.get(position);

                                //Configurar Alert Dialog
                                AlertDialog.Builder dialog = new AlertDialog.Builder(CimentosSalvosActivity.this);

                                dialog.setTitle("Deletar Cimento");
                                dialog.setTitle("Deseja realmente deletar o cimento " + itemCimentoSelecionado.getNomeDoCimento() + "?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        CimentosSalvosDAO cimentosSalvosDAO = new CimentosSalvosDAO(getApplicationContext());
                                        if (cimentosSalvosDAO.deletar(itemCimentoSelecionado)) {
                                            CurvaCimentoDAO curvaCimentoDAO = new CurvaCimentoDAO(getApplicationContext(), itemCimentoSelecionado.getNomeDoCimento(), itemCimentoSelecionado.getTempoDeCura());
                                            curvaCimentoDAO.deletarTabela(curvaCimentoDAO.nomeDaTabela);
                                            carregarListaDeCimentos();
                                            Toast.makeText(CimentosSalvosActivity.this, "Sucesso ao deletar cimento.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(CimentosSalvosActivity.this, "Erro ao deletar cimento.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                //Exibir Dialog
                                dialog.create();
                                dialog.show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

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

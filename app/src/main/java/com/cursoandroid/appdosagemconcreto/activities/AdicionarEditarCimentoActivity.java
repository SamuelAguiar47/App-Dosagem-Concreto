package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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
    private ItemPontoCimento itemPontoCimento = new ItemPontoCimento();
    private ItemPontoCimento pontoCimentoSelecionado = new ItemPontoCimento();
    private TextInputEditText textInputNomeDoCimento, textInputTempoDeCura, textInputobservacoes, textInputValorDeAC, textInputValorDeFcj;
    private Button buttonAdicionarDados, buttonConfirmar;
    private CurvaCimentoProvisoriaDAO curvaCimentoProvisoriaDAO;

    //Dados do cimento inseridos
    private String nomeDoCimento, tempoDeCura, observacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_editar_cimento);

        //Configuar elementos de interface
        textInputNomeDoCimento = findViewById(R.id.textInputNomeDoCimento);
        textInputTempoDeCura = findViewById(R.id.textInputTempoDeCura);
        textInputobservacoes = findViewById(R.id.textInputObservacoes);
        textInputValorDeAC = findViewById(R.id.textInputValorDeAC);
        textInputValorDeFcj = findViewById(R.id.textInputValorDeFcj);

        buttonAdicionarDados = findViewById(R.id.buttonAdicionarDados);
        buttonConfirmar = findViewById(R.id.buttonConfirmar);

        //Pré-configuração de teste
        textInputNomeDoCimento.setText("CP32 teste");
        textInputTempoDeCura.setText("28");
        textInputobservacoes.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                                    "Aliquam venenatis velit id urna vulputate euismod. Aenean " +
                                    "arcu elit, rhoncus id sem in, hendrerit vulputate dolor. " +
                                    "Proin odio tellus, consectetur in nulla a, ultricies lobortis odio.");

        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textInputTempoDeCura.getText().toString().equals("")) {
                    Toast.makeText(AdicionarEditarCimentoActivity.this, "Informe o tempo de curar para continuar.", Toast.LENGTH_SHORT).show();
                } else if (curvaCimentoProvisoriaDAO.listar().size() < 5) {
                    Toast.makeText(AdicionarEditarCimentoActivity.this, "Devem ser inseridos pelo menos 5 pontos.", Toast.LENGTH_SHORT).show();
                }else {
                    if (textInputNomeDoCimento.getText().toString().equals("")) {
                        nomeDoCimento = "sem nome";
                    } else {
                        nomeDoCimento = textInputNomeDoCimento.getText().toString();
                    }
                    if(textInputobservacoes.getText().toString().equals("")) {
                        observacoes = "nenhuma";
                    } else {
                        observacoes = textInputobservacoes.getText().toString();
                    }

                    tempoDeCura = textInputTempoDeCura.getText().toString();

                    Intent intentAbrirDadosCimentoActivity = new Intent(getApplicationContext(), DadosCimentoActivity.class);
                    intentAbrirDadosCimentoActivity.putExtra("nome do cimento", nomeDoCimento);
                    intentAbrirDadosCimentoActivity.putExtra("tempo de cura", tempoDeCura);
                    intentAbrirDadosCimentoActivity.putExtra("observações", observacoes);

                    startActivity(intentAbrirDadosCimentoActivity);
                }
            }
        });

        buttonAdicionarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((textInputValorDeAC.getText().toString().equals("")) || textInputValorDeFcj.getText().toString().equals("")) {
                    Toast.makeText(AdicionarEditarCimentoActivity.this, "Preencha os campos para adicionar um novo ponto.", Toast.LENGTH_LONG).show();
                } else {
                    itemPontoCimento.setValorDeAC(Double.parseDouble(textInputValorDeAC.getText().toString()));
                    itemPontoCimento.setValorDeFcj(Double.parseDouble(textInputValorDeFcj.getText().toString()));
                    CurvaCimentoProvisoriaDAO curvaCimentoProvisoriaDAO = new CurvaCimentoProvisoriaDAO(getApplicationContext());
                    curvaCimentoProvisoriaDAO.salvar(itemPontoCimento);
                    carregarListaDePontosDoCimentos();
                }
            }
        });

        //Configurar RecyclerView
        recyclerViewAdicionarEditarCimentos = findViewById(R.id.RecyclerViewAdicionarEditarCimentos);

        //Adicionar evento de clique
        recyclerViewAdicionarEditarCimentos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewAdicionarEditarCimentos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                deletarPontoCimento(position);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                deletarPontoCimento(position);
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

    }

    public void carregarListaDePontosDoCimentos() {

        //Listar Tarefas
        curvaCimentoProvisoriaDAO = new CurvaCimentoProvisoriaDAO(getApplicationContext());
        listaPontosCimento = curvaCimentoProvisoriaDAO.listar();


        /*
            Exibe pontos do cimento no RecyclerView
         */

        //Configurar um adapter
        pontosDoCimentoAdapter = new PontosDoCimentoAdapter((ArrayList<ItemPontoCimento>) listaPontosCimento);

        //Configurar um RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAdicionarEditarCimentos.setLayoutManager( layoutManager );
        recyclerViewAdicionarEditarCimentos.setHasFixedSize(true);
        //recyclerViewAdicionarEditarCimentos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerViewAdicionarEditarCimentos.setAdapter(pontosDoCimentoAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaDePontosDoCimentos();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void deletarPontoCimento(int position) {
        pontoCimentoSelecionado = listaPontosCimento.get(position);

        AlertDialog.Builder dialogExcluirPontoCimento = new AlertDialog.Builder(AdicionarEditarCimentoActivity.this);

        //Configurar titulo e mensagem
        dialogExcluirPontoCimento.setTitle("Excluir ponto");
        dialogExcluirPontoCimento.setMessage("Deseja realmente excluir este ponto dos dados do cimento?");

        //Configurar botões
        dialogExcluirPontoCimento.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CurvaCimentoProvisoriaDAO curvaCimentoProvisoriaDAO = new CurvaCimentoProvisoriaDAO(getApplicationContext());
                if ( curvaCimentoProvisoriaDAO.deletar(pontoCimentoSelecionado) ) {
                    carregarListaDePontosDoCimentos();
                    Toast.makeText(AdicionarEditarCimentoActivity.this, "Sucesso ao deletar ponto!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdicionarEditarCimentoActivity.this, "Erro ao deletar ponto!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogExcluirPontoCimento.setNeutralButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //Configurar criação e exibição da dialog
        dialogExcluirPontoCimento.create();
        dialogExcluirPontoCimento.show();
    }
}

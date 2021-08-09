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
import com.cursoandroid.appdosagemconcreto.classesdecalculo.RegressaoLinear;
import com.cursoandroid.appdosagemconcreto.helper.CurvaCimentoProvisoriaDAO;
import com.cursoandroid.appdosagemconcreto.model.ItemPontoCimento;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DadosCimentoActivity extends AppCompatActivity {

    private TextView textViewRotuloDoCimento, textViewDadosNomeDoCimento, textViewDadosTempoDeCura,
                     textViewQtdeDePontos, textViewFormulaDeAbrams;
    private Button buttonExibirPontos;
    private RecyclerView recyClerViewDadosCimento;
    private PontosDoCimentoAdapter pontosDoCimentoAdapter;
    private List<ItemPontoCimento> listaPontosCimento = new ArrayList<>();
    private CurvaCimentoProvisoriaDAO curvaCimentoProvisoriaDAO;
    private Double[][] arrayCurva;
    private RegressaoLinear regressaoLinear;

    // Classes de arredondamento e formatação
    private DecimalFormat arred0 = new DecimalFormat("####");
    private DecimalFormat arred1X = new DecimalFormat("##0.#");
    private DecimalFormat arred1 = new DecimalFormat("##0.0");
    private DecimalFormat arred2 = new DecimalFormat("##0.00");
    private DecimalFormat arred2x = new DecimalFormat("##0.##");
    private DecimalFormat arred3 = new DecimalFormat("##0.000");
    private DecimalFormat arred3x = new DecimalFormat("##0.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_cimento);

        Bundle dados = getIntent().getExtras();

        //Configurar elementos de interface
        textViewRotuloDoCimento = findViewById(R.id.textViewRotuloDoCimento);
        textViewDadosNomeDoCimento = findViewById(R.id.textViewDadosNomeDoCimento);
        textViewDadosTempoDeCura = findViewById(R.id.textViewDadosTempoDeCura);
        textViewQtdeDePontos = findViewById(R.id.textViewQtdeDePontos);
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
        curvaCimentoProvisoriaDAO = new CurvaCimentoProvisoriaDAO(getApplicationContext());
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

        textViewQtdeDePontos.setText("Qtde de pontos de amostras: " + listaPontosCimento.size());

        arrayCurva = new Double[listaPontosCimento.size()][2];
        int c = 0;
        while (c < listaPontosCimento.size()) {
            ItemPontoCimento itemPontoCursor = listaPontosCimento.get(c);
            arrayCurva[c][0] = itemPontoCursor.getValorDeAC();
            arrayCurva[c][1] = itemPontoCursor.getValorDeFck();
            c += 1;
        }
        regressaoLinear = new RegressaoLinear(arrayCurva);

        textViewFormulaDeAbrams = findViewById(R.id.textViewFormulaDeAbrams);
        textViewFormulaDeAbrams.setText("Lei de Abrams: fcj = " + arred2x.format(regressaoLinear.getK1()) + " / [" + arred2x.format(regressaoLinear.getK2()) +"^(a/c)]");
    }
}

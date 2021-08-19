package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.adapter.PontosDoCimentoAdapter;
import com.cursoandroid.appdosagemconcreto.classesdecalculo.RegressaoLinear;
import com.cursoandroid.appdosagemconcreto.helper.CimentosSalvosDAO;
import com.cursoandroid.appdosagemconcreto.helper.CodigosDeActivity;
import com.cursoandroid.appdosagemconcreto.helper.CurvaCimentoDAO;
import com.cursoandroid.appdosagemconcreto.helper.CurvaCimentoProvisoriaDAO;
import com.cursoandroid.appdosagemconcreto.model.ItemCimentoSalvo;
import com.cursoandroid.appdosagemconcreto.model.ItemPontoCimento;
import com.cursoandroid.appdosagemconcreto.tabelaseabacos.CurvaDeAbrams;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DadosCimentoActivity extends AppCompatActivity {

    private TextView textViewRotuloDoCimento, textViewDadosNomeDoCimento, textViewDadosTempoDeCura,
                     textViewQtdeDePontos, textViewFormulaDeAbramsK1, textViewFormulaDeAbramsK2, textViewObservacoes;
    private Button buttonDescartarCimento, buttonEditarCimento, buttonSalvarCimento, buttonExibirPontos;
    private RecyclerView recyClerViewDadosCimento;
    private PontosDoCimentoAdapter pontosDoCimentoAdapter;
    private List<ItemPontoCimento> listaPontosCimento = new ArrayList<>();
    private CurvaCimentoProvisoriaDAO curvaCimentoProvisoriaDAO;
    private ItemCimentoSalvo itemCimentoSalvo = new ItemCimentoSalvo();
    private Double[][] arrayCurva;
    private RegressaoLinear regressaoLinear;
    private LineChart graficoCurvaDeAbramsCimento;

    private String nomeDoCimento, tempoDeCura, obsevacoes;

    SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
    Date data = new Date();

    //Helper
    private CodigosDeActivity codigosDeActivity = new CodigosDeActivity();

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

        nomeDoCimento = dados.getString("nome do cimento");
        tempoDeCura = dados.getString("tempo de cura");
        obsevacoes = dados.getString("observações");


        //Configurar elementos de interface
        textViewRotuloDoCimento = findViewById(R.id.textViewRotuloDoCimento);
        textViewDadosNomeDoCimento = findViewById(R.id.textViewDadosNomeDoCimento);
        textViewDadosTempoDeCura = findViewById(R.id.textViewDadosTempoDeCura);
        textViewQtdeDePontos = findViewById(R.id.textViewQtdeDePontos);
        textViewFormulaDeAbramsK1 = findViewById(R.id.textViewFormulaDeAbramsK1);
        textViewFormulaDeAbramsK2 = findViewById(R.id.textViewFormulaDeAbramsK2);
        textViewObservacoes = findViewById(R.id.textViewObservacoes);
        buttonDescartarCimento = findViewById(R.id.buttonDescartarCimento);
        buttonEditarCimento = findViewById(R.id.buttonEditarCimento);
        buttonSalvarCimento = findViewById(R.id.buttonSalvarCimento);
        buttonExibirPontos = findViewById(R.id.buttonExibirPontos);
        recyClerViewDadosCimento = findViewById(R.id.recyclerViewDadosCimento);

        graficoCurvaDeAbramsCimento = findViewById(R.id.graficoCurvaDeAbramsCimento);

        textViewRotuloDoCimento.setText("Cimento " + nomeDoCimento);
        textViewDadosNomeDoCimento.setText("Nome do cimento: "  + nomeDoCimento);
        textViewDadosTempoDeCura.setText("Tempo de cura: "  + tempoDeCura + " dias");
        textViewObservacoes.setText("Observações: " + obsevacoes);

        buttonEditarCimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAbrirAdicionarEditarCimentoActivity = new Intent(getApplicationContext(), AdicionarEditarCimentoActivity.class);
                startActivity(intentAbrirAdicionarEditarCimentoActivity);
            }
        });

        buttonSalvarCimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Configura Alert Dialog
                AlertDialog.Builder dialogSalvarNovoCimento = new AlertDialog.Builder(DadosCimentoActivity.this);

                dialogSalvarNovoCimento.setTitle("Salvar novo cimento");
                dialogSalvarNovoCimento.setMessage("Deseja realmente salvar o cimento " + nomeDoCimento + " como um novo cimento?");

                dialogSalvarNovoCimento.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemCimentoSalvo.setNomeDoCimento(nomeDoCimento);
                        itemCimentoSalvo.setTempoDeCura(tempoDeCura);
                        itemCimentoSalvo.setQtdeDePontos(listaPontosCimento.size());
                        itemCimentoSalvo.setData(formataData.format(data));
                        CimentosSalvosDAO cimentosSalvosDAO = new CimentosSalvosDAO(getApplicationContext());
                        cimentosSalvosDAO.salvar(itemCimentoSalvo);

                        CurvaCimentoDAO curvaCimentoDAO = new CurvaCimentoDAO(getApplicationContext(), nomeDoCimento);
                        int cont = 0;
                        ItemPontoCimento itemPontoCimento;
                        while (cont < listaPontosCimento.size()) {
                            itemPontoCimento = listaPontosCimento.get(cont);
                            curvaCimentoDAO.salvar(itemPontoCimento);
                            cont += 1;
                        }

                        curvaCimentoProvisoriaDAO.limparTabela();

                        setResult(codigosDeActivity.adicionarEditarCimentoActivity);
                        finish();
                    }
                });

                dialogSalvarNovoCimento.setNegativeButton("Não", null);

                dialogSalvarNovoCimento.create();
                dialogSalvarNovoCimento.show();


            }
        });

        recyClerViewDadosCimento.setVisibility(View.GONE);

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
            arrayCurva[c][1] = itemPontoCursor.getValorDeAC();
            arrayCurva[c][0] = itemPontoCursor.getValorDeFcj();
            c += 1;
        }
        regressaoLinear = new RegressaoLinear(arrayCurva);

        textViewFormulaDeAbramsK1.setText(arred2x.format(regressaoLinear.getK1()));
        textViewFormulaDeAbramsK2.setText(arred2x.format(regressaoLinear.getK2()));
        configurarGraficoCurvaDeAbrams();
    }

    private void configurarGraficoCurvaDeAbrams() {

        graficoCurvaDeAbramsCimento.setDragEnabled(true);
        graficoCurvaDeAbramsCimento.setScaleEnabled(true);

        ArrayList<Entry> yValues = new ArrayList<>();

        Double cont = 0.0;
        while (cont < 1.0) {
            Float x = Float.valueOf(arred3x.format(cont).replace(",","."));
            Float y = Float.parseFloat(arred3x.format(regressaoLinear.calcularFcjPeloFatorAC(cont)).replace(",","."));
            yValues.add(new Entry( x , y));
            cont += 0.01;
        }

        LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");

        set1.setColor(Color.RED, 255);
        set1.setLineWidth(2f);
        set1.setCircleColor(Color.TRANSPARENT);
        set1.setCircleHoleColor(Color.TRANSPARENT);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.TRANSPARENT);
        set1.setLabel("Curva " + nomeDoCimento + " - 28 dias");
        set1.setHighLightColor(Color.TRANSPARENT);

        graficoCurvaDeAbramsCimento.getAxisRight().setEnabled(false);
        graficoCurvaDeAbramsCimento.setDescription(null);
        graficoCurvaDeAbramsCimento.setDrawBorders(true);
        graficoCurvaDeAbramsCimento.setBorderColor(getResources().getColor(R.color.colorTracoDivisao));
        graficoCurvaDeAbramsCimento.setBackgroundColor(Color.TRANSPARENT);

        graficoCurvaDeAbramsCimento.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData dadosGrafico = new LineData(dataSets);

        graficoCurvaDeAbramsCimento.setData(dadosGrafico);

    }
}

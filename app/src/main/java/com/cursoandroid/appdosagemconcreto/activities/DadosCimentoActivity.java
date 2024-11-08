package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.adapter.PontosDoCimentoAdapter;
import com.cursoandroid.appdosagemconcreto.classesdecalculo.RegressaoLinear;
import com.cursoandroid.appdosagemconcreto.helper.CimentosSalvosDAO;
import com.cursoandroid.appdosagemconcreto.helper.CodigosDeActivity;
import com.cursoandroid.appdosagemconcreto.helper.CurvaCimentoDAO;
import com.cursoandroid.appdosagemconcreto.helper.CurvaCimentoProvisoriaDAO;
import com.cursoandroid.appdosagemconcreto.helper.DbCimentoCurva;
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
import java.util.Collections;
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

    private String nomeDoCimento, tempoDeCura, observacoes, acao;
    private Long id;

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
        observacoes = dados.getString("observações");
        acao = dados.getString("ação");

        if (acao.equals("abrir cimento salvo") || acao.equals("abrir cimento editado")) {
            id = dados.getLong("id");
        }

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
        textViewObservacoes.setText("Observações: " + observacoes);

        // Configuração de exibição do botão editar cimento

        if (acao.equals("criar novo cimento")) {
            buttonEditarCimento.setVisibility(View.GONE);
        }

        // Configurações de eventos de clique

        buttonDescartarCimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Configuração da Alert Dialog
                AlertDialog.Builder dialogDescartarCimento = new AlertDialog.Builder(DadosCimentoActivity.this);

                if (acao.equals("abrir cimento salvo")) {
                    dialogDescartarCimento.setTitle("Descartar cimento");
                    dialogDescartarCimento.setMessage("Deseja realmente descartar este cimento (" + nomeDoCimento + ") e todas a informações contidas nele?");
                } else if (acao.equals("abrir cimento editado")) {
                    dialogDescartarCimento.setTitle("Descartar alterações");
                    dialogDescartarCimento.setMessage("Deseja realmente descartar as alterações realizadas neste cimento (" + nomeDoCimento + ") e retornar para a tela de cimentos salvos? (isso não apagará o cimento do banco de dados)");
                } else {
                    dialogDescartarCimento.setTitle("Descartar cimento");
                    dialogDescartarCimento.setMessage("Deseja realmente descartar este novo cimento (" + nomeDoCimento + ") e todas a informações contidas nele?");
                }
                dialogDescartarCimento.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (acao.equals("abrir cimento salvo")) {
                            CimentosSalvosDAO cimentosSalvosDAO = new CimentosSalvosDAO(getApplicationContext());
                            CurvaCimentoDAO curvaCimentoDAO = new CurvaCimentoDAO(getApplicationContext());

                            List<ItemCimentoSalvo> listaCimentosSalvos = cimentosSalvosDAO.buscarCimento(nomeDoCimento);
                            ItemCimentoSalvo cimentoSalvo = listaCimentosSalvos.get(0);

                            if (cimentosSalvosDAO.deletar(cimentoSalvo)) {
                                if (curvaCimentoDAO.deletarLista(curvaCimentoDAO.listar(nomeDoCimento))) {
                                    Toast.makeText(getApplicationContext(), "Sucesso ao descartar cimento salvo.", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Erro ao deletar lista de pontos de amostras do cimento!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Erro ao deletar cimento.", Toast.LENGTH_SHORT).show();
                            }
                        } else if (acao.equals("abrir cimento editado")) {
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Sucesso ao descartar cimento não salvo.", Toast.LENGTH_SHORT).show();
                            setResult(codigosDeActivity.adicionarEditarCimentoActivity);
                            finish();
                        }

                    }
                });

                dialogDescartarCimento.setNeutralButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                dialogDescartarCimento.create();
                dialogDescartarCimento.show();

            }

        });

        buttonEditarCimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAbrirAdicionarEditarCimentoActivity = new Intent(getApplicationContext(), AdicionarEditarCimentoActivity.class);
                intentAbrirAdicionarEditarCimentoActivity.putExtra("id", id);
                intentAbrirAdicionarEditarCimentoActivity.putExtra("nome do cimento", nomeDoCimento);
                intentAbrirAdicionarEditarCimentoActivity.putExtra("tempo de cura", tempoDeCura);
                intentAbrirAdicionarEditarCimentoActivity.putExtra("observações", observacoes);
                intentAbrirAdicionarEditarCimentoActivity.putExtra("ação", "editar cimento salvo");
                startActivityForResult(intentAbrirAdicionarEditarCimentoActivity, codigosDeActivity.dadosCimentoActivty);
                listaPontosCimento.clear();
            }
        });

        buttonSalvarCimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Configura Alert Dialog
                AlertDialog.Builder dialogSalvarNovoCimento = new AlertDialog.Builder(DadosCimentoActivity.this);

                if (acao.equals("criar novo cimento")) {
                    dialogSalvarNovoCimento.setTitle("Salvar novo cimento");
                    dialogSalvarNovoCimento.setMessage("Deseja realmente salvar o cimento " + nomeDoCimento + " como um novo cimento?");
                } else if (acao.equals("abrir cimento salvo")){
                    dialogSalvarNovoCimento.setTitle("Salvar cimento");
                    dialogSalvarNovoCimento.setMessage("Nenhuma alteração foi idenficada no cimento " + nomeDoCimento + ", deseja retornar à tela de cimentos salvos?");
                } else if (acao.equals("abrir cimento editado")) {
                    dialogSalvarNovoCimento.setTitle("Salvar alterações");
                    dialogSalvarNovoCimento.setMessage("Deseja realmente salvar as alterações realizadas no cimento " + nomeDoCimento + "?");
                }
                dialogSalvarNovoCimento.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemCimentoSalvo.setNomeDoCimento(nomeDoCimento);
                        itemCimentoSalvo.setTempoDeCura(tempoDeCura);
                        itemCimentoSalvo.setQtdeDePontos(listaPontosCimento.size());
                        itemCimentoSalvo.setData(formataData.format(data));
                        itemCimentoSalvo.setObservacoes(observacoes);
                        CimentosSalvosDAO cimentosSalvosDAO = new CimentosSalvosDAO(getApplicationContext());

                        if (acao.equals("criar novo cimento")) {
                            cimentosSalvosDAO.salvar(itemCimentoSalvo);

                            CurvaCimentoDAO curvaCimentoDAO = new CurvaCimentoDAO(getApplicationContext());
                            int cont = 0;
                            ItemPontoCimento itemPontoCimento;
                            while (cont < listaPontosCimento.size()) {
                                itemPontoCimento = listaPontosCimento.get(cont);
                                itemPontoCimento.setNomeDoCimento(nomeDoCimento);
                                curvaCimentoDAO.salvar(itemPontoCimento);
                                cont += 1;
                            }

                            curvaCimentoProvisoriaDAO.limparTabela();

                            setResult(codigosDeActivity.adicionarEditarCimentoActivity);

                            finish();
                        } else if(acao.equals("abrir cimento salvo")) {
                            curvaCimentoProvisoriaDAO.limparTabela();
                            finish();
                        } else if(acao.equals("abrir cimento editado")) {
                            itemCimentoSalvo.setId(id);
                            if (cimentosSalvosDAO.atualizar(itemCimentoSalvo)) {
                                Toast.makeText(getApplicationContext(), "Sucesso ao atualizar cimento!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Erro ao atualizar cimento!", Toast.LENGTH_SHORT).show();
                            }

                            CurvaCimentoDAO curvaCimentoDAO = new CurvaCimentoDAO(getApplicationContext());

                            curvaCimentoDAO.deletarLista(curvaCimentoDAO.listar(nomeDoCimento));

                            int cont = 0;
                            ItemPontoCimento itemPontoCimento;
                            while (cont < listaPontosCimento.size()) {
                                itemPontoCimento = listaPontosCimento.get(cont);
                                itemPontoCimento.setNomeDoCimento(nomeDoCimento);
                                curvaCimentoDAO.salvar(itemPontoCimento);
                                cont += 1;
                            }

                            curvaCimentoProvisoriaDAO.limparTabela();
                            finish();
                        }
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

        if (acao.equals("abrir cimento salvo")) {
            CurvaCimentoDAO curvaCimentoDAO = new CurvaCimentoDAO(getApplicationContext());
            try {
                List<ItemPontoCimento> listaPontosTransicao = curvaCimentoDAO.listar(nomeDoCimento);
                Collections.sort(listaPontosTransicao);
                int cont = 0;
                while (cont < listaPontosTransicao.size()) {
                    curvaCimentoProvisoriaDAO.salvar(listaPontosTransicao.get(cont));
                    Log.i("INFO", "a/c = " + listaPontosTransicao.get(cont).getValorDeAC().toString());
                    cont += 1;
                }
            } catch (Exception e) {
                Toast.makeText(this, "Erro ao listar pontos do cimento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (acao.equals("abrir cimento editado")) {

        }

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

        ArrayList<Entry> pontos = new ArrayList<>();
        List<ItemPontoCimento> listaDePontos = curvaCimentoProvisoriaDAO.listar();
        Collections.sort(listaDePontos);
        int contPontos = 0;
        while (contPontos < listaDePontos.size()) {
            Float x = Float.valueOf(arred3x.format(listaDePontos.get(contPontos).getValorDeAC()).replace(",","."));
            Float y = Float.valueOf(arred3x.format(listaDePontos.get(contPontos).getValorDeFcj()).replace(",","."));
            pontos.add(new Entry( x, y));
            contPontos += 1;
        }

        LineDataSet setPontos = new LineDataSet(pontos, "Data set pontos");

        setPontos.setColor(getResources().getColor(R.color.colorGrafico));
        setPontos.setLineWidth(0);
        setPontos.setHighLightColor(Color.TRANSPARENT);
        setPontos.setCircleColor(getResources().getColor(R.color.colorGrafico));
        setPontos.setCircleHoleColor(Color.TRANSPARENT);
        setPontos.setValueTextSize(10f);
        setPontos.setValueTextColor(Color.TRANSPARENT);
        setPontos.setLabel("Pontos inseridos");
        //setPontos.setHighLightColor(Color.TRANSPARENT);

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
        dataSets.add(setPontos);

        LineData dadosGrafico = new LineData(dataSets);

        graficoCurvaDeAbramsCimento.setData(dadosGrafico);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == codigosDeActivity.dadosCimentoActivty) {
            if (resultCode == codigosDeActivity.dadosCimentoActivty) {
                //Intent i = getIntent();
                //overridePendingTransition(0,0);
                //i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
            }
        }

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

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(this, "DadosCimentoActivity onStop", Toast.LENGTH_SHORT).show();
        curvaCimentoProvisoriaDAO.limparTabela();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        if (acao.equals("abrir cimento salvo")) {
            curvaCimentoProvisoriaDAO.limparTabela();
        }
    }
}

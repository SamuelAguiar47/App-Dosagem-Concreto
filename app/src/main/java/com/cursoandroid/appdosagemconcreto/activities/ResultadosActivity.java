package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.classesdecalculo.Dosagem;
import com.cursoandroid.appdosagemconcreto.classesdecalculo.Traco;
import com.cursoandroid.appdosagemconcreto.helper.CodigosDeActivity;
import com.cursoandroid.appdosagemconcreto.helper.TracoDAO;
import com.cursoandroid.appdosagemconcreto.materiais.Agua;
import com.cursoandroid.appdosagemconcreto.materiais.Areia;
import com.cursoandroid.appdosagemconcreto.materiais.Brita;
import com.cursoandroid.appdosagemconcreto.materiais.Cimento;
import com.cursoandroid.appdosagemconcreto.materiais.Concreto;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class ResultadosActivity extends AppCompatActivity {

    //Elementos de Interface
    private Button buttonExibirMemoriaDeCalculo;
    private LinearLayout linearLayoutMemoriaDeCalculo;
    private Button buttonEditar, buttonSalvar, buttonDescartar;
    private Button buttonExibirCurvaDeAbrams, buttonExibirTabelaAbatXDmax, buttonExibirTabelaDmaxXMF;
    private LinearLayout tabelaAbatXDmax, tabelaDmaxXMF;

    String tipoDeSalvamento;

    // Classes de cálculo
    private Dosagem dosagem = new Dosagem();
    private String acao;
    private int position;

    // Classes de arredondamento e formatação
    private DecimalFormat arred0 = new DecimalFormat("####");
    private DecimalFormat arred1 = new DecimalFormat("##0.0");
    private DecimalFormat arred1X = new DecimalFormat("##0.#");
    private DecimalFormat arred2 = new DecimalFormat("##0.00");
    private DecimalFormat arred2x = new DecimalFormat("##0.##");
    private DecimalFormat arred3 = new DecimalFormat("##0.000");
    private DecimalFormat arred3x = new DecimalFormat("##0.###");

    SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
    Date data = new Date();

    // Helper
    CodigosDeActivity codigosDeActivity = new CodigosDeActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        // Recuperar dados da Intent
        Bundle dados = getIntent().getExtras();


        dosagem = (Dosagem) dados.getSerializable("dosagem");
        acao = dados.getString("ação");

        if (acao.equals("abrirTracoSalvo")) {
            position = dados.getInt("position");
        }


        // Carregar elementos de interface
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonDescartar = findViewById(R.id.buttonDescartar);
        buttonEditar = findViewById(R.id.buttonEditar);
        buttonExibirMemoriaDeCalculo = findViewById(R.id.buttonExibirMemoriaDeCalculo);
        linearLayoutMemoriaDeCalculo = findViewById(R.id.linearLayoutMemoriaDeCalculo);
        buttonExibirTabelaAbatXDmax = findViewById(R.id.buttonVisualizarAbatXDmax);
        tabelaAbatXDmax = findViewById(R.id.tabelaAbatXDmax);
        buttonExibirTabelaDmaxXMF = findViewById(R.id.buttonVisualizarDmaxXMF);
        tabelaDmaxXMF = findViewById(R.id.tabelaDmaxXMF);
        marcarTabelas();

        // Memória de cálculo

        // Dados iniciais
        TextView textViewFckConcreto = findViewById(R.id.textViewFckConcreto);
        textViewFckConcreto.setText("Fck = " + arred1X.format(dosagem.concreto.getFck()) + " MPa");

        TextView textViewAbatimento = findViewById(R.id.textViewAbatimento);
        textViewAbatimento.setText("Abat. (Slump test) = " + arred1X.format(dosagem.concreto.getAbatimento()));

        TextView textViewTipoDeCimento = findViewById(R.id.textViewTipoDeCimento);
        textViewTipoDeCimento.setText("Tipo de cimento = " + dosagem.cimento.getEspecificacoes());

        TextView textViewMassaEspecificaCimento = findViewById(R.id.textViewMassaEspecificaCimento);
        textViewMassaEspecificaCimento.setText("Massa esp. = " + arred1X.format(dosagem.cimento.getMassaEspecifica()) + " kg/m³");

        TextView textViewModuloDeFinuraAreia = findViewById(R.id.textViewModuloDeFinuraAreia);
        textViewModuloDeFinuraAreia.setText("Mód. de finura = " + arred1.format(dosagem.areia.getModuloDefinura()));

        TextView textViewMassaEspecificaAreia = findViewById(R.id.textViewMassaEspecificaAreia);
        textViewMassaEspecificaAreia.setText("Massa esp. = " + arred1X.format(dosagem.areia.getMassaEspecifica()) + " kg/m³");

        TextView textViewMassaUnitariaAreia = findViewById(R.id.textViewMassaUnitariaAreia);
        textViewMassaUnitariaAreia.setText("Massa unit. = " + arred1X.format(dosagem.areia.getMassaUnitaria()) + " kg/m³");

        TextView textViewDiametroMaximoBrita = findViewById(R.id.textViewDiametroMaximoBrita);
        textViewDiametroMaximoBrita.setText("Diâm. máx. = " + arred1.format(dosagem.brita.getDiametroMaximo()) + " mm");

        TextView textViewMassaEspecificaBrita = findViewById(R.id.textViewMassaEspecificaBrita);
        textViewMassaEspecificaBrita.setText("Massa esp. = " + arred1X.format(dosagem.brita.getMassaEspecifica()) + " kg/m³");

        TextView textViewMassaUnitariaCompBrita = findViewById(R.id.textViewMassaUnitariaCompBrita);
        textViewMassaUnitariaCompBrita.setText("Massa unit. comp. = " + arred1X.format(dosagem.brita.getMassaUnitariaComp()) + " kg/m³");

        TextView textViewMassaUnitariaBrita = findViewById(R.id.textViewMassaUnitariaBrita);
        textViewMassaUnitariaBrita.setText("Massa unit. = " + arred1X.format(dosagem.brita.getMassaUnitaria()) + " kg/m³");

        TextView textViewDesvioPadrao = findViewById(R.id.textViewDesvioPadrao);
        textViewDesvioPadrao.setText("Sd = " + arred1.format(dosagem.concreto.getDesvioPadrao()));

        calculoDosagem();

        // Cálculo Fcj
        TextView textViewFcjCalculo = findViewById(R.id.textViewFcjCalculo);
        textViewFcjCalculo.setText("Fcj = " + arred1X.format(dosagem.concreto.getFck()) + " + 1,65*" + arred1.format(dosagem.concreto.getDesvioPadrao()));

        TextView textViewFcjResultado = findViewById(R.id.textViewFcjResultado);
        textViewFcjResultado.setText("Fcj = " + arred3.format(dosagem.concreto.getFcj()) + " Mpa");

        // Determinação do fator a/c
        TextView textViewDeterminacaoFatorAC = findViewById(R.id.textViewDeterminacaoFatorAC);
        textViewDeterminacaoFatorAC.setText("a/c = " + arred3.format(dosagem.concreto.getFatorAguaCimento()));

        // Determinação do consumo de água
        TextView textViewDeterminacaoCA = findViewById(R.id.textViewDeterminacaoCA);
        textViewDeterminacaoCA.setText("Ca = " + arred0.format(dosagem.agua.getConsumoDeAgua()) + " l/m³");

        // Cálculo do consumo de cimento
        TextView textViewConsumoDeCimentoCalculo = findViewById(R.id.textViewConsumoDeCimentoCalculo);
        textViewConsumoDeCimentoCalculo.setText("Cc = " + arred0.format(dosagem.agua.getConsumoDeAgua()) + " / " + arred3.format(dosagem.concreto.getFatorAguaCimento()));

        TextView textViewConsumoDeCimentoResultado = findViewById(R.id.textViewConsumoDeCimentoResultado);
        textViewConsumoDeCimentoResultado.setText("Cc = " + arred2x.format(dosagem.cimento.getConsumoDeCimento()) + " kg/m³");

        // Determinação do volume de brita
        TextView textViewVolumeDeBritaResultado = findViewById(R.id.textViewVolumeDeBritaResultado);
        textViewVolumeDeBritaResultado.setText("Vb (tabela) = " + arred3.format(dosagem.brita.getVolumeDeBritaTabela()) + " m³/m³");

        // Cálculo do consumo de brita
        TextView textViewConsumoDeBritaCalculo = findViewById(R.id.textViewConsumoDeBritaCalculo);
        textViewConsumoDeBritaCalculo.setText("Cb = " + arred3.format(dosagem.brita.getVolumeDeBritaTabela()) + " * " + dosagem.brita.getMassaUnitariaComp());

        TextView textViewConsumoDeBritaResultado = findViewById(R.id.textViewConsumoDeBritaResultado);
        textViewConsumoDeBritaResultado.setText("Cb = " + arred2x.format(dosagem.brita.getConsumoDeBrita()) + " kg/m³");

        // Cálculo do volume de areia
        TextView textViewVolumeDeAreiaCalculo = findViewById(R.id.textViewVolumeDeAreiaCalculo);
        textViewVolumeDeAreiaCalculo.setText("Vareia =  1 - (" + arred3.format(dosagem.cimento.getVolumeDeCimento()) +
                                            " + " + arred3.format(dosagem.brita.getVolumeDeBrita()) + " + " + arred3.format(dosagem.agua.getVolumeDeAgua()) + ")");

        TextView textViewVolumeDeAreiaResultado = findViewById(R.id.textViewVolumeDeAreiaResultado);
        textViewVolumeDeAreiaResultado.setText("Vareia = " + arred3.format(dosagem.areia.getVolumeDeAreia()) + " m³/m³");

        // Cálculo do consumo de areia
        TextView textViewConsumoDeAreiaCalculo = findViewById(R.id.textViewConsumoDeAreiaCalculo);
        textViewConsumoDeAreiaCalculo.setText("Careia = " + arred3.format(dosagem.areia.getVolumeDeAreia()) + " * " + arred2x.format(dosagem.areia.getMassaEspecifica()));

        TextView textViewConsumoDeAreiaResultado = findViewById(R.id.textViewConsumoDeAreiaResultado);
        textViewConsumoDeAreiaResultado.setText("Careia = " + arred2x.format(dosagem.areia.getConsumoDeAreia()) + " kg/m³");

        // Cálculo do traço em massa

        TextView textViewTracoConsumoDeCimentoDividendo = findViewById(R.id.textViewTracoConsumoDeCimentoDividendo);
        textViewTracoConsumoDeCimentoDividendo.setText(arred2x.format(dosagem.cimento.getConsumoDeCimento()));

        TextView textViewTracoConsumoDeCimentoQuociente1 = findViewById(R.id.textViewTracoConsumoDeCimentoQuociente1);
        textViewTracoConsumoDeCimentoQuociente1.setText(arred2x.format(dosagem.cimento.getConsumoDeCimento()));

        TextView textViewTracoConsumoDeAreiaDividendo = findViewById(R.id.textViewTracoConsumoDeAreiaDividendo);
        textViewTracoConsumoDeAreiaDividendo.setText(arred2x.format(dosagem.areia.getConsumoDeAreia()));

        TextView textViewTracoConsumoDeCimentoQuociente2 = findViewById(R.id.textViewTracoConsumoDeCimentoQuociente2);
        textViewTracoConsumoDeCimentoQuociente2.setText(arred2x.format(dosagem.cimento.getConsumoDeCimento()));

        TextView textViewTracoConsumoDeBritaDividendo = findViewById(R.id.textViewTracoConsumoDeBritaDividendo);
        textViewTracoConsumoDeBritaDividendo.setText(arred2x.format(dosagem.brita.getConsumoDeBrita()));

        TextView textViewTracoConsumoDeCimentoQuociente3 = findViewById(R.id.textViewTracoConsumoDeCimentoQuociente3);
        textViewTracoConsumoDeCimentoQuociente3.setText(arred2x.format(dosagem.cimento.getConsumoDeCimento()));

        TextView textViewTracoConsumoDeAguaDividendo = findViewById(R.id.textViewTracoConsumoDeAguaDividendo);
        textViewTracoConsumoDeAguaDividendo.setText(arred2x.format(dosagem.agua.getConsumoDeAgua()));

        TextView textViewTracoConsumoDeCimentoQuociente4 = findViewById(R.id.textViewTracoConsumoDeCimentoQuociente4);
        textViewTracoConsumoDeCimentoQuociente4.setText(arred2x.format(dosagem.cimento.getConsumoDeCimento()));

        TextView textViewTracoEmMassaResultado = findViewById(R.id.textViewTracoEmMassaResultado);
        textViewTracoEmMassaResultado.setText(arred2x.format(dosagem.getTracoEmMassa()[0]) + " : "
                                            + arred2x.format(dosagem.getTracoEmMassa()[1]) + " : "
                                            + arred2x.format(dosagem.getTracoEmMassa()[2]) + " : "
                                            + arred2x.format(dosagem.getTracoEmMassa()[3]));

        dosagem.setTracoExibido(textViewTracoEmMassaResultado.getText().toString());
        dosagem.traco.setTracoExibido(textViewTracoEmMassaResultado.getText().toString());
        dosagem.traco.setTipoDeTraco("Traco em massa");
        dosagem.traco.setDataDoTraco(formataData.format(data));



        final TextView editTextTracoExibidoCimento = findViewById(R.id.editTextTracoExibidoCimento);
        editTextTracoExibidoCimento.setText(arred2x.format(dosagem.getTracoEmMassa()[0]));
        final TextView editTextTracoExibidoAreia = findViewById(R.id.editTextTracoExibidoAreia);
        editTextTracoExibidoAreia.setText(arred2x.format(dosagem.getTracoEmMassa()[1]));
        final TextView editTextTracoExibidoBrita = findViewById(R.id.editTextTracoExibidoBrita);
        editTextTracoExibidoBrita.setText(arred2x.format(dosagem.getTracoEmMassa()[2]));
        final TextView editTextTracoExibidoAgua = findViewById(R.id.editTextTracoExibidoAgua);
        editTextTracoExibidoAgua.setText(arred2x.format(dosagem.getTracoEmMassa()[3]));

        final Double[] tracoProporcao = new Double[4];
        tracoProporcao[0] = Double.parseDouble(editTextTracoExibidoCimento.getText().toString().replace(",","."));
        tracoProporcao[1] = Double.parseDouble(editTextTracoExibidoAreia.getText().toString().replace(",","."));
        tracoProporcao[2] = Double.parseDouble(editTextTracoExibidoBrita.getText().toString().replace(",","."));
        tracoProporcao[3] = Double.parseDouble(editTextTracoExibidoAgua.getText().toString().replace(",","."));


        editTextTracoExibidoCimento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (editTextTracoExibidoCimento.hasFocus()) {
                        Double tracoCimentoInput;
                        if (editTextTracoExibidoCimento.getText().toString().isEmpty()) {
                            tracoCimentoInput = 0.0;
                        } else {
                            tracoCimentoInput = Double.parseDouble(editTextTracoExibidoCimento.getText().toString().replace(",", "."));
                        }
                        Double tracoAreiaMultiplicado = tracoCimentoInput * tracoProporcao[1];
                        Double tracoBritaMultiplicado = tracoCimentoInput * tracoProporcao[2];
                        Double tracoAguaMultiplicado = tracoCimentoInput * tracoProporcao[3];
                        editTextTracoExibidoAreia.setText(arred2x.format(tracoAreiaMultiplicado));
                        editTextTracoExibidoBrita.setText(arred2x.format(tracoBritaMultiplicado));
                        editTextTracoExibidoAgua.setText(arred2x.format(tracoAguaMultiplicado));
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextTracoExibidoAreia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (editTextTracoExibidoAreia.hasFocus()) {
                        Double tracoAreiaInput;
                        if (editTextTracoExibidoAreia.getText().toString().isEmpty()) {
                            tracoAreiaInput = 0.0;
                        } else {
                            tracoAreiaInput = Double.parseDouble(editTextTracoExibidoAreia.getText().toString().replace(",", "."));
                        }
                        Double tracoCimentoMultiplicado = tracoAreiaInput * tracoProporcao[0] / tracoProporcao[1];
                        Double tracoBritaMultiplicado = tracoAreiaInput * tracoProporcao[2] / tracoProporcao[1];
                        Double tracoAguaMultiplicado = tracoAreiaInput * tracoProporcao[3] / tracoProporcao[1];
                        editTextTracoExibidoCimento.setText(arred2x.format(tracoCimentoMultiplicado));
                        editTextTracoExibidoBrita.setText(arred2x.format(tracoBritaMultiplicado));
                        editTextTracoExibidoAgua.setText(arred2x.format(tracoAguaMultiplicado));
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextTracoExibidoBrita.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editTextTracoExibidoBrita.hasFocus()) {
                    Double tracoBritaInput;
                    if (editTextTracoExibidoBrita.getText().toString().isEmpty()) {
                        tracoBritaInput = 0.0;
                    } else {
                        tracoBritaInput = Double.parseDouble(editTextTracoExibidoBrita.getText().toString().replace(",", "."));
                    }
                    Double tracoCimentoMultiplicado = tracoBritaInput * tracoProporcao[0] / tracoProporcao[2];
                    Double tracoAreiaMultiplicado = tracoBritaInput * tracoProporcao[1] / tracoProporcao[2];
                    Double tracoAguaMultiplicado = tracoBritaInput * tracoProporcao[3] / tracoProporcao[2];
                    editTextTracoExibidoCimento.setText(arred2x.format(tracoCimentoMultiplicado));
                    editTextTracoExibidoAreia.setText(arred2x.format(tracoAreiaMultiplicado));
                    editTextTracoExibidoAgua.setText(arred2x.format(tracoAguaMultiplicado));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextTracoExibidoAgua.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }



            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editTextTracoExibidoAgua.hasFocus()) {
                    Double tracoAguaInput;
                    if (editTextTracoExibidoAgua.getText().toString().isEmpty()) {
                        tracoAguaInput = 0.0;
                    } else {
                        tracoAguaInput = Double.parseDouble(editTextTracoExibidoAgua.getText().toString().replace(",", "."));
                    }
                    Double tracoCimentoMultiplicado = tracoAguaInput * tracoProporcao[0] / tracoProporcao[3];
                    Double tracoAreiaMultiplicado = tracoAguaInput * tracoProporcao[1] / tracoProporcao[3];
                    Double tracoBritaMultiplicado = tracoAguaInput * tracoProporcao[2] / tracoProporcao[3];
                    editTextTracoExibidoCimento.setText(arred2x.format(tracoCimentoMultiplicado));
                    editTextTracoExibidoAreia.setText(arred2x.format(tracoAreiaMultiplicado));
                    editTextTracoExibidoBrita.setText(arred2x.format(tracoBritaMultiplicado));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        // Configurar visibilidade da memória de cálculo
        //linearLayoutMemoriaDeCalculo.setVisibility(View.GONE);

        buttonExibirMemoriaDeCalculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutMemoriaDeCalculo.getVisibility() == View.GONE) {
                    linearLayoutMemoriaDeCalculo.setVisibility(View.VISIBLE);
                    buttonExibirMemoriaDeCalculo.setText("Ocultar memória de cálculo");
                } else {
                    linearLayoutMemoriaDeCalculo.setVisibility(View.GONE);
                    buttonExibirMemoriaDeCalculo.setText("Exibir memória de cálculo");
                }
            }
        });

        // Configurar buttonExibirTabelaAbatXDmax

        tabelaAbatXDmax.setVisibility(View.GONE);

        buttonExibirTabelaAbatXDmax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (tabelaAbatXDmax.getVisibility() == View.GONE) {
                  tabelaAbatXDmax.setVisibility(View.VISIBLE);
                  buttonExibirTabelaAbatXDmax.setText("Ocultar tabela (ABAT. X Dmax)");
              } else {
                  tabelaAbatXDmax.setVisibility(View.GONE);
                  buttonExibirTabelaAbatXDmax.setText("Visualizar tabela (ABAT. X Dmax)");
              }
            }
        });

        // Configurar buttonExibirTabelaAbatXDmax

        tabelaDmaxXMF.setVisibility(View.GONE);

        buttonExibirTabelaDmaxXMF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tabelaDmaxXMF.getVisibility() == View.GONE) {
                    tabelaDmaxXMF.setVisibility(View.VISIBLE);
                    buttonExibirTabelaDmaxXMF.setText("Ocultar tabela (Dmax. X M.F.)");
                } else {
                    tabelaDmaxXMF.setVisibility(View.GONE);
                    buttonExibirTabelaDmaxXMF.setText("Vizualizar tabela (Dmax. X M.F.)");
                }
            }
        });

        // Configurar botão de salvar
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (acao.equals("abrirTracoSalvo")) {
                    abrirDialogSalvar(view);
                } else {
                    abrirDialogSalvarNovoTraco(view);
                }
            }
        });

        // Configurar botão de descartar
        buttonDescartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDialogDescartar(view);
            }
        });

        //Configuração botão de editar
        if (acao.equals("calcularNovoTraco")) {
            buttonEditar.setVisibility(View.GONE);
        }

        if (acao.equals("abrirTracoSalvo")) {
            buttonEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentAbrirInserirDadosActivity = new Intent(getApplicationContext(), InserirDadosActivity.class);
                    intentAbrirInserirDadosActivity.putExtra("acao", "editarTracoSalvo");
                    intentAbrirInserirDadosActivity.putExtra("position", position);
                    startActivityForResult(intentAbrirInserirDadosActivity, codigosDeActivity.resultadosActivity);
                }
            });
        }

    }

    public void calculoDosagem() {

        dosagem.calcularFcj();

        dosagem.determinarFatorAguaCimento();

        dosagem.determinarConsumoDeAgua();

        dosagem.determinarConsumoDeCimento();

        dosagem.determinarVolumeDeBrita();

        dosagem.determinarConsumoDeBrita();

        dosagem.determinarVolumeDeAreia();

        dosagem.determinarConsumoDeAreia();

        dosagem.determinarTracoEmMassa();
    }

    public void abrirDialogSalvar(final View view) {

        // Instanciar AlertDialog
        AlertDialog.Builder dialogSalvar = new AlertDialog.Builder( this );

        // Configurar título e mensagem
        dialogSalvar.setTitle("Salvar Traço");
        dialogSalvar.setMessage("Deseja salvar como um novo traço ou sobrescrever o traço existente?");

        // Configurar cancelameto
        dialogSalvar.setCancelable(false);

        // Configurar ações para sim e não
        dialogSalvar.setPositiveButton("Novo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirDialogSalvarNovoTraco(view);
            }
        });

        dialogSalvar.setNegativeButton("Sobrescrever", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TracoDAO tracoDAO = new TracoDAO(getApplicationContext());
                if (tracoDAO.atualizar(dosagem)) {
                    Toast.makeText(getApplicationContext(), "Sucesso ao atualizar traço!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao atualizar traço!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogSalvar.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        // Criar e exibir AlertDialog
        dialogSalvar.create();
        dialogSalvar.show();

    }

    public void abrirDialogSalvarNovoTraco(View view) {

        // Instanciar AlertDialog
        AlertDialog.Builder dialogSalvarNovoTraco = new AlertDialog.Builder( this );

        // Configurar título e mensagem
        dialogSalvarNovoTraco.setTitle("Salvar Traço");

        // Configurar cancelameto
        dialogSalvarNovoTraco.setCancelable(false);

        // Configurar caixa de texto
        final View viewSalvarNovoTracoContainer = LayoutInflater.from(this).inflate(
                R.layout.alert_dialog_salvar,
                (LinearLayout) findViewById(R.id.alertDialogSalvarContainer)
        );

        dialogSalvarNovoTraco.setView(viewSalvarNovoTracoContainer);

        // Configurar ações para sim e não
        dialogSalvarNovoTraco.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                TextInputEditText textInputNomeDoTraco = viewSalvarNovoTracoContainer.findViewById(R.id.textInputNomeDoTraco);
                String nomeDoTraco = textInputNomeDoTraco.getText().toString();
                if ( nomeDoTraco == null || nomeDoTraco.equals("")) {
                    dosagem.traco.setNomeDoTraco("Traço sem nome");
                } else {
                    dosagem.traco.setNomeDoTraco(nomeDoTraco);
                }


                TracoDAO tracoDAO = new TracoDAO(getApplicationContext());
                try {
                    tracoDAO.salvar(dosagem);
                    Toast.makeText(getApplicationContext(), "Traço salvo com sucesso", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Erro ao salvar traço!", Toast.LENGTH_SHORT).show();
                }

                Intent intentAbrirTracosSalvosActivity = new Intent(getApplicationContext(), TracosSalvosActivity.class);
                startActivity(intentAbrirTracosSalvosActivity);
                setResult(codigosDeActivity.inserirDadosActivity);
                finish();

            }
        });

        dialogSalvarNovoTraco.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        // Criar e exibir AlertDialog
        dialogSalvarNovoTraco.create();
        dialogSalvarNovoTraco.show();

    }

    public void abrirDialogDescartar(View view) {

        // Instanciar AlertDialog
        AlertDialog.Builder dialogDescartar = new AlertDialog.Builder( this );

        // Configurar título e mensagem
        dialogDescartar.setTitle("Deseja realmente descartar este traço?");

        // Configurar cancelameto
        dialogDescartar.setCancelable(false);

        // Configurar ações para sim e não
        dialogDescartar.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                TracoDAO tracoDAO = new TracoDAO(getApplicationContext());
                if ( tracoDAO.deletar(dosagem) ) {
                    Toast.makeText(getApplicationContext(), "Sucesso ao excluir traço!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao excluir traço!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        dialogDescartar.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        // Criar e exibir AlertDialog
        dialogDescartar.create();
        dialogDescartar.show();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == codigosDeActivity.resultadosActivity) {
            if (resultCode == codigosDeActivity.resultadosActivity) {
                //Intent i = getIntent();
                //overridePendingTransition(0,0);
                //i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
            }
        }

    }

    public void marcarTabelas() {

        // Tabela Abat x Diam. Max.
        String cel = null;

        Double abat = dosagem.concreto.getAbatimento();
        if (abat >= 40 && abat <= 60) {
            TextView t1Abat40a60 = findViewById(R.id.t1Abat40a60);
            t1Abat40a60.setTextColor(getResources().getColor(R.color.colorAccent));
            cel = "0";
        } else if (abat > 60 && abat <= 80) {
            TextView t1Abat60a80 = findViewById(R.id.t1Abat60a80);
            t1Abat60a80.setTextColor(getResources().getColor(R.color.colorAccent));
            cel = "1";
        } else if (abat >= 80 && abat <= 100) {
            TextView t1Abat80a100 = findViewById(R.id.t1Abat80a100);
            t1Abat80a100.setTextColor(getResources().getColor(R.color.colorAccent));
            cel = "2";
        }

        Double dim = dosagem.brita.getDiametroMaximo();
        if (dim <= 9.5) {
            TextView t1Dim9e5 = findViewById(R.id.t1Dim9e5);
            t1Dim9e5.setTextColor(getResources().getColor(R.color.colorAccent));
            cel += "0";
        } else if (dim <= 19) {
            TextView t1Dim19 = findViewById(R.id.t1Dim19);
            t1Dim19.setTextColor(getResources().getColor(R.color.colorAccent));
            cel += "1";
        } else if (dim <= 25) {
            TextView t1Dim25 = findViewById(R.id.t1Dim25);
            t1Dim25.setTextColor(getResources().getColor(R.color.colorAccent));
            cel += "2";
        } else if (dim <= 32) {
            TextView t1Dim32 = findViewById(R.id.t1Dim32);
            t1Dim32.setTextColor(getResources().getColor(R.color.colorAccent));
            cel += "3";
        } else if (dim <= 38) {
            TextView t1Dim38 = findViewById(R.id.t1Dim38);
            t1Dim38.setTextColor(getResources().getColor(R.color.colorAccent));
            cel += "4";
        }

        if (cel.equals("00")) {
            TextView t1c00 = findViewById(R.id.t1c00);
            t1c00.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("01")) {
            TextView t1c01 = findViewById(R.id.t1c01);
            t1c01.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("02")) {
            TextView t1c02 = findViewById(R.id.t1c02);
            t1c02.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("03")) {
            TextView t1c03 = findViewById(R.id.t1c03);
            t1c03.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("04")) {
            TextView t1c04 = findViewById(R.id.t1c04);
            t1c04.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("10")) {
            TextView t1c10 = findViewById(R.id.t1c10);
            t1c10.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("11")) {
            TextView t1c11 = findViewById(R.id.t1c11);
            t1c11.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("12")) {
            TextView t1c12 = findViewById(R.id.t1c12);
            t1c12.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("13")) {
            TextView t1c13 = findViewById(R.id.t1c13);
            t1c13.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("14")) {
            TextView t1c14 = findViewById(R.id.t1c14);
            t1c14.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("20")) {
            TextView t1c20 = findViewById(R.id.t1c20);
            t1c20.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("21")) {
            TextView t1c21 = findViewById(R.id.t1c21);
            t1c21.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("22")) {
            TextView t1c22 = findViewById(R.id.t1c22);
            t1c22.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("23")) {
            TextView t1c23 = findViewById(R.id.t1c23);
            t1c23.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (cel.equals("24")) {
            TextView t1c24 = findViewById(R.id.t1c24);
            t1c24.setTextColor(getResources().getColor(R.color.colorAccent));
        }

        // Tabela Diam. Max. x Mod. de Finura
        cel = null;

        Double mf = dosagem.areia.getModuloDefinura();
        if (mf == 1.8) {
            TextView t2MF18 = findViewById(R.id.t2MF18);
            t2MF18.setTextColor(getResources().getColor(R.color.colorAccent));
            cel = "0";
        } else if (mf == 2.0) {
            TextView t2MF20 = findViewById(R.id.t2MF20);
            t2MF20.setTextColor(getResources().getColor(R.color.colorAccent));
            cel = "1";
        } else if (mf == 2.2) {
            TextView t2MF22 = findViewById(R.id.t2MF22);
            t2MF22.setTextColor(getResources().getColor(R.color.colorAccent));
            cel = "2";
        } else if (mf == 2.4) {
            TextView t2MF24 = findViewById(R.id.t2MF24);
            t2MF24.setTextColor(getResources().getColor(R.color.colorAccent));
            cel = "3";
        } else if (mf == 2.6) {
            TextView t2MF26 = findViewById(R.id.t2MF26);
            t2MF26.setTextColor(getResources().getColor(R.color.colorAccent));
            cel = "4";
        } else if (mf == 2.8) {
            TextView t2MF28 = findViewById(R.id.t2MF28);
            t2MF28.setTextColor(getResources().getColor(R.color.colorAccent));
            cel = "5";
        } else if (mf == 3.0) {
            TextView t2MF30 = findViewById(R.id.t2MF30);
            t2MF30.setTextColor(getResources().getColor(R.color.colorAccent));
            cel = "6";
        } else if (mf == 3.2) {
            TextView t2MF32 = findViewById(R.id.t2MF32);
            t2MF32.setTextColor(getResources().getColor(R.color.colorAccent));
            cel = "7";
        } else if (mf == 3.4) {
            TextView t2MF34 = findViewById(R.id.t2MF34);
            t2MF34.setTextColor(getResources().getColor(R.color.colorAccent));
            cel = "8";
        } else if (mf == 3.6) {
            TextView t2MF36 = findViewById(R.id.t2MF36);
            t2MF36.setTextColor(getResources().getColor(R.color.colorAccent));
            cel = "9";
        }



        dim = dosagem.brita.getDiametroMaximo();
        if (dim <= 9.5) {
            TextView t2Dim9e5 = findViewById(R.id.t2Dim9e5);
            t2Dim9e5.setTextColor(getResources().getColor(R.color.colorAccent));
            cel += "0";
        } else if (dim <= 19) {
            TextView t2Dim19 = findViewById(R.id.t2Dim19);
            t2Dim19.setTextColor(getResources().getColor(R.color.colorAccent));
            cel += "1";
        } else if (dim <= 25) {
            TextView t2Dim25 = findViewById(R.id.t2Dim25);
            t2Dim25.setTextColor(getResources().getColor(R.color.colorAccent));
            cel += "2";
        } else if (dim <= 32) {
            TextView t2Dim32 = findViewById(R.id.t2Dim32);
            t2Dim32.setTextColor(getResources().getColor(R.color.colorAccent));
            cel += "3";
        } else if (dim <= 38) {
            TextView t2Dim38 = findViewById(R.id.t2Dim38);
            t2Dim38.setTextColor(getResources().getColor(R.color.colorAccent));
            cel += "4";
        }




    }



}

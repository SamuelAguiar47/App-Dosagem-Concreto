package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

    private Button buttonExibirMemoriaDeCalculo;
    private LinearLayout linearLayoutMemoriaDeCalculo;
    private Button buttonEditar, buttonSalvar, buttonDescartar;

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
                    startActivity(intentAbrirInserirDadosActivity);
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
                tipoDeSalvamento = "sobrescrever";
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
                Toast.makeText(getApplicationContext(), "Salvando...", Toast.LENGTH_SHORT).show();

                TracoDAO tracoDAO = new TracoDAO(getApplicationContext());
                tracoDAO.salvar(dosagem);

                Intent intentAbrirTracosSalvosActivity = new Intent(getApplicationContext(), TracosSalvosActivity.class);
                startActivity(intentAbrirTracosSalvosActivity);
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

        dialogDescartar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        // Criar e exibir AlertDialog
        dialogDescartar.create();
        dialogDescartar.show();

    }



}

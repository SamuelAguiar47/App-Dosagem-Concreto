package com.cursoandroid.appdosagemconcreto.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class InserirDadosActivity extends AppCompatActivity {

    // Elementos da interface
    private TextInputEditText textInputFck, textInputAbatimento, textInputMassaEspecificaCimento,
            textInputModuloDeFinuraAreia, textInputMassaEspecificaAreia, textInputMassaUnitariaAreia,
            textInputDiametroMaximoBrita, textInputMassaEspecificaBrita, textInputMassaUnitariaCompBrita,
            textInputMassaUnitariaBrita;
    private Spinner spinnerTipoDeCimento, spinnerDesvioPadrao, spinnerTipoDeTraco;
    private TextView textViewUnidadesTraco, textViewUnidadesCimento, textViewUnidadesAreia, textViewUnidadesBrita, textViewUnidadesAgua;
    private Button buttonCalcularTraco;

    // Materiais
    private Cimento cimento = new Cimento();
    private Areia areia = new Areia();
    private Brita brita = new Brita();
    private Agua agua = new Agua();
    private Concreto concreto = new Concreto();

    // Classes de calculo
    private Dosagem dosagem = new Dosagem();
    private Traco traco = new Traco();
    String acao;
    int position;
    private List<Dosagem> listaDosagens = new ArrayList<>();

    // Helper
    CodigosDeActivity codigosDeActivity = new CodigosDeActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_dados);

        // Recuperar dados da Intent
        Bundle dados = getIntent().getExtras();
        acao = dados.getString("acao");

        // Caixas de Texto
        textInputFck = findViewById(R.id.textInputFck);
        textInputAbatimento = findViewById(R.id.textInputAbatimento);
        textInputMassaEspecificaCimento = findViewById(R.id.textInputMassaEspecificaCimento);
        textInputModuloDeFinuraAreia = findViewById(R.id.textInputModuloDeFinuraAreia);
        textInputMassaEspecificaAreia = findViewById(R.id.textInputMassaEspecificaAreia);
        textInputMassaUnitariaAreia = findViewById(R.id.textInputMassaUnitariaAreia);
        textInputDiametroMaximoBrita = findViewById(R.id.textInputDiametroMaximoBrita);
        textInputMassaEspecificaBrita = findViewById(R.id.textInputMassaEspecificaBrita);
        textInputMassaUnitariaCompBrita = findViewById(R.id.textInputMassaUnitariaCompBrita);
        textInputMassaUnitariaBrita = findViewById(R.id.textInputMassaUnitariaBrita);

        //Spinners
        spinnerTipoDeCimento = findViewById(R.id.spinnerTipoDeCimento);
        spinnerDesvioPadrao = findViewById(R.id.spinnerDesvioPadrao);
        spinnerTipoDeTraco = findViewById(R.id.spinnerTipoDeTraco);

        carregarDadosSpinner();
        spinnerTipoDeTraco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerTipoDeCimento.getSelectedItem().toString().equals("Traço para 1 saco (50kg) de cimento em volume") ||
                    spinnerTipoDeCimento.getSelectedItem().toString().equals("Traço para 1 saco (50kg) em padiolas")){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // pré-configuração de teste
        if (acao.equals("calcularNovoTraco")) {
            textInputFck.setText("20");
            textInputAbatimento.setText("70");
            textInputMassaEspecificaCimento.setText("3100");
            textInputModuloDeFinuraAreia.setText("2.4");
            textInputMassaEspecificaAreia.setText("2650");
            textInputMassaUnitariaAreia.setText("1450");
            textInputDiametroMaximoBrita.setText("25");
            textInputMassaEspecificaBrita.setText("2750");
            textInputMassaUnitariaCompBrita.setText("1550");
            textInputMassaUnitariaBrita.setText("1430");

        } else if (acao.equals("editarTracoSalvo")) {
            //Recuperar dosagem
            concreto = (Concreto) dados.getSerializable("concreto");
            cimento = (Cimento) dados.getSerializable("cimento");
            areia = (Areia) dados.getSerializable("areia");
            brita = (Brita) dados.getSerializable("brita");
            agua = (Agua) dados.getSerializable("agua");
            traco = (Traco) dados.getSerializable("traco");

            dosagem.concreto = concreto;
            dosagem.cimento = cimento;
            dosagem.areia = areia;
            dosagem.brita = brita;
            dosagem.agua = agua;
            dosagem.traco = traco;

            textInputFck.setText(dosagem.concreto.getFck().toString());
            textInputAbatimento.setText(dosagem.concreto.getAbatimento().toString());
            Double desvioPadrao = dosagem.concreto.getDesvioPadrao();
            int spinnerPosition = 0;
            if (desvioPadrao.equals(4.0)) {
                spinnerPosition = 0;
            } else if (desvioPadrao.equals(5.5)){
                spinnerPosition = 1;
            } else if (desvioPadrao.equals(7.0)){
                spinnerPosition = 2;
            }
            spinnerDesvioPadrao.setSelection(spinnerPosition);

            String tipoDeCimento = dosagem.cimento.getEspecificacoes();
            spinnerPosition = 0;
            if (tipoDeCimento.equals("CP29")) {
                spinnerPosition = 0;
            } else if (tipoDeCimento.equals("CP32")){
                spinnerPosition = 1;
            } else if (tipoDeCimento.equals("CP35")){
                spinnerPosition = 2;
            } else if (tipoDeCimento.equals("CP38")){
                spinnerPosition = 3;
            } else if (tipoDeCimento.equals("CP41")){
                spinnerPosition = 4;
            } else if (tipoDeCimento.equals("CP44")){
                spinnerPosition = 5;
            } else if (tipoDeCimento.equals("CP47")){
                spinnerPosition = 6;
            } else if (tipoDeCimento.equals("CP50")){
                spinnerPosition = 7;
            }
            spinnerTipoDeCimento.setSelection(spinnerPosition);
            textInputMassaEspecificaCimento.setText(dosagem.cimento.getMassaEspecifica().toString());
            textInputModuloDeFinuraAreia.setText(dosagem.areia.getModuloDefinura().toString());
            textInputMassaEspecificaAreia.setText(dosagem.areia.getMassaEspecifica().toString());
            textInputMassaUnitariaAreia.setText(dosagem.areia.getMassaUnitaria().toString());
            textInputDiametroMaximoBrita.setText(dosagem.brita.getDiametroMaximo().toString());
            textInputMassaEspecificaBrita.setText(dosagem.brita.getMassaEspecifica().toString());
            textInputMassaUnitariaCompBrita.setText(dosagem.brita.getMassaUnitariaComp().toString());
            textInputMassaUnitariaBrita.setText(dosagem.brita.getMassaUnitaria().toString());

            String tipoDeTraco = dosagem.traco.getTipoDeTraco();
            spinnerPosition = 0;
            if (tipoDeTraco.equals("Traço para 1 m³ de concreto")) {
                spinnerPosition = 0;
            } else if (tipoDeTraco.equals("Traço unitário em massa")){
                spinnerPosition = 1;
            } else if (tipoDeTraco.equals("Traço para 1 saco (50kg) de cimento em massa")){
                spinnerPosition = 2;
            } else if (tipoDeTraco.equals("Traço para 1 saco (50kg) de cimento em volume")){
                spinnerPosition = 3;
            } else if (tipoDeTraco.equals("Traço para 1 saco (50kg) de cimento em padiolas")){
                spinnerPosition = 4;
            }
            spinnerTipoDeTraco.setSelection(spinnerPosition);


        }

        buttonCalcularTraco = findViewById(R.id.buttonCalcularTraco);
        buttonCalcularTraco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (
                    (textInputFck.getText().toString() == null || textInputFck.getText().toString().equals("")) ||
                    (textInputAbatimento.getText().toString() == null || textInputAbatimento.getText().toString().equals("")) ||

                    (textInputMassaEspecificaCimento.getText().toString() == null || textInputMassaEspecificaCimento.getText().toString().equals("")) ||

                    (textInputModuloDeFinuraAreia.getText().toString() == null || textInputModuloDeFinuraAreia.getText().toString().equals("")) ||
                    (textInputMassaEspecificaAreia.getText().toString() == null || textInputMassaEspecificaAreia.getText().toString().equals("")) ||
                    (textInputMassaUnitariaAreia.getText().toString() == null || textInputMassaUnitariaAreia.getText().toString().equals("")) ||

                    (textInputDiametroMaximoBrita.getText().toString() == null || textInputDiametroMaximoBrita.getText().toString().equals("")) ||
                    (textInputMassaEspecificaBrita.getText().toString() == null || textInputMassaEspecificaBrita.getText().toString().equals("")) ||
                    (textInputMassaUnitariaCompBrita.getText().toString() == null || textInputMassaUnitariaCompBrita.getText().toString().equals("")) ||
                    (textInputMassaUnitariaBrita.getText().toString() == null || textInputMassaUnitariaBrita.getText().toString().equals(""))
                ) {
                    Toast.makeText(InserirDadosActivity.this, "Por favor preencha todos os campos", Toast.LENGTH_LONG).show();
                } else if (acao.equals("calcularNovoTraco")) {

                    inserirDados();
                    dosagem.inserirInformacoesIncicias(concreto, cimento, areia, brita, agua);
                    dosagem.traco = traco;

                    // Criar Intent
                    Intent intentAbrirResultadosActivity = new Intent(getApplicationContext(), ResultadosActivity.class);

                    // Passar dados
                    intentAbrirResultadosActivity.putExtra("dosagem", dosagem);
                    intentAbrirResultadosActivity.putExtra("ação", "calcularNovoTraco");

                    // Iniciar a Activity dos Resultados
                    startActivityForResult(intentAbrirResultadosActivity, codigosDeActivity.inserirDadosActivity);
                } else if (acao.equals("editarTracoSalvo")) {
                    inserirDados();
                    dosagem.inserirInformacoesIncicias(concreto, cimento, areia, brita, agua);
                    dosagem.traco = traco;

                    // Criar Intent
                    Intent intentAbrirResultadosActivity = new Intent(getApplicationContext(), ResultadosActivity.class);

                    // Passar dados
                    intentAbrirResultadosActivity.putExtra("dosagem", dosagem);
                    intentAbrirResultadosActivity.putExtra("ação", "abrirTracoSalvo");
                    intentAbrirResultadosActivity.putExtra("position", position);

                    // Iniciar a Activity dos Resultados
                    startActivity(intentAbrirResultadosActivity);
                    setResult(codigosDeActivity.resultadosActivity);
                    finish();
                }
            }
        });
    }

    public void inserirDados() {
        concreto.setDesvioPadrao(Double.parseDouble(spinnerDesvioPadrao.getSelectedItem().toString()));
        concreto.setFck(Double.parseDouble(textInputFck.getText().toString()));
        concreto.setAbatimento(Double.parseDouble(textInputAbatimento.getText().toString()));

        cimento.setEspecificacoes(spinnerTipoDeCimento.getSelectedItem().toString());
        cimento.setMassaEspecifica(Double.parseDouble(textInputMassaEspecificaCimento.getText().toString()));

        areia.setModuloDefinura(Double.parseDouble(textInputModuloDeFinuraAreia.getText().toString()));
        areia.setMassaEspecifica(Double.parseDouble(textInputMassaEspecificaAreia.getText().toString()));
        areia.setMassaUnitaria(Double.parseDouble(textInputMassaUnitariaAreia.getText().toString()));

        brita.setDiametroMaximo(Double.parseDouble(textInputDiametroMaximoBrita.getText().toString()));
        brita.setMassaEspecifica(Double.parseDouble(textInputMassaEspecificaBrita.getText().toString()));
        brita.setMassaUnitariaComp(Double.parseDouble(textInputMassaUnitariaCompBrita.getText().toString()));
        brita.setMassaUnitaria(Double.parseDouble(textInputMassaUnitariaBrita.getText().toString()));

        traco.setTipoDeTraco(spinnerTipoDeTraco.getSelectedItem().toString());
    }

    private void carregarDadosSpinner() {

        // Spinner Desvio Padrão
        String[] desviosPadrao = new String[]{
                "4.0", "5.5", "7.0"
        };
        ArrayAdapter<String> arrayAdapterDesviosPadrao = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                desviosPadrao
        );
        arrayAdapterDesviosPadrao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDesvioPadrao.setAdapter(arrayAdapterDesviosPadrao);

        // Spinner Tipo de Cimento
        String[] tiposDeCimento = new String[]{
                "CP29", "CP32", "CP35", "CP38", "CP41", "CP44", "CP47", "CP50"
        };
        ArrayAdapter<String> arrayAdapterTiposDeCimento = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                tiposDeCimento
        );
        arrayAdapterTiposDeCimento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoDeCimento.setAdapter(arrayAdapterTiposDeCimento);

        // Spinner Tipo de Traço
        String[] tiposDeTraco = new String[]{
                "Traço para 1 m³ de concreto", "Traço unitário em massa",
                "Traço para 1 saco (50kg) de cimento em massa", "Traço para 1 saco (50kg) de cimento em volume",
                "Traço para 1 saco (50kg) de cimento em padiolas"
        };
        ArrayAdapter<String> arrayAdapterTiposDeTraco = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                tiposDeTraco
        );
        arrayAdapterTiposDeTraco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoDeTraco.setAdapter(arrayAdapterTiposDeTraco);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == codigosDeActivity.inserirDadosActivity) {
            if (resultCode == codigosDeActivity.inserirDadosActivity) {
                //Intent i = getIntent();
                //overridePendingTransition(0,0);
                //i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
            }
        }

    }
}

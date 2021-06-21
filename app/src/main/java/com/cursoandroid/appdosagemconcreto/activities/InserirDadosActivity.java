package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.classesdecalculo.Dosagem;
import com.cursoandroid.appdosagemconcreto.materiais.Agua;
import com.cursoandroid.appdosagemconcreto.materiais.Areia;
import com.cursoandroid.appdosagemconcreto.materiais.Brita;
import com.cursoandroid.appdosagemconcreto.materiais.Cimento;
import com.cursoandroid.appdosagemconcreto.materiais.Concreto;
import com.google.android.material.textfield.TextInputEditText;

public class InserirDadosActivity extends AppCompatActivity {

    // Elementos da interface
    private TextInputEditText textInputFck, textInputAbatimento, textInputMassaEspecificaCimento,
            textInputModuloDeFinuraAreia, textInputMassaEspecificaAreia, textInputMassaUnitariaAreia,
            textInputDiametroMaximoBrita, textInputMassaEspecificaBrita, textInputMassaUnitariaCompBrita, textInputMassaUnitariaBrita;
    private Spinner spinnerTipoDeCimento, spinnerDesvioPadrao;
    private Button buttonCalcularTraco;

    // Materiais
    private Cimento cimento = new Cimento();
    private String tipoCimento;
    private Areia areia = new Areia();
    private Brita brita = new Brita();
    private Agua agua = new Agua();
    private Concreto concreto = new Concreto();

    // Classes de calculo
    private Dosagem dosagem = new Dosagem();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_dados);

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
        carregarDadosSpinner();

        // pré-configuração de teste
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
                } else {

                    inserirDados();
                    dosagem.inserirInformacoesIncicias(concreto, cimento, areia, brita, agua);

                    // Criar Intent
                    Intent intentAbrirResultadosActivity = new Intent(getApplicationContext(), ResultadosActivity.class);

                    // Passar dados
                    intentAbrirResultadosActivity.putExtra("dosagem", dosagem);
                    intentAbrirResultadosActivity.putExtra("ação", "calcularNovoTraco");

                    // Iniciar a Activity dos Resultados
                    startActivity(intentAbrirResultadosActivity);
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
    }

    private void carregarDadosSpinner() {

        String[] tiposDeCimento = new String[]{
                "CP29", "CP32", "CP35", "CP38", "CP41", "CP44", "CP47", "CP50"
        };
        ArrayAdapter<String> arrayAdapterTiposDeCimento = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                tiposDeCimento
        );
        arrayAdapterTiposDeCimento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoDeCimento.setAdapter(arrayAdapterTiposDeCimento);

        String[] desviosPadrao = new String[]{
                "4.0", "5.5", "7.0"
        };
        ArrayAdapter<String> arrayAdapterDesviosPadrao = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                desviosPadrao
        );
        arrayAdapterDesviosPadrao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDesvioPadrao.setAdapter(arrayAdapterDesviosPadrao);

    }

}

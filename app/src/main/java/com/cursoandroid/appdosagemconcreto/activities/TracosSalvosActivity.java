package com.cursoandroid.appdosagemconcreto.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.adapter.TracoAdapter;
import com.cursoandroid.appdosagemconcreto.classesdecalculo.Dosagem;
import com.cursoandroid.appdosagemconcreto.helper.RecyclerItemClickListener;
import com.cursoandroid.appdosagemconcreto.helper.TracoDAO;
import com.cursoandroid.appdosagemconcreto.materiais.Agua;
import com.cursoandroid.appdosagemconcreto.materiais.Areia;
import com.cursoandroid.appdosagemconcreto.materiais.Brita;
import com.cursoandroid.appdosagemconcreto.materiais.Cimento;
import com.cursoandroid.appdosagemconcreto.materiais.Concreto;
import com.cursoandroid.appdosagemconcreto.model.ItemTracoSalvo;

import java.util.ArrayList;
import java.util.List;

public class TracosSalvosActivity extends AppCompatActivity {
    // Elementos de Interface
    private RecyclerView recyclerTracosSalvos;
    private List<Dosagem> listaTracos = new ArrayList<>();

    // Materiais
    private Cimento cimento = new Cimento();
    private Areia areia = new Areia();
    private Brita brita = new Brita();
    private Agua agua = new Agua();
    private Concreto concreto = new Concreto();

    // Classes de cálculo
    private Dosagem dosagemSelecionada = new Dosagem();

    // Adapter
    private TracoAdapter tracoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracos_salvos);

        // Configurar RecyclerView
        recyclerTracosSalvos = findViewById(R.id.recyclerTracosSalvos);

        // Adicionar evento de clique
        recyclerTracosSalvos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerTracosSalvos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                try {
                                    Intent intentAbrirResultadosActivity = new Intent(getApplicationContext(), ResultadosActivity.class);

                                    // Passar dados
                                    dosagemSelecionada = listaTracos.get(position);

                                    intentAbrirResultadosActivity.putExtra("dosagem", dosagemSelecionada);
                                    intentAbrirResultadosActivity.putExtra("ação", "abrirTracoSalvo");
                                    intentAbrirResultadosActivity.putExtra("position", position);

                                    // Iniciar a Activity dos Resultados
                                    startActivity(intentAbrirResultadosActivity);
                                    //finish();
                                } catch (Exception e) {
                                    Toast.makeText(TracosSalvosActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    Log.i("INFOERRO", e.getMessage());
                                }
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                // Recuperar traço para deletar
                                dosagemSelecionada = listaTracos.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(TracosSalvosActivity.this);

                                // Configurar título e mensagem
                                dialog.setTitle("Deseja deletar o traço " + dosagemSelecionada.traco.getNomeDoTraco() + "?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        TracoDAO tracoDAO = new TracoDAO(getApplicationContext());
                                        if ( tracoDAO.deletar(dosagemSelecionada) ) {
                                            carregarTracosSalvos();
                                            Toast.makeText(TracosSalvosActivity.this, "Sucesso ao excluir traço!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(TracosSalvosActivity.this, "Erro ao excluir traço!", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                // Exibir dialog
                                dialog.create();
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

    }

    public void carregarTracosSalvos() {

        //Listar traços
        TracoDAO tracoDAO = new TracoDAO( getApplicationContext() );
        listaTracos = tracoDAO.listar();

        //Configurar um adapter
        tracoAdapter = new TracoAdapter( listaTracos );

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerTracosSalvos.setLayoutManager( layoutManager );
        recyclerTracosSalvos.setHasFixedSize(true);
        recyclerTracosSalvos.addItemDecoration(new DividerItemDecoration( getApplicationContext(), LinearLayout.VERTICAL));
        recyclerTracosSalvos.setAdapter(tracoAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarTracosSalvos();

    }
}

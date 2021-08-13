package com.cursoandroid.appdosagemconcreto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.model.ItemCimentoSalvo;

import java.util.ArrayList;
import java.util.List;

public class CimentoAdapter extends RecyclerView.Adapter<CimentoAdapter.MyViewHolder>{

    private List<ItemCimentoSalvo> listaDeCimentosSalvos;

    public CimentoAdapter(ArrayList<ItemCimentoSalvo> lista) {

        this.listaDeCimentosSalvos = lista;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.lista_cimentos_salvos_adapter, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ItemCimentoSalvo itemCimentoSalvo = listaDeCimentosSalvos.get(position);
        holder.nomeDoCimento.setText(itemCimentoSalvo.getNomeDoCimento());
        holder.tempoDeCura.setText("Cura: " + itemCimentoSalvo.getTempoDeCura() + " dias");
        holder.qtdeDePontos.setText(itemCimentoSalvo.getQtdeDePontos() + " amostras");

    }

    @Override
    public int getItemCount() {
        return this.listaDeCimentosSalvos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeDoCimento, tempoDeCura, data, qtdeDePontos;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeDoCimento = itemView.findViewById(R.id.textViewNomeDoCimento);
            tempoDeCura = itemView.findViewById(R.id.textViewTempoDeCura);
            data = itemView.findViewById(R.id.textViewData);
            qtdeDePontos = itemView.findViewById(R.id.textViewQtdeDePontos);

        }
    }

}

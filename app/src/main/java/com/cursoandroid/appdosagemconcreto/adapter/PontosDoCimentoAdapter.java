package com.cursoandroid.appdosagemconcreto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.model.ItemPontoCimento;

import java.util.ArrayList;
import java.util.List;

public class PontosDoCimentoAdapter extends RecyclerView.Adapter<PontosDoCimentoAdapter.MyViewHolder> {

    private List<ItemPontoCimento> listaPontosCimento;

    public PontosDoCimentoAdapter(ArrayList<ItemPontoCimento> lista) {

        this.listaPontosCimento = lista;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.lista_pontos_cimento_adapter, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ItemPontoCimento itemPontoCimento = listaPontosCimento.get(position);
        holder.dadosDoPonto.setText(itemPontoCimento.getNomeDoPonto());

    }

    @Override
    public int getItemCount() {
        return this.listaPontosCimento.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dadosDoPonto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dadosDoPonto = itemView.findViewById(R.id.textViewItemPontoCimento);

        }
    }
}

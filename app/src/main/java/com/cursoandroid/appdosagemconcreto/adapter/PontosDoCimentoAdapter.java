package com.cursoandroid.appdosagemconcreto.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

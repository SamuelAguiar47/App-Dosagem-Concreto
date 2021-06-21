package com.cursoandroid.appdosagemconcreto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.appdosagemconcreto.R;
import com.cursoandroid.appdosagemconcreto.classesdecalculo.Dosagem;
import com.cursoandroid.appdosagemconcreto.model.ItemTracoSalvo;

import java.util.ArrayList;
import java.util.List;

public class TracoAdapter extends RecyclerView.Adapter<TracoAdapter.MyViewHolder> {

    private List<Dosagem> listaTracos = new ArrayList<>();

    public TracoAdapter(List<Dosagem> lista) {

        this.listaTracos = lista;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemTraco = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.lista_traco_adapter, parent, false);

        return new MyViewHolder(itemTraco);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Dosagem itemTracoSalvo = listaTracos.get(position);
        holder.rotuloDoTraco.setText( itemTracoSalvo.traco.getNomeDoTraco() );
        holder.dataDoTraco.setText( itemTracoSalvo.traco.getDataDoTraco() );
        holder.tipoDeTraco.setText( itemTracoSalvo.traco.getTipoDeTraco() );
        holder.tracoExibido.setText( itemTracoSalvo.traco.getTracoExibido() );

    }

    @Override
    public int getItemCount() {
        return this.listaTracos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView rotuloDoTraco;
        TextView dataDoTraco;
        TextView tipoDeTraco;
        TextView tracoExibido;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rotuloDoTraco = itemView.findViewById(R.id.textTraco);
            dataDoTraco = itemView.findViewById(R.id.textViewDataDoTraco);
            tipoDeTraco = itemView.findViewById(R.id.textViewTipoDeTraco);
            tracoExibido = itemView.findViewById(R.id.textViewTracoExibido);

        }
    }

}

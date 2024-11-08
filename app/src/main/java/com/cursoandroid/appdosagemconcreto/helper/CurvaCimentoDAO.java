package com.cursoandroid.appdosagemconcreto.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.cursoandroid.appdosagemconcreto.model.ItemPontoCimento;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CurvaCimentoDAO implements ICurvaCimentoDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    // Classes de arredondamento e formatação
    private DecimalFormat arred0 = new DecimalFormat("####");
    private DecimalFormat arred1X = new DecimalFormat("##0.#");
    private DecimalFormat arred1 = new DecimalFormat("##0.0");
    private DecimalFormat arred2 = new DecimalFormat("##0.00");
    private DecimalFormat arred2x = new DecimalFormat("##0.##");
    private DecimalFormat arred3 = new DecimalFormat("##0.000");
    private DecimalFormat arred3x = new DecimalFormat("##0.###");

    public CurvaCimentoDAO(Context context) {
        //nomeDaTabela = "tabela" + nomeDoCimento.replace(" ", "") + "para" + tempoDeCura + "dias";
        DbCimentoCurva dbCimentoCurva = new DbCimentoCurva(context);
        escreve = dbCimentoCurva.getWritableDatabase();
        le = dbCimentoCurva.getReadableDatabase();
    }

    @Override
    public boolean salvar(ItemPontoCimento itemPontoCimento) {

        ContentValues cv = new ContentValues();
        cv.put("ac", itemPontoCimento.getValorDeAC());
        cv.put("fcj", itemPontoCimento.getValorDeFcj());
        cv.put("nomeCimento", itemPontoCimento.getNomeDoCimento());

        try {
            escreve.insert(DbCimentoCurva.TABELA_DADOS_CIMENTO, null, cv);
            Log.i("INFO", "Sucesso ao salvar ponto do cimento na tabela definitva.");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao salvar ponto do cimento na tabela definitva.");
        }

        return true;
    }

    @Override
    public boolean atualizar(ItemPontoCimento itemPontoCimento) {
        return false;
    }

    @Override
    public boolean deletar(ItemPontoCimento itemPontoCimento) {

        try {
            String[] args = {itemPontoCimento.getId().toString()};
            escreve.delete(DbCimentoCurva.TABELA_DADOS_CIMENTO, "id=?", args);
            Log.i("INFO","Sucesso ao deletar ponto.");
        } catch (Exception e) {
            Log.i("INFO","Erro ao deletar ponto.");
        }

        return true;
    }

    public boolean deletarLista(List<ItemPontoCimento> listaPontosCimento) {

        try {
            int cont = 0;
            while (cont < listaPontosCimento.size()) {
                ItemPontoCimento itemPontoCimento = listaPontosCimento.get(cont);
                String[] args = {itemPontoCimento.getId().toString()};
                escreve.delete(DbCimentoCurva.TABELA_DADOS_CIMENTO, "id=?", args);
                cont += 1;
            }
            Log.i("INFO", "Sucesso ao deletar lista de pontos de amostras do cimento!");
        } catch (Exception e) {
            Log.i("INFO", "Erro ao deletar lista de pontos de amostras do cimento!");
            return false;
        }
        return true;
    };

    @Override
    public List<ItemPontoCimento> listar(String nomeCimentoSelecionado) {

        List<ItemPontoCimento> listaPontosCimento = new ArrayList<>();

        String sql = "SELECT * FROM " + DbCimentoCurva.TABELA_DADOS_CIMENTO + " WHERE nomeCimento = '" + nomeCimentoSelecionado + "' ;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ) {

            ItemPontoCimento itemPontoCimento = new ItemPontoCimento();

            Long id = c.getLong( c.getColumnIndex("id") );
            Double valorDeAC = c.getDouble( c.getColumnIndex("ac") );
            Double valorDeFcj = c.getDouble( c.getColumnIndex("fcj") );

            itemPontoCimento.setId(id);
            itemPontoCimento.setValorDeAC(valorDeAC);
            itemPontoCimento.setValorDeFcj(valorDeFcj);
            itemPontoCimento.setNomeDoPonto("a/c: " + arred2.format(valorDeAC) + "   ➔   Fcj: " + arred2x.format(valorDeFcj) + " MPa");

            listaPontosCimento.add(itemPontoCimento);
        }

        return listaPontosCimento;
    }

}

package com.cursoandroid.appdosagemconcreto.helper;

import com.cursoandroid.appdosagemconcreto.model.ItemPontoCimento;

import java.util.List;

public interface ICurvaCimentoDAO {

    public boolean salvar(ItemPontoCimento itemPontoCimento);
    public boolean atualizar(ItemPontoCimento itemPontoCimento);
    public boolean deletar(ItemPontoCimento itemPontoCimento);
    public List<ItemPontoCimento> listar();

}

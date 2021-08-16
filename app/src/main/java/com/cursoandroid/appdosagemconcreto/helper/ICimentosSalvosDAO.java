package com.cursoandroid.appdosagemconcreto.helper;

import com.cursoandroid.appdosagemconcreto.model.ItemCimentoSalvo;

import java.util.List;

public interface ICimentosSalvosDAO {

    public boolean salvar(ItemCimentoSalvo itemCimentoSalvo);
    public boolean atualizar(ItemCimentoSalvo itemCimentoSalvo);
    public boolean deletar(ItemCimentoSalvo itemCimentoSalvo);
    public List<ItemCimentoSalvo> listar();

}

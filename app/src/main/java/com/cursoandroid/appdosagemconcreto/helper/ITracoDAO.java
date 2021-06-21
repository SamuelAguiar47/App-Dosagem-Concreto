package com.cursoandroid.appdosagemconcreto.helper;

import com.cursoandroid.appdosagemconcreto.classesdecalculo.Dosagem;

import java.util.List;

public interface ITracoDAO {

    public boolean salvar(Dosagem dosagem);
    public boolean atualizar(Dosagem dosagem);
    public boolean deletar(Dosagem dosagem);
    public List<Dosagem> listar();

}

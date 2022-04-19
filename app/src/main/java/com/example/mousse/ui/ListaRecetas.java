package com.example.mousse.ui;

import java.util.ArrayList;

public class ListaRecetas {
    private ArrayList<Receta> listaRecetas;

    public ListaRecetas(ArrayList<Receta> listaRecetas) {
        this.listaRecetas = listaRecetas;
    }

    public ArrayList<Receta> getListaRecetas() {
        return listaRecetas;
    }

    public void setListaRecetas(ArrayList<Receta> listaRecetas) {
        this.listaRecetas = listaRecetas;
    }

    public void addReceta(Receta receta){
        this.listaRecetas.add(receta);
    }
}

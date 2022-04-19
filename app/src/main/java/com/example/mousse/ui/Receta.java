package com.example.mousse.ui;

import java.util.ArrayList;

public class Receta {
    private ArrayList<String> pasos;
    private String nombre;
    private ArrayList<String> ingredientes;
    private ArrayList<String> hashtags;
    private Usuario usuario;
    //foto

    public Receta(ArrayList<String> pasos, String nombre, ArrayList<String> ingredientes, ArrayList<String> hashtags, Usuario usuario) {
        this.pasos = pasos;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.hashtags = hashtags;
        this.usuario = usuario;
    }

    public ArrayList<String> getPasos() {
        return pasos;
    }

    public void setPasos(ArrayList<String> pasos) {
        this.pasos = pasos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void addPaso(String paso){
        this.pasos.add(paso);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<String> hashtags) {
        this.hashtags = hashtags;
    }
}

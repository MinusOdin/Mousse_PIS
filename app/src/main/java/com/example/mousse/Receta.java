package com.example.mousse;

import android.util.Log;

import java.util.ArrayList;

public class Receta {
    private ArrayList<String> pasos;
    private String nombre;
    private ArrayList<String> ingredientes;
    private ArrayList<String> hashtags;
    private String emailUsuario;
    private String descripcion;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    //foto

    public Receta(String nombre,  String descripcion, ArrayList<String> hashtags, ArrayList<String> ingredientes) {
        //this.pasos = pasos;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.hashtags = hashtags;
        this.descripcion = descripcion;
    }

    public Receta(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getEmailUsuario() { return emailUsuario; }

    public void setEmailUsuario(String idusuario) {
        this.emailUsuario = idusuario;
    }

    public String getDescripcion(){ return descripcion; }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<String> hashtags) {
        this.hashtags = hashtags;
    }

    public void saveReceta() {

        Log.d("saveReceta", "saveReceta-> saveDocument");
        adapter.saveReceta(this.nombre, this.descripcion, this.hashtags, this.ingredientes
        );
    }
}

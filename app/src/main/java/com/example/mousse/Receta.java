package com.example.mousse;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Receta implements Parcelable {
    private ArrayList<String> pasos;
    private String nombre;
    private ArrayList<String> ingredientes;
    private ArrayList<String> hashtags;
    private String emailUsuario;
    private String descripcion;
    private String id;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    //foto

    public Receta(String nombre, String descripcion, String emailUsuario, ArrayList<String> hashtags, ArrayList<String> ingredientes, ArrayList<String> pasos, String id) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.emailUsuario = emailUsuario;
        this.hashtags = hashtags;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.id = id;

    }

    private Receta(Parcel in) {
        this.nombre = in.readString();
        this.descripcion = in.readString();
        this.emailUsuario = in.readString();
        this.hashtags = in.readArrayList(null);
        this.ingredientes = in.readArrayList(null);
        this.pasos = in.readArrayList(null);
        this.id = in.readString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Receta> CREATOR
            = new Parcelable.Creator<Receta>() {
        public Receta createFromParcel(Parcel in) {
            return new Receta(in);
        }

        public Receta[] newArray(int size) {
            return new Receta[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(descripcion);
        parcel.writeString(emailUsuario);
        parcel.writeList(hashtags);
        parcel.writeList(ingredientes);
        parcel.writeList(pasos);
        parcel.writeString(id);
    }
}

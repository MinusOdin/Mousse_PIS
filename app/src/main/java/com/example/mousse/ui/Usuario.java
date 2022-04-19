package com.example.mousse.ui;

public class Usuario {
    private String nombre;
    private String mail;
    private String contraseña;
    private ListaRecetas recetasLike;
    private ListaRecetas recetasPublicas;
    private ListaRecetas recetasEditar;
    private ListaRecetas recetasGuardadas;
    private ListaRecetas recetasHechas;

    public Usuario(String nombre, String mail, String contraseña) {
        this.nombre = nombre;
        this.mail = mail;
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public String getContraseña() {
        return contraseña;
    }

    public ListaRecetas getRecetasLike() {
        return recetasLike;
    }

    public void setRecetasLike(ListaRecetas recetasLike) {
        this.recetasLike = recetasLike;
    }

    public ListaRecetas getRecetasPublicas() {
        return recetasPublicas;
    }

    public void setRecetasPublicas(ListaRecetas recetasPublicas) {
        this.recetasPublicas = recetasPublicas;
    }

    public ListaRecetas getRecetasEditar() {
        return recetasEditar;
    }

    public void setRecetasEditar(ListaRecetas recetasEditar) {
        this.recetasEditar = recetasEditar;
    }

    public ListaRecetas getRecetasGuardadas() {
        return recetasGuardadas;
    }

    public void setRecetasGuardadas(ListaRecetas recetasGuardadas) {
        this.recetasGuardadas = recetasGuardadas;
    }

    public ListaRecetas getRecetasHechas() {
        return recetasHechas;
    }

    public void setRecetasHechas(ListaRecetas recetasHechas) {
        this.recetasHechas = recetasHechas;
    }
}

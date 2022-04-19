package com.example.mousse;

public class Usuario {
    private String nombre;
    private String mail;
    private String contraseña;

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
}

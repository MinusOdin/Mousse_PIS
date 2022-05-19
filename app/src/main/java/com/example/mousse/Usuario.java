package com.example.mousse;

import android.util.Log;

public class Usuario {
    private String nombre;
    private String email;
    private String contraseña;
    public static Usuario currentUser;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public Usuario(String email, String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
    }

    public static void setCurrentUser(String email, String password) {
        currentUser = new Usuario(email, password);
    }

    public static String getCurrentUserEmail() {
        return currentUser.getEmail();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void saveUsuario() {
        Log.d("saveUsuario", "saveUsuario-> saveUser");
        adapter.saveUser(this.email, this.contraseña);
    }
}

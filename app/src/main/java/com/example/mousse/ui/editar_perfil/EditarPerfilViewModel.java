package com.example.mousse.ui.editar_perfil;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mousse.DatabaseAdapter;
import com.example.mousse.Receta;
import com.example.mousse.Usuario;

import java.util.ArrayList;

public class EditarPerfilViewModel extends ViewModel implements DatabaseAdapter.vmInterface{

    private final MutableLiveData<ArrayList<Receta>> mRecetas;
    private final MutableLiveData<ArrayList<Usuario>> mUsuarios;
    private final MutableLiveData<String> mToast;
    private final DatabaseAdapter da;

    public EditarPerfilViewModel() {
        mRecetas = new MutableLiveData<>();
        mUsuarios = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        da = new DatabaseAdapter(this);
    }

    public void guardarDatos(String nombre, Uri foto){
        da.saveDataEditPerfin(nombre, foto);
    }

    public void guardarNombre(String nombre){
        da.saveNameEditPerfil(nombre);
    }

    public LiveData<String> getToast(){
        return mToast;
    }

    @Override
    public void setCollectionPublicadas(ArrayList<Receta> recetas) {

    }

    @Override
    public void setCollectionHechas(ArrayList<Receta> recetas) {

    }

    @Override
    public void setCollectionLikes(ArrayList<Receta> recetas) {

    }

    @Override
    public void setCollectionFavs(ArrayList<Receta> recetas) {

    }

    @Override
    public void setToast(String t) {
        mToast.setValue(t);
    }

    @Override
    public void setSuccesfull(boolean succesfull) {

    }
    void getNombre(String user){
        da.getNombre(user);
    }
}

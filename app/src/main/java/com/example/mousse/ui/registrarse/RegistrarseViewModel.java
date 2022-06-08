package com.example.mousse.ui.registrarse;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mousse.DatabaseAdapter;
import com.example.mousse.Receta;
import com.example.mousse.Usuario;

import java.util.ArrayList;

public class RegistrarseViewModel extends ViewModel implements DatabaseAdapter.vmInterface{

    private final MutableLiveData<ArrayList<Receta>> mRecetas;
    private final MutableLiveData<ArrayList<Usuario>> mUsuarios;
    private final MutableLiveData<String> mToast;
    private final MutableLiveData<Boolean> mSuccesfull;

    private final DatabaseAdapter da;

    public RegistrarseViewModel() {
        mRecetas = new MutableLiveData<>();
        mUsuarios = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        mSuccesfull = new MutableLiveData<>();
        da = new DatabaseAdapter(this);
    }

    public void registrarUsuario(String email, String contraseña, Uri foto) {
            da.saveUser(email, contraseña, foto);

    }

    public LiveData<String> getToast(){
        return mToast;
    }

    public LiveData<Boolean> getSuccesfull(){
        return mSuccesfull;
    }

    @Override
    public void setCollectionPublicadas(ArrayList<Receta> recetas) {
        mRecetas.setValue(recetas);
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
        mSuccesfull.setValue(succesfull);
        Log.d("setRegistrat", "Si funciona");
    }

}


package com.example.mousse.ui.otro_perfil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mousse.DatabaseAdapter;
import com.example.mousse.Receta;

import java.util.ArrayList;

public class OtroPerfilViewModel extends ViewModel implements DatabaseAdapter.vmInterface{

    private final MutableLiveData<ArrayList<Receta>> mRecetasPublicadas;
    private final MutableLiveData<ArrayList<Receta>> mRecetasHechas;
    private final MutableLiveData<String> mToast;
    private final DatabaseAdapter da;

    public OtroPerfilViewModel() {
        mRecetasPublicadas = new MutableLiveData<>();

        mRecetasHechas = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        da = new DatabaseAdapter(this);
    }
    public void init(String email){
        da.getCollectionByUserHecho(email);
        da.getCollectionByUser(email);
    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<Receta>> getRecetasPublicadas(){
        return mRecetasPublicadas;
    }

    public LiveData<ArrayList<Receta>> getRecetasHechas(){
        return mRecetasHechas;
    }

    public Receta getReceta(int idx){
        return mRecetasPublicadas.getValue().get(idx);
    }

    public LiveData<String> getToast(){
        return mToast;
    }

    //communicates user inputs and updates the result in the viewModel
    @Override
    public void setCollectionPublicadas(ArrayList<Receta> recetas) {
        mRecetasPublicadas.setValue(recetas);
    }

    @Override
    public void setCollectionHechas(ArrayList<Receta> recetas) {
        mRecetasHechas.setValue(recetas);
    }

    @Override
    public void setCollectionLikes(ArrayList<Receta> recetas) {

    }

    @Override
    public void setCollectionFavs(ArrayList<Receta> recetas) {

    }

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
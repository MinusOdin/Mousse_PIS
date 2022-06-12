package com.example.mousse.ui.crear_receta;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mousse.DatabaseAdapter;
import com.example.mousse.Receta;

import java.util.ArrayList;

public class CrearRecetaViewModel extends ViewModel implements DatabaseAdapter.vmInterface {

    private final MutableLiveData<ArrayList<Receta>> mRecetas;
    private final MutableLiveData<String> mToast;
    private final MutableLiveData<Boolean> mSuccesfull;
    DatabaseAdapter da;

    public CrearRecetaViewModel() {
        mRecetas = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        mSuccesfull = new MutableLiveData<>();
        da = new DatabaseAdapter(this);
    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<Receta>> getRecetas(){
        return mRecetas;
    }

    public Receta getReceta(int idx){
        return mRecetas.getValue().get(idx);
    }

    public void addReceta(Boolean publicada, String name, String descripcion, ArrayList<String> hashtags, ArrayList<String> ingredients, ArrayList<String> pasos, Uri foto){
            da.saveReceta(publicada, name, descripcion, hashtags, ingredients, pasos, foto);
    }

    public LiveData<String> getToast(){
        return mToast;
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

    public void setToast(String t) {
        mToast.setValue(t);
    }

    @Override
    public void setSuccesfull(boolean succesfull) {
        mSuccesfull.setValue(true);
    }
    public LiveData<Boolean> getSuccesfull() {
        return mSuccesfull;
    }
}
package com.example.mousse.ui.buscar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mousse.DatabaseAdapter;
import com.example.mousse.Receta;

import java.util.ArrayList;

public class BuscarViewModel extends ViewModel implements DatabaseAdapter.vmInterface {

    private final MutableLiveData<ArrayList<Receta>> mRecetas;
    private final MutableLiveData<String> mToast;
    private final DatabaseAdapter da;

    public BuscarViewModel() {
        mRecetas = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        da = new DatabaseAdapter(this);
    }

    public void init(String string){
        da.getCollectionSearch(string);
    }

    public LiveData<ArrayList<Receta>> getRecetas(){
        return mRecetas;
    }

    public Receta getReceta(int idx){
        return mRecetas.getValue().get(idx);
    }

    public LiveData<String> getToast(){
        return mToast;
    }


    @Override
    public void setCollection(ArrayList<Receta> recetas) {
        mRecetas.setValue(recetas);
    }

    @Override
    public void setToast(String t) {
        mToast.setValue(t);
    }

    @Override
    public void setSuccesfull(boolean succesfull) {

    }
}
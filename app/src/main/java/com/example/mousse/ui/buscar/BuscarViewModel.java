package com.example.mousse.ui.buscar;

import android.util.Log;

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
    private final MutableLiveData<Boolean> mSuccesfull;

    public BuscarViewModel() {
        mSuccesfull = new MutableLiveData<>();
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

    public LiveData<Boolean> getSuccesfull(){
        return mSuccesfull;
    }


    @Override
    public void setCollection(ArrayList<Receta> recetas) {
        mRecetas.setValue(recetas);
    }

    @Override
    public void setCollection2(ArrayList<Receta> recetas) {

    }

    @Override
    public void setToast(String t) {
        mToast.setValue(t);
    }

    @Override
    public void setSuccesfull(boolean succesfull) {
        mSuccesfull.setValue(succesfull);
    }
}
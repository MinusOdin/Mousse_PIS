package com.example.mousse.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mousse.DatabaseAdapter;
import com.example.mousse.Receta;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel implements DatabaseAdapter.vmInterface {

    private final MutableLiveData<ArrayList<Receta>> mRecetas;
    private final MutableLiveData<String> mToast;
    private final DatabaseAdapter da;

    public HomeViewModel() {
        mRecetas = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        da = new DatabaseAdapter(this);
        da.getCollection10();
    }
    public void init(){
        da.getCollectionByUser();
    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<Receta>> getRecetas(){
        return mRecetas;
    }

    public Receta getReceta(int idx){
        return mRecetas.getValue().get(idx);
    }

    public LiveData<String> getToast(){
        return mToast;
    }

    //communicates user inputs and updates the result in the viewModel
    @Override
    public void setCollection(ArrayList<Receta> recetas) {
        mRecetas.setValue(recetas);
    }

    public void setToast(String t) {
        mToast.setValue(t);
    }

    @Override
    public void setSuccesfull(boolean succesfull) {
    }

}
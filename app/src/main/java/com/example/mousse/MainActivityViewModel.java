package com.example.mousse;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;


public class MainActivityViewModel extends AndroidViewModel implements DatabaseAdapter.vmInterface{

    private final MutableLiveData<ArrayList<Receta>> mRecetas;
    private final MutableLiveData<String> mToast;


    public static final String TAG = "ViewModel";

    //Constructor
    public MainActivityViewModel(Application application){
        super(application);
        mRecetas = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        DatabaseAdapter da = new DatabaseAdapter(this);
    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<Receta>> getRecetas(){
        return mRecetas;
    }

    public Receta getReceta(int idx){
        return mRecetas.getValue().get(idx);
    }

    public void addReceta(String name, String descripcion){
        Receta receta = new Receta(name, descripcion);
        if (receta != null) {
            ArrayList<Receta> v = mRecetas.getValue();
            v.add(receta);
            //Inform observer.
            mRecetas.setValue(mRecetas.getValue());
            receta.saveReceta();
        }
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
}



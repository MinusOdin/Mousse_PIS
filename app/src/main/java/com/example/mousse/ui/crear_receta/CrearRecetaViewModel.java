package com.example.mousse.ui.crear_receta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mousse.DatabaseAdapter;
import com.example.mousse.Receta;
import com.example.mousse.Usuario;

import java.util.ArrayList;

public class CrearRecetaViewModel extends ViewModel implements DatabaseAdapter.vmInterface {

    private final MutableLiveData<ArrayList<Receta>> mRecetas;
    private final MutableLiveData<String> mToast;

    public CrearRecetaViewModel() {
        mRecetas = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        DatabaseAdapter da = new DatabaseAdapter(this);
        da.getCollectionByUser();
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
            mRecetas.getValue().add(receta);
            //Inform observer.
            mRecetas.setValue(mRecetas.getValue());
            receta.saveReceta();
        }
    }

    public LiveData<String> getToast(){
        return mToast;
    }

    @Override
    public void setCollection(ArrayList<Receta> recetas) {
        mRecetas.setValue(recetas);
    }

    public void setToast(String t) {
        mToast.setValue(t);
    }
}
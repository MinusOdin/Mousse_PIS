package com.example.mousse.ui.receta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mousse.DatabaseAdapter;
import com.example.mousse.Receta;

import java.util.ArrayList;

public class RecetaViewModel extends ViewModel implements DatabaseAdapter.vmInterface {

    private final DatabaseAdapter da;
    private final MutableLiveData<Boolean> fav;
    private final MutableLiveData<ArrayList<Receta>> mRecetas;
    private final MutableLiveData<String> mToast;


    public RecetaViewModel() {
        fav = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        da = new DatabaseAdapter(this);
        mRecetas = new MutableLiveData<>();
    }

    public void is_fav(){
        da.isfav();
    }

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

    public void guardar_fav(String id){
        da.guardar_receta_fav(id);
    }

    public LiveData<ArrayList<Receta>> getRecetas(){
        return mRecetas;
    }

    public LiveData<String> getToast(){
        return mToast;
    }

    public void no_fav(String id){
        da.eliminar_fav(id);
    }
}

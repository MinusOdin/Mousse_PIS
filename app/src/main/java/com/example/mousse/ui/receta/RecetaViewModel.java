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
    private final MutableLiveData<ArrayList<Receta>> mRecetas2;
    private final MutableLiveData<ArrayList<Receta>> mRecetas3;


    public RecetaViewModel() {
        fav = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        da = new DatabaseAdapter(this);
        mRecetas = new MutableLiveData<>();
        mRecetas2 = new MutableLiveData<>();
        mRecetas3 = new MutableLiveData<>();
    }

    public void is_fav(){
        da.isfav();
    }

    public void setCollection(ArrayList<Receta> recetas) {
        mRecetas.setValue(recetas);
    }

    @Override
    public void setCollection2(ArrayList<Receta> recetas) {
        mRecetas2.setValue(recetas);
    }

    @Override
    public void setCollection3(ArrayList<Receta> recetas) {
        mRecetas3.setValue(recetas);
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

    public void no_like(String id){
        da.eliminar_like(id);
    }

    public void guardar_like(String id){
        da.guardar_receta_like(id);
    }

    public LiveData<ArrayList<Receta>> getRecetas2(){
        return mRecetas2;
    }

    public void is_like(){
        da.isLike();
    }

    public void no_hecho(String id){
        da.eliminar_hecho(id);
    }

    public void guardar_hecho(String id){
        da.guardar_receta_hecho(id);
    }

    public LiveData<ArrayList<Receta>> getRecetas3(){
        return mRecetas3;
    }

    public void is_hecho(){
        da.isHecho();
    }
}

package com.example.mousse.ui.perfil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mousse.DatabaseAdapter;
import com.example.mousse.Receta;
import com.example.mousse.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PerfilViewModel extends ViewModel implements DatabaseAdapter.vmInterface {

    private final MutableLiveData<ArrayList<Receta>> mRecetasPublicadas;
    private final MutableLiveData<ArrayList<Receta>> mRecetasHechas;
    private final MutableLiveData<ArrayList<Receta>> mRecetasLikes;
    private final MutableLiveData<ArrayList<Receta>> mRecetasFavs;
    private final MutableLiveData<String> mToast;
    private final DatabaseAdapter da;


    public PerfilViewModel() {
        mRecetasPublicadas = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        mRecetasHechas = new MutableLiveData<>();
        mRecetasLikes = new MutableLiveData<>();
        mRecetasFavs = new MutableLiveData<>();
        da = new DatabaseAdapter(this);
    }

    public void init(){
        da.getCollectionByUserHecho(Usuario.getCurrentUserEmail());
        da.getCollectionByUserLikes(Usuario.getCurrentUserEmail());
        da.getCollectionByUserFav(Usuario.getCurrentUserEmail());
        da.getCollectionByUser(Usuario.getCurrentUserEmail());
    }
    public void setCollectionTabPublicadas(){
        da.getCollectionByUser(Usuario.getCurrentUserEmail());
    }

    public void setCollectionTabHecho(){
        da.getCollectionByUserHecho(Usuario.getCurrentUserEmail());
    }
    public void setCollectionTabLike(){
        da.getCollectionByUserLikes(Usuario.getCurrentUserEmail());
    }

    public void setCollectionTabFav(){
        da.getCollectionByUserFav(Usuario.getCurrentUserEmail());
    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<Receta>> getRecetasPublicadas(){
        return mRecetasPublicadas;
    }

    public MutableLiveData<ArrayList<Receta>> getRecetasHechas() {
        return mRecetasHechas;
    }

    public MutableLiveData<ArrayList<Receta>> getRecetasLikes() {
        return mRecetasLikes;
    }

    public MutableLiveData<ArrayList<Receta>> getRecetasFavs() {
        return mRecetasFavs;
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
        mRecetasLikes.setValue(recetas);
    }

    @Override
    public void setCollectionFavs(ArrayList<Receta> recetas) {
        mRecetasFavs.setValue(recetas);
    }

    public void setToast(String t) {
        mToast.setValue(t);
    }

    @Override
    public void setSuccesfull(boolean succesfull) {
    }

    void getNombre(String usuario){
        da.getNombre(usuario);
    }

}
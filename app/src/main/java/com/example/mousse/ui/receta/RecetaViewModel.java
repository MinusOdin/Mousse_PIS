package com.example.mousse.ui.receta;

import androidx.lifecycle.ViewModel;

import com.example.mousse.DatabaseAdapter;
import com.example.mousse.Receta;

import java.util.ArrayList;

public class RecetaViewModel extends ViewModel implements DatabaseAdapter.vmInterface {


    @Override
    public void setCollection(ArrayList<Receta> recetas) {

    }

    @Override
    public void setToast(String t) {

    }

    @Override
    public void setSuccesfull(boolean succesfull) {

    }
}

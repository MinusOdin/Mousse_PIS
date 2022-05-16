package com.example.mousse.ui.editar_perfil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mousse.DatabaseAdapter;
import com.example.mousse.Receta;
import com.example.mousse.Usuario;

import java.util.ArrayList;

public class EditarPerfilViewModel extends ViewModel implements DatabaseAdapter.vmInterface{

    private final MutableLiveData<ArrayList<Receta>> mRecetas;
    private final MutableLiveData<ArrayList<Usuario>> mUsuarios;
    private final MutableLiveData<String> mToast;

    public EditarPerfilViewModel() {
        mRecetas = new MutableLiveData<>();
        mUsuarios = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        DatabaseAdapter da = new DatabaseAdapter(this);
    }


    @Override
    public void setCollection(ArrayList<Receta> recetas) {

    }

    @Override
    public void setToast(String t) {

    }
}

package com.example.mousse.ui.crear_receta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CrearRecetaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CrearRecetaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
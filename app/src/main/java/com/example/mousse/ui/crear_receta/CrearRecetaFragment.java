package com.example.mousse.ui.crear_receta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.R;

public class CrearRecetaFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CrearRecetaViewModel crearRecetaViewModel =
                new ViewModelProvider(this).get(CrearRecetaViewModel.class);

        View root = inflater.inflate(R.layout.creacio_receptes, container, false);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
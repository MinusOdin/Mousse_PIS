package com.example.mousse.ui.crear_receta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.R;
import com.example.mousse.databinding.FragmentNotificationsBinding;
import com.example.mousse.ui.home.HomeViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class CrearRecetaFragment extends Fragment {

    EditText editTextNombreReceta;
    EditText editTextDescripcioReceta;
    Button btnpublicar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CrearRecetaViewModel crearRecetaViewModel =
                new ViewModelProvider(this).get(CrearRecetaViewModel.class);


        View root = inflater.inflate(R.layout.creacio_receptes, container, false);
        btnpublicar = root.findViewById(R.id.btnpublicar);
        btnpublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextNombreReceta = root.findViewById(R.id.editTextNombre);
                editTextDescripcioReceta = root.findViewById(R.id.editTextDescripcion);
                crearRecetaViewModel.addReceta(editTextNombreReceta.getText().toString(), editTextDescripcioReceta.getText().toString());
            }
        });
        return root;
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
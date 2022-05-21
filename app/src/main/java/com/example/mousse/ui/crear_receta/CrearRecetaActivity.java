package com.example.mousse.ui.crear_receta;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.R;
import com.example.mousse.databinding.FragmentNotificationsBinding;
import com.example.mousse.ui.home.HomeViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;

public class CrearRecetaActivity extends AppCompatActivity {

    EditText editTextNombreReceta;
    EditText editTextDescripcioReceta;
    EditText editTextHashtagsReceta;
    EditText editTextIngredientsReceta;
    TextInputEditText editTextPasos;
    Button btnpublicar;
    Button btnCancelar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrearRecetaViewModel crearRecetaViewModel =
                new ViewModelProvider(this).get(CrearRecetaViewModel.class);

        setContentView(R.layout.creacio_receptes);
        btnpublicar = findViewById(R.id.btnpublicar);
        btnpublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextNombreReceta = findViewById(R.id.editTextNombre);
                editTextDescripcioReceta = findViewById(R.id.editTextDescripcion);
                editTextHashtagsReceta = findViewById(R.id.editTextHashtagsReceta);
                ArrayList<String> hashtags = new ArrayList<>(Arrays.asList(editTextHashtagsReceta.getText().toString().split(",")));
                editTextIngredientsReceta = findViewById(R.id.editTextIngredientsReceta);
                ArrayList<String> ingredients = new ArrayList<>(Arrays.asList(editTextIngredientsReceta.getText().toString().split(",")));
                editTextPasos = findViewById(R.id.editTextPasos);
                ArrayList<String> pasos = new ArrayList<>(Arrays.asList(editTextPasos.getText().toString().split("\n")));
                crearRecetaViewModel.addReceta(editTextNombreReceta.getText().toString(), editTextDescripcioReceta.getText().toString(), hashtags, ingredients, pasos);
            }
        });
        btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });

    }

    public void showPopup() {

        View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 600);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // Initialize objects from layout
        Button aceptarButton = popupView.findViewById(R.id.aceptar_button);
        aceptarButton.setOnClickListener((v) -> {
            popupWindow.dismiss();
            finish();

        });
        Button cancelarButton = popupView.findViewById(R.id.cancelar_button);
        cancelarButton.setOnClickListener((v) -> {
            popupWindow.dismiss();
        });
    }

    @Override
    public void onBackPressed() {
        showPopup();
        //super.onBackPressed();
    }
}
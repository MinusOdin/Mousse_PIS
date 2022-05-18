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

public class CrearRecetaActivity extends AppCompatActivity {

    EditText editTextNombreReceta;
    EditText editTextDescripcioReceta;
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
                crearRecetaViewModel.addReceta(editTextNombreReceta.getText().toString(), editTextDescripcioReceta.getText().toString());
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
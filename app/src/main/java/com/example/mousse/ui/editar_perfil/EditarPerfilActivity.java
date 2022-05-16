package com.example.mousse.ui.editar_perfil;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.R;

public class EditarPerfilActivity extends AppCompatActivity {
    private ImageButton btnNoGuardar;
    private ImageButton btnGuardar;
    private ImageButton btnFoto;
    private EditText editTextDescripcion;
    private EditText editTextNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditarPerfilViewModel loginViewModel =
                new ViewModelProvider(this).get(EditarPerfilViewModel.class);

        setContentView(R.layout.editar_perfil);


        btnNoGuardar = findViewById(R.id.noguardar);
        btnNoGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

        btnGuardar = findViewById(R.id.guardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //guardar datos en la base de datos
            }
        });
        btnFoto = findViewById(R.id.foto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //poner foto
            }
        });
/*
        //mostrar datos usuario
        editTextNombre = findViewById(R.id.nombre);
        editTextNombre.setText(); //coger datos de usuario de la base de datos

        editTextDescripcion = findViewById(R.id.descripcion);<
        editTextDescripcion.setText(); //coger datos de usuario de la base de datos

 */
    }

    public void showPopup(View anchorView) {

        View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 600);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

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

}

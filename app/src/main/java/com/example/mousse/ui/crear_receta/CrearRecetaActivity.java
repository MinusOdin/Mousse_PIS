package com.example.mousse.ui.crear_receta;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.R;
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
    Button buttonFoto;
    ImageView image;
    public static final int PICK_IMAGE = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrearRecetaViewModel crearRecetaViewModel =
                new ViewModelProvider(this).get(CrearRecetaViewModel.class);

        setContentView(R.layout.creacio_receptes);
        btnpublicar = findViewById(R.id.btnpublicar);
        btnpublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(editTextNombreReceta == null && editTextIngredientsReceta == null&& editTextPasos == null)) {
                    editTextNombreReceta = findViewById(R.id.editTextNombre);
                    editTextDescripcioReceta = findViewById(R.id.editTextDescripcion);
                    editTextHashtagsReceta = findViewById(R.id.editTextHashtagsReceta);
                    ArrayList<String> hashtags = new ArrayList<>(Arrays.asList(editTextHashtagsReceta.getText().toString().split(",")));
                    editTextIngredientsReceta = findViewById(R.id.editTextIngredientsReceta);
                    ArrayList<String> ingredients = new ArrayList<>(Arrays.asList(editTextIngredientsReceta.getText().toString().split(",")));
                    editTextPasos = findViewById(R.id.editTextPasos);
                    ArrayList<String> pasos = new ArrayList<>(Arrays.asList(editTextPasos.getText().toString().split("\n")));
                    crearRecetaViewModel.addReceta(editTextNombreReceta.getText().toString(), editTextDescripcioReceta.getText().toString(), hashtags, ingredients, pasos);
                buttonFoto = findViewById(R.id.buttonFoto);
                buttonFoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                    }
                });


                    }
                else {
                    Toast toast=Toast.makeText(getApplicationContext(),"Te faltan datos en nombre, ingredient i pasos",Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            Log.d("Si", "Si ho hem aconseguit");
        }
    }

    @Override
    public void onBackPressed() {
        showPopup();
        //super.onBackPressed();
    }


}
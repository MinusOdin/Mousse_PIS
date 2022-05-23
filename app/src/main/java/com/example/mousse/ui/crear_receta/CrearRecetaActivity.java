package com.example.mousse.ui.crear_receta;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

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
    ImageView image;
    public static final int RESULT_LOAD_IMAGE = 1;

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
                /*image = findViewById(R.id.imageViewReceta);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent imagePickerIntent = new Intent();
                        imagePickerIntent.setType("image/*");
                        imagePickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                        //startActivityFo
                    }
                });

                 */
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
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode== RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            image.setImageURI(selectedImage);
        }
    }

 */

    @Override
    public void onBackPressed() {
        showPopup();
        //super.onBackPressed();
    }


}
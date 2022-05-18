package com.example.mousse.ui.receta;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mousse.R;

public class RecetaActivity extends AppCompatActivity {
    private Button buttonPasAPas;
    private ToggleButton toggleButtonNoHecha;
    private ImageButton imageButtonFav;
    private TextView textReceta;
    private TextView textHashtags;
    private RecyclerView recyclerIngredientes;
    private RecyclerView recyclerPasos;
    private RecetaViewModel recetaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecetaViewModel recetaViewModel =
                new ViewModelProvider(this).get(RecetaViewModel.class);

        setContentView(R.layout.recepta);

        buttonPasAPas = findViewById(R.id.buttonPasAPas);
        buttonPasAPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}

package com.example.mousse.ui.receta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.CustomAdapter;
import com.example.mousse.R;
import com.example.mousse.Receta;
import com.example.mousse.ui.otro_perfil.OtroPerfilActivity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RecetaActivity extends AppCompatActivity {
    private ImageButton fav;
    private Receta receta;
    private RecetaViewModel recetaViewModel;
    private Boolean isFav;
    private Boolean empezar = false;
    private ImageButton like;
    private Boolean isLike;
    private Boolean empezar2 = false;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recetaViewModel =
                new ViewModelProvider(this).get(RecetaViewModel.class);

        setContentView(R.layout.recepta);
        receta = (Receta) getIntent().getParcelableExtra("Receta");
        TextView textNomReceta = findViewById(R.id.textNomReceta);
        textNomReceta.setText(receta.getNombre());
        TextView textDescripcion = findViewById(R.id.textDescripcion);
        textDescripcion.setText(receta.getDescripcion());
        TextView textHashtags = findViewById(R.id.textHashtags);
        textHashtags.setText("#" + receta.getHashtags().stream().map(Object::toString).collect(Collectors.joining(" #")));
        TextView textIngredients = findViewById(R.id.textIngredientes);
        textIngredients.setText(receta.getIngredientes().stream().map(Object::toString).collect(Collectors.joining(", ")));
        TextView textPasos = findViewById(R.id.textPasos);
        textPasos.setText(receta.getPasos().stream().map(Object::toString).collect(Collectors.joining("\n")));
        TextView textUsuari = findViewById(R.id.textUsuari);
        textUsuari.setText(receta.getEmailUsuario());
        textUsuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecetaActivity.this, OtroPerfilActivity.class);
                intent.putExtra("user", receta.getEmailUsuario());
                startActivity(intent);
            }
        });
        fav = findViewById(R.id.imageButtonFav);
        recetaViewModel.is_fav();
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recetaViewModel.is_fav();
            }
        });
        like = findViewById(R.id.imageButtonLike);
        recetaViewModel.is_like();
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recetaViewModel.is_like();
            }
        });
        setLiveDataObservers();
    }
    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        recetaViewModel.getRecetas().observe(this, new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
                isFav = false;
                for (Receta r: recetas){
                    if (r.getId().equals(receta.getId())){
                        isFav = true;
                    }
                }
                if (empezar) {
                    if (isFav) {
                        fav.setImageResource(R.drawable.fav_sin);
                        recetaViewModel.no_fav(receta.getId());
                    } else {
                        fav.setImageResource(R.drawable.fav);
                        recetaViewModel.guardar_fav(receta.getId());
                    }
                }
                else{
                    if (!isFav) {
                        fav.setImageResource(R.drawable.fav_sin);
                    } else {
                        fav.setImageResource(R.drawable.fav);
                    }
                }
                empezar = true;
            }
        });

        /*final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(RecetaActivity.this, t, Toast.LENGTH_SHORT).show();
            }
        };
        recetaViewModel.getToast().observe(this, observerToast);

         */

        recetaViewModel.getRecetas2().observe(this, new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
                isLike = false;
                for (Receta r: recetas){
                    if (r.getId().equals(receta.getId())){
                        isLike = true;
                    }
                }
                if (empezar2) {
                    if (isLike) {
                        like.setImageResource(R.drawable.like_sin);
                        recetaViewModel.no_like(receta.getId());
                    } else {
                        like.setImageResource(R.drawable.like_con);
                        recetaViewModel.guardar_like(receta.getId());
                    }
                }
                else{
                    if (!isLike) {
                        like.setImageResource(R.drawable.like_sin);
                    } else {
                        like.setImageResource(R.drawable.like_con);
                    }
                }
                empezar2 = true;
            }
        });
    }
}

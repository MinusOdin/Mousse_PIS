package com.example.mousse.ui.receta;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.R;
import com.example.mousse.Receta;

import java.util.stream.Collectors;

public class RecetaActivity extends AppCompatActivity {

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecetaViewModel recetaViewModel =
                new ViewModelProvider(this).get(RecetaViewModel.class);

        setContentView(R.layout.recepta);
        Receta receta = (Receta) getIntent().getParcelableExtra("Receta");
        TextView textNomReceta = findViewById(R.id.textNomReceta);
        textNomReceta.setText(receta.getNombre());
        TextView textDescripcion = findViewById(R.id.textDescripcion);
        textDescripcion.setText(receta.getDescripcion());
        TextView textHashtags = findViewById(R.id.textHashtags);
        String hashtags = "";
        /*for (String hash : receta.getHashtags()) {
            hashtags += ", " + hash;
        }
        //textHashtags.setText(hashtags);
         */
        TextView textIngredients = findViewById(R.id.textIngredientes);
        //.setText(receta.getIngredientes().stream().map(Object::toString).collect(Collectors.joining(", ")));
        TextView textUsuari = findViewById(R.id.textUsuari);
        textUsuari.setText(receta.getEmailUsuario());

    }
}

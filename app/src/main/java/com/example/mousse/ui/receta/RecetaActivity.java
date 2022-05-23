package com.example.mousse.ui.receta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.R;
import com.example.mousse.Receta;
import com.example.mousse.ui.otro_perfil.OtroPerfilActivity;

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
        textUsuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecetaActivity.this, OtroPerfilActivity.class);
                intent.putExtra("user", receta.getEmailUsuario());
                startActivity(intent);
            }
        });
    }
}

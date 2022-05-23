package com.example.mousse.ui.otro_perfil;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mousse.CustomAdapter;
import com.example.mousse.R;
import com.example.mousse.Receta;

import java.util.ArrayList;

public class OtroPerfilActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private OtroPerfilViewModel otroPerfilViewModel;

    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otroPerfilViewModel =
                new ViewModelProvider(this).get(OtroPerfilViewModel.class);
        setContentView(R.layout.usuario_view);

        String emailUsuario = (String) getIntent().getStringExtra("user");
        otroPerfilViewModel.setEmail(emailUsuario);
        TextView textEmail = findViewById(R.id.textEmail);
        textEmail.setText(emailUsuario);

        mRecyclerView = findViewById(R.id.recyclerViewRecetasOtroUsuario);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this) );
        ArrayList<Receta> recetas = new ArrayList<>();
        CustomAdapter newAdapter = new CustomAdapter( this, recetas);
        mRecyclerView.setAdapter(newAdapter);

        setLiveDataObservers();

    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        otroPerfilViewModel.getRecetas().observe(this, new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
                CustomAdapter newAdapter = new CustomAdapter(OtroPerfilActivity.this, recetas);
                mRecyclerView.setAdapter(newAdapter);
            }
        });

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(OtroPerfilActivity.this, t, Toast.LENGTH_SHORT).show();
            }
        };
        otroPerfilViewModel.getToast().observe(this, observerToast);
    }
}

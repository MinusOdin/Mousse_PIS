package com.example.mousse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mousse.ui.crear_receta.CrearRecetaActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mousse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        findViewById(R.id.navigation_crear_receta).setOnClickListener(this);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder()
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);//per fer intents
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }



    public void goToCrearReceta(){
        Intent intent = new Intent(this, CrearRecetaActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (R.id.navigation_crear_receta == view.getId()){
            goToCrearReceta();
        }
    }
}
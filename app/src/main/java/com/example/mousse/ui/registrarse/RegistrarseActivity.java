package com.example.mousse.ui.registrarse;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.MainActivity;
import com.example.mousse.R;

public class RegistrarseActivity extends AppCompatActivity {
    Button btnregistrarse;
    EditText editTextEmail;
    EditText editTextContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RegistrarseViewModel registrarseViewModel =
                new ViewModelProvider(this).get(RegistrarseViewModel.class);

        setContentView(R.layout.registrarse);

        btnregistrarse = findViewById(R.id.btnRegistrarse);
        btnregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextEmail = findViewById(R.id.editTextTextEmail);
                editTextContraseña = findViewById(R.id.editTextTextContraseña);
                registrarseViewModel.registrarUsuario(editTextEmail.getText().toString(), editTextContraseña.getText().toString());
                Intent intent = new Intent(RegistrarseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

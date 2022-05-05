package com.example.mousse.ui.login;

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
import com.example.mousse.ui.registrarse.RegistrarseActivity;

public class LoginActivity extends AppCompatActivity {
    Button btnregistrarse;
    Button btnlogin;
    EditText editTextEmail;
    EditText editTextContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginViewModel loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);

        setContentView(R.layout.login);

        btnregistrarse = findViewById(R.id.btnRegistrarse);
        btnregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrarseActivity.class);
                startActivity(intent);
            }
        });
        btnlogin = findViewById(R.id.btnLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

package com.example.mousse.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.MainActivity;
import com.example.mousse.R;
import com.example.mousse.ui.registrarse.RegistrarseActivity;

public class LoginActivity extends AppCompatActivity {
    private Button btnregistrarse;
    private Button btnlogin;
    private EditText editTextEmail;
    private EditText editTextContraseña;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);

        setContentView(R.layout.login);

        btnregistrarse = findViewById(R.id.btnRegistrarse);
        btnregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrarseActivity.class);
                finish();
                startActivity(intent);
            }
        });
        btnlogin = findViewById(R.id.btnLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextEmail = findViewById(R.id.editTextEmailLogin);
                editTextContraseña = findViewById(R.id.editTextContraseñaLogin);
                Log.d("SI", "AJKDSLFÑDJGFDLÑKJSAÑLKFJASLDJ");
                loginViewModel.loginUsuario(editTextEmail.getText().toString().trim(), editTextContraseña.getText().toString().trim());

            }
        });

        loginViewModel.getSuccesfull().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean succesfull) {
                if(succesfull){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
                else {
                    Toast toast=Toast.makeText(getApplicationContext(),"Credenciales Erronias",Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();

                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}

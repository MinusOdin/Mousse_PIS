package com.example.mousse.ui.registrarse;

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
                editTextContraseña = findViewById(R.id.editTextContraseñaLogin);
                registrarseViewModel.registrarUsuario(editTextEmail.getText().toString(), editTextContraseña.getText().toString());
            }
        });

        registrarseViewModel.getSuccesfull().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean succesfull) {
                Log.d("observerregistrat:fora del if", "Si funciona");
                if(succesfull){
                    Log.d("observerregistrat:dins de lif(registrat)", "Si funciona");
                    Intent intent = new Intent(RegistrarseActivity.this, MainActivity.class);
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
}

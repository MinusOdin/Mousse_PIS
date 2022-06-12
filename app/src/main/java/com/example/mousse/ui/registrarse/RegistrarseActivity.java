package com.example.mousse.ui.registrarse;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.MainActivity;
import com.example.mousse.R;
import com.example.mousse.ui.crear_receta.CrearRecetaActivity;
import com.example.mousse.ui.login.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RegistrarseActivity extends AppCompatActivity {
    private Button btnregistrarse;
    private EditText editTextEmail;
    private EditText editTextContraseña;
    private EditText editTextContraseña2;
    private EditText editTextNombre;
    private ImageButton imageButton;
    private Uri foto;
    public static final int PICK_IMAGE = 1;
    private RegistrarseViewModel registrarseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registrarseViewModel =
                new ViewModelProvider(this).get(RegistrarseViewModel.class);

        setContentView(R.layout.registrarse);
        foto = Uri.parse("res:///" + R.drawable.mousseimagen);
        imageButton = findViewById(R.id.imageButtonUserImage);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        btnregistrarse = findViewById(R.id.btnRegistrarse);
        btnregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextEmail = findViewById(R.id.editTextTextEmail);
                editTextContraseña = findViewById(R.id.editTextContraseñaLogin);
                editTextContraseña2 = findViewById(R.id.editTextContraseñaLogin2);
                editTextNombre = findViewById(R.id.editTextNombre);
                if(editTextContraseña.getText().toString().equals(editTextContraseña2.getText().toString())){
                    registrarseViewModel.registrarUsuario(editTextEmail.getText().toString(), editTextContraseña.getText().toString(), foto, editTextNombre.getText().toString());
                }else{
                    Toast toast=Toast.makeText(getApplicationContext(),"Contraseña Erronia",Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }

            }
        });

        registrarseViewModel.getSuccesfull().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean succesfull) {
                Log.d("observerregistrat:fora del if", "Si funciona");
                if(succesfull){
                    Log.d("observerregistrat:dins de lif(registrat)", "Si funciona");
                    Intent intent = new Intent(RegistrarseActivity.this, MainActivity.class);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (!(data == null)) {
                foto = data.getData();
                String imageEncoded = getRealPathFromURI(RegistrarseActivity.this, foto);
                Bitmap selectedImage = BitmapFactory.decodeFile(imageEncoded);
                imageButton.setImageBitmap(selectedImage);
            }
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        OutputStream out;
        File file = new File(getFilename(context));

        try {
            if (file.createNewFile()) {
                InputStream iStream = context != null ? context.getContentResolver().openInputStream(contentUri) : context.getContentResolver().openInputStream(contentUri);
                byte[] inputData = getBytes(iStream);
                out = new FileOutputStream(file);
                out.write(inputData);
                out.close();
                return file.getAbsolutePath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private String getFilename(Context context) {
        File mediaStorageDir = new File(context.getExternalFilesDir(""), "patient_data");
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }

        String mImageName = "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        return mediaStorageDir.getAbsolutePath() + "/" + mImageName;

    }
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(RegistrarseActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

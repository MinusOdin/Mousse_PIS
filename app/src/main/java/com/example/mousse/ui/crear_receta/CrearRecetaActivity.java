package com.example.mousse.ui.crear_receta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.MainActivity;
import com.example.mousse.R;
import com.example.mousse.ui.login.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class CrearRecetaActivity extends AppCompatActivity {

    EditText editTextNombreReceta;
    EditText editTextDescripcioReceta;
    EditText editTextHashtagsReceta;
    EditText editTextIngredientsReceta;
    TextInputEditText editTextPasos;
    Button btnpublicar;
    Button btnCancelar;
    Button buttonFoto;
    ImageView image;
    Uri foto;

    public static final int PICK_IMAGE = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrearRecetaViewModel crearRecetaViewModel =
                new ViewModelProvider(this).get(CrearRecetaViewModel.class);

        setContentView(R.layout.creacio_receptes);
        editTextNombreReceta = findViewById(R.id.editTextNombre);
        editTextDescripcioReceta = findViewById(R.id.editTextDescripcion);
        editTextHashtagsReceta = findViewById(R.id.editTextHashtagsReceta);
        editTextIngredientsReceta = findViewById(R.id.editTextIngredientsReceta);
        editTextPasos = findViewById(R.id.editTextPasos);
        btnpublicar = findViewById(R.id.btnpublicar);


        image = findViewById(R.id.imageViewReceta);

        btnpublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(editTextNombreReceta.getText().toString().equals("") && editTextIngredientsReceta.getText().toString().equals("") && editTextPasos.getText().toString().equals("") )) {
                    ArrayList<String> hashtags = new ArrayList<>(Arrays.asList(editTextHashtagsReceta.getText().toString().split(",")));
                    ArrayList<String> ingredients = new ArrayList<>(Arrays.asList(editTextIngredientsReceta.getText().toString().split(",")));
                    ArrayList<String> pasos = new ArrayList<>(Arrays.asList(editTextPasos.getText().toString().split("\n")));
                    crearRecetaViewModel.addReceta(true, editTextNombreReceta.getText().toString(), editTextDescripcioReceta.getText().toString(), hashtags, ingredients, pasos, foto);
                }
                else {
                    Toast toast=Toast.makeText(getApplicationContext(),"Te faltan datos en nombre, ingredient i pasos",Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
                finish();
                }
            });
        crearRecetaViewModel.getSuccesfull().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean succesfull) {
                if(succesfull){
                    finish();
                }
                else {
                    Toast toast=Toast.makeText(getApplicationContext(),"No publicada",Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();

                }
            }
        });
        buttonFoto = findViewById(R.id.buttonFoto);
        buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        //image.getImageUri
        btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });

    }

    public void showPopup() {

        View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 350);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // Initialize objects from layout
        Button aceptarButton = popupView.findViewById(R.id.aceptar_button);
        aceptarButton.setOnClickListener((v) -> {
            popupWindow.dismiss();
            finish();

        });
        Button cancelarButton = popupView.findViewById(R.id.cancelar_button);
        cancelarButton.setOnClickListener((v) -> {
            popupWindow.dismiss();
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (!(data == null)) {
                foto = data.getData();
                String imageEncoded = getRealPathFromURI(CrearRecetaActivity.this, foto);
                Bitmap selectedImage = BitmapFactory.decodeFile(imageEncoded);
                image.setImageBitmap(selectedImage);
            }
        }
    }

    @Override
    public void onBackPressed() {
        showPopup();
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


}
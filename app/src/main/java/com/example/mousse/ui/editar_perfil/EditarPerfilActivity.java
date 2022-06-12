package com.example.mousse.ui.editar_perfil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.R;
import com.example.mousse.Receta;
import com.example.mousse.Usuario;
import com.example.mousse.ui.registrarse.RegistrarseActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class EditarPerfilActivity extends AppCompatActivity {
    private ImageButton btnNoGuardar;
    private ImageButton btnGuardar;
    private ImageButton btnFoto;
    private EditText editTextNombre;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();
    private EditarPerfilViewModel loginViewModel;
    private Uri foto;
    public static final int PICK_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel =
                new ViewModelProvider(this).get(EditarPerfilViewModel.class);

        setContentView(R.layout.editar_perfil);
        setLiveDataObservers();

        foto = Uri.parse("res:///" + R.drawable.mousseimagen);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        btnNoGuardar = findViewById(R.id.noguardar);
        btnNoGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });

        btnGuardar = findViewById(R.id.guardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(foto.equals(Uri.parse("res:///" + R.drawable.mousseimagen))){
                    loginViewModel.guardarNombre(editTextNombre.getText().toString());
                }else{
                    loginViewModel.guardarDatos(editTextNombre.getText().toString(), foto);
                }
                finish();
            }
        });
        btnFoto = findViewById(R.id.foto);
        StorageReference downRef = storageReference.child("usuario/" + Usuario.getCurrentUserEmail() + "/image.jpg" );
        downRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("URL Descargada", uri.toString());

                Bitmap selectedImage = getBitmapFromURL(uri.toString());
                btnFoto.setImageBitmap(selectedImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                btnFoto.setImageResource(R.drawable.mousseimagen);
            }
        });
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        editTextNombre = findViewById(R.id.nombre);
        loginViewModel.getNombre(Usuario.getCurrentUserEmail());

    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                //Toast.makeText(OtroPerfilActivity.this, t, Toast.LENGTH_SHORT).show();
                editTextNombre.setText(t);
            }
        };
        loginViewModel.getToast().observe(this, observerToast);
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

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (!(data == null)) {
                foto = data.getData();
                String imageEncoded = getRealPathFromURI(EditarPerfilActivity.this, foto);
                Bitmap selectedImage = BitmapFactory.decodeFile(imageEncoded);
                btnFoto.setImageBitmap(selectedImage);
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
        showPopup();
    }
}

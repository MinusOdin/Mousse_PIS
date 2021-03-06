package com.example.mousse.ui.receta;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mousse.CustomAdapter;
import com.example.mousse.R;
import com.example.mousse.Receta;
import com.example.mousse.Usuario;
import com.example.mousse.ui.otro_perfil.OtroPerfilActivity;
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
import java.util.stream.Collectors;

public class RecetaActivity extends AppCompatActivity {
    private ImageButton fav;
    private Receta receta;
    private RecetaViewModel recetaViewModel;
    private Boolean isFav;
    private Boolean empezar = false;
    private ImageButton like;
    private Boolean isLike;
    private Boolean empezar2 = false;
    private ToggleButton hecho;
    private Boolean isHecho;
    private Boolean empezar3 = false;
    private ImageButton eliminar;
    ImageView recetaImage;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recetaViewModel =
                new ViewModelProvider(this).get(RecetaViewModel.class);

        setContentView(R.layout.recepta);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        receta = (Receta) getIntent().getParcelableExtra("Receta");
        TextView textNomReceta = findViewById(R.id.textNomReceta);
        textNomReceta.setText(receta.getNombre());
        TextView textDescripcion = findViewById(R.id.textDescripcion);
        textDescripcion.setText(receta.getDescripcion());

        TextView textHashtags = findViewById(R.id.textHashtags);
        textHashtags.setText("#" + receta.getHashtags().stream().map(Object::toString).collect(Collectors.joining(" #")));

        TextView textIngredients = findViewById(R.id.textIngredientes);
        textIngredients.setText(receta.getIngredientes().stream().map(Object::toString).collect(Collectors.joining(", ")));

        TextView textPasos = findViewById(R.id.textPasos);
        textPasos.setText(receta.getPasos().stream().map(Object::toString).collect(Collectors.joining("\n")));
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
        recetaImage = findViewById(R.id.imageView);
        StorageReference downRef = storageReference.child("recetas/" + receta.getId() + "/image.jpg" );
        downRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("URL Descargada", uri.toString());

                Bitmap selectedImage = getBitmapFromURL(uri.toString());
                recetaImage.setImageBitmap(selectedImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                recetaImage.setImageResource(R.drawable.plato);
            }
        });

        fav = findViewById(R.id.imageButtonFav);
        recetaViewModel.is_fav();
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recetaViewModel.is_fav();
            }
        });
        like = findViewById(R.id.imageButtonLike);
        recetaViewModel.is_like();
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recetaViewModel.is_like();
            }
        });
        hecho = (ToggleButton) findViewById(R.id.toggleButtonNoHecha);
        recetaViewModel.is_hecho();
        hecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recetaViewModel.is_hecho();
            }
        });

        eliminar = findViewById(R.id.imageButtonEliminar);
        if (receta.getPublicada() && receta.getEmailUsuario().equals(Usuario.getCurrentUserEmail())){
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopup();
                }
            });
        }
        else{
            eliminar.setVisibility(View.INVISIBLE);
        }

        setLiveDataObservers();
    }

    public void showPopup() {

        View popupView = getLayoutInflater().inflate(R.layout.popup_eliminar, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 350);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // Initialize objects from layout
        Button aceptarButton = popupView.findViewById(R.id.aceptar_button);
        aceptarButton.setOnClickListener((v) -> {
            popupWindow.dismiss();
            recetaViewModel.eliminar_receta(receta.getId());
            finish();

        });
        Button cancelarButton = popupView.findViewById(R.id.cancelar_button);
        cancelarButton.setOnClickListener((v) -> {
            popupWindow.dismiss();
        });
    }

    protected void onStart(){
        super.onStart();
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        recetaViewModel.getRecetas().observe(this, new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
                isFav = false;
                for (Receta r: recetas){
                    if (r.getId().equals(receta.getId())){
                        isFav = true;
                    }
                }
                if (empezar) {
                    if (isFav) {
                        fav.setImageResource(R.drawable.fav_sin);
                        recetaViewModel.no_fav(receta.getId());
                    } else {
                        fav.setImageResource(R.drawable.fav);
                        recetaViewModel.guardar_fav(receta.getId());
                    }
                }
                else{
                    if (!isFav) {
                        fav.setImageResource(R.drawable.fav_sin);
                    } else {
                        fav.setImageResource(R.drawable.fav);
                    }
                }
                empezar = true;
            }
        });

        /*final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(RecetaActivity.this, t, Toast.LENGTH_SHORT).show();
            }
        };
        recetaViewModel.getToast().observe(this, observerToast);

         */

        recetaViewModel.getRecetas2().observe(this, new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
                isLike = false;
                for (Receta r: recetas){
                    if (r.getId().equals(receta.getId())){
                        isLike = true;
                    }
                }
                if (empezar2) {
                    if (isLike) {
                        like.setImageResource(R.drawable.like_sin);
                        recetaViewModel.no_like(receta.getId());
                    } else {
                        like.setImageResource(R.drawable.like_con);
                        recetaViewModel.guardar_like(receta.getId());
                    }
                }
                else{
                    if (!isLike) {
                        like.setImageResource(R.drawable.like_sin);
                    } else {
                        like.setImageResource(R.drawable.like_con);
                    }
                }
                empezar2 = true;
            }
        });

        recetaViewModel.getRecetas3().observe(this, new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
                isHecho = false;
                for (Receta r: recetas){
                    if (r.getId().equals(receta.getId())){
                        isHecho = true;
                    }
                }
                if (empezar3) {
                    if (isHecho) {
                        recetaViewModel.no_hecho(receta.getId());
                    } else {
                        recetaViewModel.guardar_hecho(receta.getId());
                    }
                }
                else{
                        hecho.setChecked(isHecho);
                }
                empezar3 = true;
            }
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

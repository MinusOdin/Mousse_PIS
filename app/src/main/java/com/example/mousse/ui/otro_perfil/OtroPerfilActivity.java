package com.example.mousse.ui.otro_perfil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mousse.CustomAdapter;
import com.example.mousse.R;
import com.example.mousse.Receta;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class OtroPerfilActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private OtroPerfilViewModel otroPerfilViewModel;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    private ImageView imageViewUser;
    private String emailUsuario;
    private TabLayout tabLayout;
    private TextView textEmail;

    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otroPerfilViewModel =
                new ViewModelProvider(this).get(OtroPerfilViewModel.class);
        setContentView(R.layout.usuario_view);


        emailUsuario = (String) getIntent().getStringExtra("user");
        textEmail = findViewById(R.id.textEmail);
        otroPerfilViewModel.getNombre(emailUsuario);


        otroPerfilViewModel.init(emailUsuario);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        imageViewUser = findViewById(R.id.imageUser);
        StorageReference downRef = storageReference.child("usuario/" + emailUsuario + "/image.jpg" );
        downRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("URL Descargada", uri.toString());

                Bitmap selectedImage = getBitmapFromURL(uri.toString());
                imageViewUser.setImageBitmap(selectedImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                imageViewUser.setImageResource(R.drawable.mousseimagen);
            }
        });


        mRecyclerView = findViewById(R.id.recyclerViewRecetasOtroUsuario);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this) );
        ArrayList<Receta> recetas = otroPerfilViewModel.getRecetasPublicadas().getValue();
        CustomAdapter newAdapter = new CustomAdapter( this, recetas);
        mRecyclerView.setAdapter(newAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        ArrayList<Receta> recetasPublicadas = otroPerfilViewModel.getRecetasPublicadas().getValue();
                        CustomAdapter newAdapterPub = new CustomAdapter( OtroPerfilActivity.this, recetasPublicadas);
                        mRecyclerView.setAdapter(newAdapterPub);
                        break;
                    case 1:
                        ArrayList<Receta> recetasHechas = otroPerfilViewModel.getRecetasHechas().getValue();
                        CustomAdapter newAdapterHechas = new CustomAdapter( OtroPerfilActivity.this, recetasHechas);
                        mRecyclerView.setAdapter(newAdapterHechas);
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        setLiveDataObservers();

    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        otroPerfilViewModel.getRecetasPublicadas().observe(this, new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
            }
        });

        otroPerfilViewModel.getRecetasHechas().observe(this, new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
            }
        });

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                //Toast.makeText(OtroPerfilActivity.this, t, Toast.LENGTH_SHORT).show();
                textEmail.setText(t);
            }
        };
        otroPerfilViewModel.getToast().observe(this, observerToast);
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
}

package com.example.mousse.ui.perfil;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mousse.CustomAdapter;
import com.example.mousse.R;
import com.example.mousse.Receta;
import com.example.mousse.Usuario;
import com.example.mousse.ui.crear_receta.CrearRecetaActivity;
import com.example.mousse.ui.editar_perfil.EditarPerfilActivity;
import com.example.mousse.ui.login.LoginActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;

public class PerfilFragment extends Fragment{

    private static final String TAG = "Perfil Fragment";
    private RecyclerView mRecyclerView;
    private PerfilViewModel viewModel;
    private Button btnEditarPerfil;
    private ImageView imageViewUser;
    private Button btnLogOut;
    private TextView textEmail;
    private TabLayout tabLayout;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        viewModel.init();

        View root = inflater.inflate(R.layout.usuarios_perfil, container, false);


        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        imageViewUser = root.findViewById(R.id.imageUser);
        StorageReference downRef = storageReference.child("usuario/" + Usuario.getCurrentUserEmail() + "/image.jpg" );
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

        textEmail = root.findViewById(R.id.textEmail);
        textEmail.setText(Usuario.getCurrentUserEmail());



        mRecyclerView = root.findViewById(R.id.recyclerViewRecetasUsuario);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()) );
        ArrayList<Receta> recetasPublicadas = viewModel.getRecetasPublicadas().getValue();
        CustomAdapter newAdapterPub = new CustomAdapter(root.getContext(), recetasPublicadas);
        mRecyclerView.setAdapter(newAdapterPub);

        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        ArrayList<Receta> recetasPublicadas = viewModel.getRecetasPublicadas().getValue();
                        CustomAdapter newAdapterPub = new CustomAdapter(root.getContext(), recetasPublicadas);
                        mRecyclerView.setAdapter(newAdapterPub);
                        break;
                    case 1:
                        ArrayList<Receta> recetasHechas = viewModel.getRecetasHechas().getValue();
                        CustomAdapter newAdapterHechas = new CustomAdapter(root.getContext(), recetasHechas);
                        mRecyclerView.setAdapter(newAdapterHechas);
                        break;
                    case 2:
                        ArrayList<Receta> recetasLikes = viewModel.getRecetasLikes().getValue();
                        CustomAdapter newAdapterLikes = new CustomAdapter(root.getContext(), recetasLikes);
                        mRecyclerView.setAdapter(newAdapterLikes);
                        break;
                    case 3:
                        ArrayList<Receta> recetasFavs = viewModel.getRecetasFavs().getValue();
                        CustomAdapter newAdapterFavs = new CustomAdapter(root.getContext(), recetasFavs);
                        mRecyclerView.setAdapter(newAdapterFavs);
                        break;
                    case 4:
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

        setLiveDataObservers(root);

        //boton editar perfil
        btnEditarPerfil = root.findViewById(R.id.editar_perfil);
        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditarPerfilActivity.class);
                startActivity(intent);
            }
        });
        //
        btnLogOut = root.findViewById(R.id.buttonLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });

        return root;
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

    public void setLiveDataObservers(View root) {
        //Subscribe the activity to the observable
        viewModel.getRecetasPublicadas().observe(getViewLifecycleOwner(), new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
                Log.d("Funciona lobserver de les receptes del perfil?", "Si");
            }
        });

        viewModel.getRecetasHechas().observe(getViewLifecycleOwner(), new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
                Log.d("Funciona lobserver de les receptes del perfil?", "Si");
            }
        });

        viewModel.getRecetasLikes().observe(getViewLifecycleOwner(), new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
                Log.d("Funciona lobserver de les receptes del perfil?", "Si");
            }
        });

        viewModel.getRecetasFavs().observe(getViewLifecycleOwner(), new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
                Log.d("Funciona lobserver de les receptes del perfil?", "Si");
            }
        });

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(root.getContext(), t, Toast.LENGTH_SHORT).show();
            }
        };
        viewModel.getToast().observe(getViewLifecycleOwner(), observerToast);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void showPopup() {

        View popupView = getLayoutInflater().inflate(R.layout.popup_log_out, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 600);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // Initialize objects from layout
        Button aceptarButton = popupView.findViewById(R.id.aceptar_button);
        aceptarButton.setOnClickListener((v) -> {
            popupWindow.dismiss();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getFragmentManager().beginTransaction().remove(this).commit();
        });
        Button cancelarButton = popupView.findViewById(R.id.cancelar_button);
        cancelarButton.setOnClickListener((v) -> {
            popupWindow.dismiss();
        });
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

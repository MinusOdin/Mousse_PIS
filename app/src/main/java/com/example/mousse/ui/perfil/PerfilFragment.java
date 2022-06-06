package com.example.mousse.ui.perfil;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
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
import com.example.mousse.ui.editar_perfil.EditarPerfilActivity;
import com.example.mousse.ui.login.LoginActivity;

import java.util.ArrayList;

public class PerfilFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private PerfilViewModel viewModel;
    private Button btnEditarPerfil;
    private Button btnLogOut;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        viewModel.init();

        View root = inflater.inflate(R.layout.usuarios_perfil, container, false);

        mRecyclerView = root.findViewById(R.id.recyclerViewRecetasUsuario);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()) );
        ArrayList<Receta> recetas = new ArrayList<>();
        CustomAdapter newAdapter = new CustomAdapter(root.getContext(), recetas);
        mRecyclerView.setAdapter(newAdapter);
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

    public void setLiveDataObservers(View root) {
        //Subscribe the activity to the observable
        viewModel.getRecetas().observe(getViewLifecycleOwner(), new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
                Log.d("Funciona lobserver de les receptes del perfil?", "Si");
                CustomAdapter newAdapter = new CustomAdapter(root.getContext(), recetas);
                mRecyclerView.setAdapter(newAdapter);
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
}

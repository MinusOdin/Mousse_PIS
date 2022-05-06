package com.example.mousse.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mousse.CustomAdapter;
import com.example.mousse.R;
import com.example.mousse.Receta;

import java.util.ArrayList;

public class PerfilFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private PerfilViewModel viewModel;
    private CustomAdapter newAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);



        View root = inflater.inflate(R.layout.usuarios_perfil, container, false);

        mRecyclerView = root.findViewById(R.id.recyclerViewRecetasUsuario);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()) );
        ArrayList<Receta> recetas = new ArrayList<>();
        recetas.add(new Receta("Macarrons", "Macarrones con queso"));
        recetas.add(new Receta("Macarrons2", "Macarrones con queso"));
        recetas.add(new Receta("Macarrons3", "Macarrones con queso"));
        recetas.add(new Receta("Macarrons4", "Macarrones con queso"));
        newAdapter = new CustomAdapter(root.getContext(), recetas);
        mRecyclerView.setAdapter(newAdapter);
        //setLiveDataObservers(root);

        return root;
    }

    public void setLiveDataObservers(View root) {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        final Observer<ArrayList<Receta>> observer = new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
                newAdapter.notifyDataSetChanged();
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(root.getContext(), t, Toast.LENGTH_SHORT).show();
            }
        };

        viewModel.getRecetas().observe(getViewLifecycleOwner(), observer);
        viewModel.getToast().observe(getViewLifecycleOwner(), observerToast);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

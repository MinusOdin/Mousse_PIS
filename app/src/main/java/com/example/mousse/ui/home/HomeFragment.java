package com.example.mousse.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mousse.CustomAdapter;
import com.example.mousse.R;
import com.example.mousse.Receta;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_pantalladerecomenacions, container, false);
        mRecyclerView = root.findViewById(R.id.recycleviewRecom);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()) );
        ArrayList<Receta> recetas = new ArrayList<>();
        CustomAdapter newAdapter = new CustomAdapter(root.getContext(), recetas);
        mRecyclerView.setAdapter(newAdapter);
        setLiveDataObservers(root);
        return root;
    }

    public void setLiveDataObservers(View root) {
        //Subscribe the activity to the observable
        homeViewModel.getRecetas().observe(getViewLifecycleOwner(), new Observer<ArrayList<Receta>>() {
            @Override
            public void onChanged(ArrayList<Receta> recetas) {
                Log.d("Funciona lobserver de les receptes de recomenacions?", "Si");
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
        homeViewModel.getToast().observe(getViewLifecycleOwner(), observerToast);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
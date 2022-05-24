package com.example.mousse.ui.buscar;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.mousse.ui.perfil.PerfilViewModel;

import java.util.ArrayList;

public class BuscarFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private BuscarViewModel viewModel;
    private EditText editTextBuscar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(BuscarViewModel.class);

        View root = inflater.inflate(R.layout.fragment_buscar, container, false);
        mRecyclerView = root.findViewById(R.id.recetas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()) );
        ArrayList<Receta> recetas = new ArrayList<>();
        CustomAdapter newAdapter = new CustomAdapter(root.getContext(), recetas);
        mRecyclerView.setAdapter(newAdapter);
        setLiveDataObservers(root);

        editTextBuscar = root.findViewById(R.id.buscar);
        editTextBuscar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    viewModel.init(editTextBuscar.getText().toString());

                    return true;

                }
                return false;
            }
        });
        viewModel.getSuccesfull().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean){
                    Toast toast=Toast.makeText(getContext(),"No hay recetas",Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
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
}
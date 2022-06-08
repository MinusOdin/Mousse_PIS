package com.example.mousse;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Provide a reference to the type of views that you are using
 * (custom ViewHolder).
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {

    private final TextView textViewNombre;
    private final TextView textViewDescripcion;
    private final LinearLayout linearLayout;
    private final ImageView recetaImage;

    public CustomViewHolder(View view) {
        super(view);
        // Define click listener for the ViewHolder's View

        textViewNombre = view.findViewById(R.id.textViewIngredientsReceta);
        textViewDescripcion = view.findViewById(R.id.textViewDescripcionReceta);
        linearLayout = view.findViewById(R.id.linearLayout);
        recetaImage = view.findViewById(R.id.fotoRecetaItem);
    }

    public TextView getTextViewDescripcion() {
        return textViewDescripcion;
    }

    public LinearLayout getLinearLayout() {return linearLayout;}

    public TextView getTextViewNombre() {
        return textViewNombre;
    }

    public ImageView getRecetaImage() {
        return recetaImage;
    }
}

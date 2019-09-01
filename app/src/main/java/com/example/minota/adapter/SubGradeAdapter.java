package com.example.minota.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.minota.R;

public class SubGradeAdapter extends RecyclerView.Adapter<SubGradeAdapter.SubNotaViewHolder> {

    private int nroSubNotas;

    public SubGradeAdapter(int nroSubNotas){
        this.nroSubNotas = nroSubNotas;
    }

    @NonNull
    @Override
    public SubNotaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        View view = layoutInflater.inflate(R.layout.sub_nota, viewGroup, false);

        return new SubNotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubNotaViewHolder subNotaViewHolder, int i) {

        String nombre= "Grade "+ (i + 1);
        Double porcentaje = 100.0/nroSubNotas;

        subNotaViewHolder.bin(nombre, porcentaje, 20.0);
    }

    @Override
    public int getItemCount() {
        return nroSubNotas;
    }

    class SubNotaViewHolder extends RecyclerView.ViewHolder{

        public final TextView mNombreSubNotaTextView;
        public final TextView mPorcentajeSubNotaTextView;
        public final EditText mSubNotaEditTex;

        public SubNotaViewHolder(View itemView) {
            super(itemView);

            mNombreSubNotaTextView = itemView.findViewById(R.id.tv_nombre_sub_nota);
            mPorcentajeSubNotaTextView = itemView.findViewById(R.id.tv_sub_porcentaje);
            mSubNotaEditTex = itemView.findViewById(R.id.et_sub_nota);
        }

        void bin(String nomSubNota, Double porcSubNota, Double subNota){
            mNombreSubNotaTextView.setText(nomSubNota);
            mPorcentajeSubNotaTextView.setText(String.valueOf(porcSubNota));
            mSubNotaEditTex.setText(String.valueOf(subNota));
        }
    }
}

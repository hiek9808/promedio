package com.example.minota.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.example.minota.R;
import com.example.minota.model.Grade;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.NotaViewHolder> {

    private List<Grade> grades;

    public Context context;

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    final private ListItemLongClickListener  mOnClickListener;

    public interface ListItemLongClickListener {
        void onListItemClick(int position, RecyclerView view);
    }



    public GradeAdapter(Context context, ListItemLongClickListener clickListener) {
        this.context = context;
        mOnClickListener = clickListener;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        View view = layoutInflater.inflate(R.layout.nota, viewGroup, false);

        return new NotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder notaViewHolder, int i) {

        Grade grade = grades.get(i);

        notaViewHolder.bin(grade.getNameGrade(), grade.getPercent(), grade.getGrade());

        SubGradeAdapter subGradeAdapter = new SubGradeAdapter(grades.get(i).getSubGrades());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        notaViewHolder.recyclerView.setLayoutManager(linearLayoutManager);

        notaViewHolder.recyclerView.setHasFixedSize(true);

        notaViewHolder.recyclerView.setAdapter(subGradeAdapter);




    }

    @Override
    public void onViewRecycled(@NonNull NotaViewHolder holder) {
        super.onViewRecycled(holder);

        holder.recycled();
    }

    @Override
    public int getItemCount() {
        if (grades == null) return 0;
        return grades.size();
    }

    class NotaViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public final TextView mNombreNotaTextView;
        public final TextView mPorcentajeTextView;
        public final EditText mNotaEditText;
        public final RecyclerView recyclerView;


        public NotaViewHolder(View itemView) {
            super(itemView);

            mNombreNotaTextView = itemView.findViewById(R.id.tv_nombre_nota);
            mPorcentajeTextView = itemView.findViewById(R.id.tv_porcentaje);
            mNotaEditText = itemView.findViewById(R.id.et_nota);
            recyclerView = itemView.findViewById(R.id.rv_sub_notas);
            recyclerView.setVisibility(View.GONE);

            itemView.setOnLongClickListener(this);

            mNotaEditText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    recycled();

                    return false;
                }
            });

        }

        void bin(String nombreNota, Double porcentaje, Double nota){
            mNombreNotaTextView.setText(nombreNota);
            mPorcentajeTextView.setText(String.valueOf(porcentaje));
            mNotaEditText.setText(String.valueOf(nota));


        }

        void recycled(){

            String nota = mNotaEditText.getText().toString();

            if (!nota.isEmpty()){
                grades.get(getAdapterPosition()).setGrade(Double.parseDouble(nota));

            } else {
                grades.get(getAdapterPosition()).setGrade(0.0);
            }
        }

        @Override
        public boolean onLongClick(View v) {

            mOnClickListener.onListItemClick(getAdapterPosition(), recyclerView);


            return true;

        }
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<Grade> getGrades() {
        return grades;
    }
}

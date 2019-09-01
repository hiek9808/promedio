package com.example.minota.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minota.R;
import com.example.minota.model.Lesson;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.CursoViewHolder> {

    private List<Lesson> lessons;

    final private ListItemClickListener  mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public LessonAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;

    }

    @NonNull
    @Override
    public CursoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        View view = layoutInflater.inflate(R.layout.curso, viewGroup, false);



        return new CursoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoViewHolder holder, int position)  {


        holder.bin(lessons.get(position).getNameLesson().toUpperCase());


    }

    @Override
    public int getItemCount() {

        if (lessons == null) return 0;
        return lessons.size();
    }

    public class CursoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mCursoDisplayTextView;

        public CursoViewHolder(View itemView){
            super(itemView);

            mCursoDisplayTextView = itemView.findViewById(R.id.tv_curso);

            itemView.setOnClickListener(this);
        }

        void bin(String nombreCurso){
            mCursoDisplayTextView.setText(nombreCurso);
        }


        @Override
        public void onClick(View v) {
            int position = lessons.get(getAdapterPosition()).getIdLesson();
            mOnClickListener.onListItemClick(position);
        }
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}

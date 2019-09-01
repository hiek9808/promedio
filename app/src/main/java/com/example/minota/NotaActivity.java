package com.example.minota;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.minota.adapter.GradeAdapter;
import com.example.minota.data.InstituteDbHelper;
import com.example.minota.model.Grade;

import java.util.ArrayList;
import java.util.List;

public class NotaActivity extends AppCompatActivity implements GradeAdapter.ListItemLongClickListener{

    RecyclerView recyclerView;
    GradeAdapter gradeAdapter;
    List<Grade> grades;
    InstituteDbHelper db;
    TextView mNotaFinalTextView;
    Button mCalcularButton;
    double notaFinal;
    Integer idLesson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);

        Intent intent = getIntent();
        setTitle(String.valueOf(intent.getExtras().getString("name")));
        idLesson = intent.getExtras().getInt("position");

        recyclerView = findViewById(R.id.rv_notas);
        mNotaFinalTextView = findViewById(R.id.tv_nota_final);
        mCalcularButton = findViewById(R.id.btn_calcular);

        db = new InstituteDbHelper(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        grades = db.getByIdLesson(idLesson);


        gradeAdapter = new GradeAdapter(this,this );
        gradeAdapter.setGrades(grades);

        recyclerView.setAdapter(gradeAdapter);

        mCalcularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotaFinalTextView.setText(String.valueOf(calcularNotaFinal()));
            }
        });
    }

    @Override
    public void onListItemClick(int position, RecyclerView view) {
        if (view.getVisibility() == View.VISIBLE){
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public double calcularNotaFinal(){
        notaFinal = 0;
        for (Grade n : gradeAdapter.getGrades()){
            notaFinal += n.getGrade();
        }

        return notaFinal/ grades.size();
    }

    @Override
    protected void onStop() {
        super.onStop();

        for ( Grade g : grades){
            db.updateGrade(g);
        }
    }
}

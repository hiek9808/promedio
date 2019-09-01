package com.example.minota;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.minota.adapter.LessonAdapter;
import com.example.minota.data.InstituteDbHelper;
import com.example.minota.model.Grade;
import com.example.minota.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LessonAdapter.ListItemClickListener{

    RecyclerView recyclerView;
    LessonAdapter lessonAdapter;
    Toast mToast;
    List<Lesson> lessons;
    InstituteDbHelper db;
    FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_cursos);
        actionButton = findViewById(R.id.floatingActionButton);

        db = new InstituteDbHelper(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        if (db.getAllLessons().size() == 0){
            db.saveLesson("Integracion de Aplicaciones");
            Integer idLesson = db.getAllLessons().get(0).getIdLesson();
            db.saveGrade("Unidad de Aprendizaje", 0.4, 15.0, 4, idLesson);
            db.saveGrade("Evaluacion Permanente", 0.3, 12.0, 4, idLesson);
            db.saveGrade("Examen Parcial", 0.1, 14.0, 0, idLesson);
            db.saveGrade("Examen Final", 0.2, 15.0, 0, idLesson);
        }

        lessons = db.getAllLessons();

        lessonAdapter = new LessonAdapter(this);
        lessonAdapter.setLessons(lessons);

        recyclerView.setAdapter(lessonAdapter);


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSimpleDialog().show();

            }
        });

    }


    @Override
    public void onListItemClick(int clickedItemIndex) {

        String name = "";

        for (Lesson l : lessons){
            if (l.getIdLesson() == clickedItemIndex) name = l.getNameLesson();
        }

        Intent intent = new Intent(this, NotaActivity.class);
        intent.putExtra("position", clickedItemIndex);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Titulo")
                .setMessage("El Mensaje para el usuario")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.saveLesson("nombre del curso");

                                List<Lesson> lessonList = db.getAllLessons();

                                Integer idLesson = lessonList.get(lessonList.size()-1).getIdLesson();
                                db.saveGrade("Unidad de Aprendizaje", 0.4, 15.0, 4, idLesson);
                                db.saveGrade("Evaluacion Permanente", 0.3, 12.0, 4, idLesson);
                                db.saveGrade("Examen Parcial", 0.1, 14.0, 0, idLesson);
                                db.saveGrade("Examen Final", 0.2, 15.0, 0, idLesson);

                                lessons = db.getAllLessons();
                                lessonAdapter.setLessons(lessons);

                                recyclerView.setAdapter(lessonAdapter);
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

        return builder.create();
    }
}

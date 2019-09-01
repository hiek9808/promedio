package com.example.minota.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.minota.data.InstituteContract.*;
import com.example.minota.model.Grade;
import com.example.minota.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class InstituteDbHelper extends SQLiteOpenHelper {

    // The database nameLesson
    private static final String DATABASE_NAME = "institute.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public InstituteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getReadableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LESSON_TABLE = "CREATE TABLE " + LessonEntry.TABLE_NAME +" (" +
                LessonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LessonEntry.COLUMN_NAME + " TEXT NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_LESSON_TABLE);

        final String SQL_CREATE_GRADE_TABLE = "CREATE TABLE " + GradeEntry.TABLE_NAME + " (" +
                GradeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                GradeEntry.COLUMN_NAME + " TEXT NOT NULL," +
                GradeEntry.COLUMN_PERCENT + " REAL NOT NULL," +
                GradeEntry.COLUMN_GRADE + " REAL NOT NULL," +
                GradeEntry.COLUMN_SUB_GRADES + " INTEGER NOT NULL," +
                GradeEntry.COLUMN_LESSON_ID + " INTEGER NOT NULL," +
                " FOREIGN KEY( " + GradeEntry.COLUMN_LESSON_ID + ")" +
                " REFERENCES " + LessonEntry.TABLE_NAME + "( " + LessonEntry._ID + " )" +
                ");";

        db.execSQL(SQL_CREATE_GRADE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LessonEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public void saveLesson(String name){
        ContentValues cv = new ContentValues();
        cv.put(LessonEntry.COLUMN_NAME, name);
        db.insert(LessonEntry.TABLE_NAME, null, cv);
    }

    public void saveGrade(String name, Double percent, Double grade, Integer subGrades, Integer idLesson){
        ContentValues cv = new ContentValues();
        cv.put(GradeEntry.COLUMN_NAME, name);
        cv.put(GradeEntry.COLUMN_PERCENT, percent);
        cv.put(GradeEntry.COLUMN_GRADE, grade);
        cv.put(GradeEntry.COLUMN_SUB_GRADES, subGrades);
        cv.put(GradeEntry.COLUMN_LESSON_ID, idLesson);
        db.insert(GradeEntry.TABLE_NAME, null, cv);
    }

    public void updateGrade(Grade grade){
        ContentValues cv = new ContentValues();
        cv.put(GradeEntry.COLUMN_NAME, grade.getNameGrade());
        cv.put(GradeEntry.COLUMN_PERCENT, grade.getPercent());
        cv.put(GradeEntry.COLUMN_GRADE, grade.getGrade());
        cv.put(GradeEntry.COLUMN_SUB_GRADES, grade.getSubGrades());
        db.update(GradeEntry.TABLE_NAME, cv, GradeEntry._ID +" = " + grade.getIdGrade(), null);
    }

    public List<Grade> getByIdLesson(Integer id){
        List<Grade> grades = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + GradeEntry.TABLE_NAME + " WHERE " +
                GradeEntry.COLUMN_LESSON_ID + " = " + id, null);

        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Integer idGrade = cursor.getInt(cursor.getColumnIndex(GradeEntry._ID));
                String name = cursor.getString(cursor.getColumnIndex(GradeEntry.COLUMN_NAME));
                Double percent = cursor.getDouble(cursor.getColumnIndex(GradeEntry.COLUMN_PERCENT));
                Double grade = cursor.getDouble(cursor.getColumnIndex(GradeEntry.COLUMN_GRADE));
                Integer subGrades = cursor.getInt(cursor.getColumnIndex(GradeEntry.COLUMN_SUB_GRADES));
                Integer idLesson = cursor.getInt(cursor.getColumnIndex(GradeEntry.COLUMN_LESSON_ID));

                Grade gr = new Grade();
                gr.setIdGrade(idGrade);
                gr.setNameGrade(name);
                gr.setPercent(percent);
                gr.setGrade(grade);
                gr.setSubGrades(subGrades);
                gr.setIdLesson(idLesson);
                grades.add(gr);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return grades;
    }

    public List<Lesson> getAllLessons(){
        List<Lesson> lessons = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + LessonEntry.TABLE_NAME, null);

        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex(LessonEntry._ID));
                String name = cursor.getString(cursor.getColumnIndex(LessonEntry.COLUMN_NAME));
                lessons.add(new Lesson(id, name));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lessons;
    }

    public List<Grade> getAllGrades(){
        List<Grade> grades = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + GradeEntry.TABLE_NAME, null);

        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex(GradeEntry._ID));
                String name = cursor.getString(cursor.getColumnIndex(GradeEntry.COLUMN_NAME));
                Double percent = cursor.getDouble(cursor.getColumnIndex(GradeEntry.COLUMN_PERCENT));
                Double grade = cursor.getDouble(cursor.getColumnIndex(GradeEntry.COLUMN_GRADE));
                Integer subGrades = cursor.getInt(cursor.getColumnIndex(GradeEntry.COLUMN_SUB_GRADES));
                Integer idLesson = cursor.getInt(cursor.getColumnIndex(GradeEntry.COLUMN_LESSON_ID));

                Grade gr = new Grade();
                gr.setIdGrade(id);
                gr.setNameGrade(name);
                gr.setPercent(percent);
                gr.setGrade(grade);
                gr.setSubGrades(subGrades);
                gr.setIdLesson(idLesson);
                grades.add(gr);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return grades;
    }

    public void dropTable(){
        db.execSQL("DELETE FROM " + LessonEntry.TABLE_NAME);
        db.execSQL("DELETE FROM " + GradeEntry.TABLE_NAME);
    }
}

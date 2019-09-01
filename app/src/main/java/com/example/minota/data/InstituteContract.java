package com.example.minota.data;

import android.provider.BaseColumns;

public class InstituteContract {

    public static final class LessonEntry implements BaseColumns {
        public static final String TABLE_NAME = "lesson";
        public static final String COLUMN_NAME = "name";
    }

    public static final class GradeEntry implements BaseColumns {
        public static final String TABLE_NAME = "grade";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PERCENT = "percent";
        public static final String COLUMN_GRADE = "grade";
        public static final String COLUMN_SUB_GRADES = "sub_grades";
        public static final String COLUMN_LESSON_ID = "id_grade";

    }
}

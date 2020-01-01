package com.example.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.control.DatabaseContract.*;
import androidx.annotation.Nullable;


public class TimetableHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "timetableList.db";
    public static final int DATABASE_VERSION = 1;

    public TimetableHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PERCENTS_TABLE = "CREATE TABLE " +
                TimetableEntry.TABLE_NAME + " (" +
                TimetableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TimetableEntry.COLUMN_DAY + " INTEGER NOT NULL, " +
                TimetableEntry.COLUMN_HOUR + " INTEGER NOT NULL, " +
                TimetableEntry.COLUMN_MINUTE + " INTEGER NOT NULL, " +
                TimetableEntry.COLUMN_VALUE + " INTEGER NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_PERCENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TimetableEntry.TABLE_NAME);
        onCreate(db);
    }
}

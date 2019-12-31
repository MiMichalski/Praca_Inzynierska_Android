package com.example.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.control.DatabaseContract.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "percentList.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PERCENTS_TABLE = "CREATE TABLE " +
            PercentsEntry.TABLE_NAME + " (" +
            PercentsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PercentsEntry.COLUMN_NAME + " INTEGER NOT NULL" +
            ");";
        db.execSQL(SQL_CREATE_PERCENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PercentsEntry.TABLE_NAME);
        onCreate(db);
    }
}

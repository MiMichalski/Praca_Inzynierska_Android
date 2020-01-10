package com.example.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

public class TimeKeeper implements Runnable {

    private SQLiteDatabase mDatabase;
    protected Context context;
    int dayC, day;

    public TimeKeeper(Context context){
        this.context = context.getApplicationContext();
    }




    @Override
    public void run() {
        TimetableHelper dbHelper = new TimetableHelper(context);
        mDatabase = dbHelper.getReadableDatabase();




    }

    public short day(){
        Calendar calendar = Calendar.getInstance();
        dayC = calendar.DAY_OF_WEEK;
        switch (dayC){
            case 1: return 6;
            case 2: return 0;
            case 3: return 1;
            case 4: return 2;
            case 5: return 3;
            case 6: return 4;
            case 7: return 5;
        }
        return 0;
    }


}

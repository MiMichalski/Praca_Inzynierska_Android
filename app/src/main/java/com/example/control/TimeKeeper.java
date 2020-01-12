package com.example.control;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.Calendar;

public class TimeKeeper implements Runnable {

    private SQLiteDatabase mDatabase;
    protected Context context;
    private int dayC;

    public TimeKeeper(Context context){
        this.context = context.getApplicationContext();
    }




    @Override
    public void run() {
        TimetableHelper dbHelper = new TimetableHelper(context);
        mDatabase = dbHelper.getReadableDatabase();


        short day;
        int hour, minute, dbHour, dbMinute, dbValue;

        while(true){
            day = day();
            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            dbHour = -1;
            dbMinute = -1;
            dbValue = 0;

            String query = "SELECT * FROM " + DatabaseContract.TimetableEntry.TABLE_NAME + " WHERE " +
                    DatabaseContract.TimetableEntry.COLUMN_HOUR + " = ? AND " +
                    DatabaseContract.TimetableEntry.COLUMN_MINUTE + " = ? AND " +
                    DatabaseContract.TimetableEntry.COLUMN_DAY + " = ?";
            Cursor cursor = mDatabase.rawQuery(query, new String[]{Integer.toString(hour), Integer.toString(minute), Integer.toString(day)});

            if (cursor.moveToFirst()) {
                dbHour =  Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseContract.TimetableEntry.COLUMN_HOUR)));
                dbMinute =  Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseContract.TimetableEntry.COLUMN_MINUTE)));
                dbValue =  Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseContract.TimetableEntry.COLUMN_VALUE)));
            }
            cursor.close();

            if(dbHour == hour && dbMinute == minute && MainActivity.modeAuto){
                ConnectionLib.sendWidth(Integer.toString(dbValue));
            }



            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private short day(){
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

package com.example.control;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TimetableActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private SQLiteDatabase mDatabase;
    private TimetableAdapter mAdapter;

    private static Spinner spinner;
    private TextView timeView;
    private EditText valueEdit;
    public static short day;
    private static int tHour, tMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        spinner = findViewById(R.id.timetable_spinner);
        timeView = findViewById(R.id.timetable_time);
        valueEdit = findViewById(R.id.timetable_value);

        TimetableHelper dbHelper = new TimetableHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.timetable_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TimetableAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = (short) position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void commandSetTime(View view){
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeView.setText(hourOfDay + ":" + minute);
        tHour = hourOfDay;
        tMin = minute;
    }

    private void addItem(){
        int value = Integer.parseInt(valueEdit.getText().toString());

        if(value < 0 || value > 100){
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.TimetableEntry.COLUMN_DAY, day);
        cv.put(DatabaseContract.TimetableEntry.COLUMN_HOUR, tHour);
        cv.put(DatabaseContract.TimetableEntry.COLUMN_MINUTE, tMin);
        cv.put(DatabaseContract.TimetableEntry.COLUMN_VALUE, value);

        mDatabase.insert(DatabaseContract.TimetableEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());
        valueEdit.getText().clear();
        timeView.setText("00:00");
        tHour = 0;
        tMin = 0;

    }

    private void removeItem(long id){
        mDatabase.delete(DatabaseContract.PercentsEntry.TABLE_NAME,
                DatabaseContract.PercentsEntry._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }

    public void commandAdd(View view){
        addItem();
    }


    private Cursor getAllItems(){
        return mDatabase.query(
                DatabaseContract.TimetableEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                (DatabaseContract.TimetableEntry.COLUMN_HOUR + DatabaseContract.TimetableEntry.COLUMN_MINUTE) + " DESC"
        );

    }
}

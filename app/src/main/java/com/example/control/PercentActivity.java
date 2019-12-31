package com.example.control;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PercentActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private DatabaseAdapter mAdapter;

    private Button addButton;
    private EditText editText;
    private String isInConfigMode = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DatabaseAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(isInConfigMode.equals("1")){
                    removeItem((long) viewHolder.itemView.getTag());
                } else {
                    String query = "SELECT "+ DatabaseContract.PercentsEntry.COLUMN_NAME + " FROM " + DatabaseContract.PercentsEntry.TABLE_NAME + " WHERE " + DatabaseContract.PercentsEntry._ID + " =" + viewHolder.itemView.getTag() ;
                    Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                    String value = "0";
                    Cursor  cursor = mDatabase.rawQuery(query,null);
                    if (cursor.moveToFirst()) {
                        value =  cursor.getString(cursor.getColumnIndex(DatabaseContract.PercentsEntry.COLUMN_NAME));
                    }
                    cursor.close();
                    ConnectionLib.sendWidth(value);
                    Toast.makeText(getApplicationContext(), getString(R.string.message_command_send) + value + "%", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).attachToRecyclerView(recyclerView);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.8), (int)(height*0.8));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isInConfigMode = extras.getString("mode");
        }

        addButton = findViewById(R.id.button6);
        editText = findViewById(R.id.editText2);

        if(isInConfigMode.equals("0")){
            addButton.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.INVISIBLE);
        }

    }

    private void addItem(){
        int value = Integer.parseInt(editText.getText().toString());
        if(value < 0 || value > 100){
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.PercentsEntry.COLUMN_NAME, value);

        mDatabase.insert(DatabaseContract.PercentsEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());
        editText.getText().clear();

    }

    private void removeItem(long id){
        mDatabase.delete(DatabaseContract.PercentsEntry.TABLE_NAME,
                DatabaseContract.PercentsEntry._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }

    public void commandAdd(View view){
        if(isInConfigMode.equals("0")){
            return;
        }
        addItem();
    }


    private Cursor getAllItems(){
        return mDatabase.query(
                DatabaseContract.PercentsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.PercentsEntry.COLUMN_NAME + " DESC"
        );

    }
}

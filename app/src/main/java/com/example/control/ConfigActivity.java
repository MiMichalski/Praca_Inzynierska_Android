package com.example.control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    public void commandPercentSettings(View view){
        Intent intent = new Intent(this, PercentActivity.class);
        intent.putExtra("mode", "1");
        startActivity(intent);
    }

    public void commandAutoSettings(View view){

    }
}

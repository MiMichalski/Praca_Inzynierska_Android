package com.example.control;
import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.concurrent.atomic.AtomicBoolean;


public class MainActivity extends AppCompatActivity{

    public static boolean hasAdminRights = false;
    public static volatile AtomicBoolean connectedToController = new AtomicBoolean(false);
    public static  boolean modeAuto = false;



    private ToggleButton mode;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Starts server thread
        Thread recServerThread = new Thread(new ServerListener());
        if(!recServerThread.isAlive()) {
            recServerThread.start();
        }

        //Starts auto mode
        Thread autoMode = new Thread(new TimeKeeper(getApplicationContext()));
        autoMode.start();


        //Starts toggle button listener
        mode = findViewById(R.id.toggleButton);
        mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    modeAuto = true;
                } else {
                    modeAuto = false;
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    public void commandOpen(View view) {
        if (connectedToController.get()) {
            ConnectionLib.sendWidth("100");
            Toast.makeText(this, getString(R.string.message_command_send) + " 100%", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.messageNC), Toast.LENGTH_LONG).show();
        }
        if(mode.isChecked()){ mode.setChecked(false); }
    }

    public void commandClose(View view){
        if(connectedToController.get()){
            ConnectionLib.sendWidth("0");
            Toast.makeText(this, getString(R.string.message_command_send) + " 0%", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.messageNC), Toast.LENGTH_LONG).show();
        }
        if(mode.isChecked()){ mode.setChecked(false); }
    }

    public void commandPercent(View view){
        if(connectedToController.get()){
            Intent intent = new Intent(this, PercentActivity.class);
            intent.putExtra("mode", "0");
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.messageNC), Toast.LENGTH_LONG).show();
        }
        if(mode.isChecked()){ mode.setChecked(false); }
    }

    public void commandSettings(View view){
        if(connectedToController.get()){
            if(hasAdminRights){
                Intent intent = new Intent(this, ConfigActivity.class);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, getString(R.string.messageNC), Toast.LENGTH_SHORT).show();
        }

    }

    public void prepare(){
        Handshake task = new Handshake();
        task.execute();
    }

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            switch (wifiStateExtra){
                case WifiManager.WIFI_STATE_ENABLED:
                    prepare();
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    connectedToController.set(false);
                    //recServerThread.interrupt();
                    break;
            }
        }
    };

    private class Handshake extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            int i = 0;
            while(i<5 && !connectedToController.get()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mWifi.isConnected()) {
                    ConnectionLib.handshake();
                    if(connectedToController.get()){return null;}  // Volatile Atomowy boolean nie działa...
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
            return null;
        }
    }
}

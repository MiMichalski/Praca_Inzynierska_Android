package com.example.control;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.ref.WeakReference;


public class LoginActivity extends AppCompatActivity {

    private EditText password;

    public static boolean newMsg = false;
    public static String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        password = findViewById(R.id.editText);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.8), (int)(height*0.4));


    }

    //On button press, starts login task
    public void commandLogin(View view){
        ConnectionLib.sendPassword(password.getText().toString());
        Toast.makeText(getApplicationContext(), getString(R.string.messageA), Toast.LENGTH_SHORT).show();
        AsyncLogin task = new AsyncLogin(this);
        task.execute();
    }

    //On incorrect password, informs user
    public void loginFailed(){
        Toast.makeText(this, getString(R.string.messageF), Toast.LENGTH_SHORT).show();
    }

    //On correct password, informs user, opens config menu
    public void loginSuccess(){
        Toast.makeText(this, getString(R.string.messageP), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
        MainActivity.hasAdminRights = true;
    }

    //On exceeding login time, informs user
    public void loginTimeout(){
        Toast.makeText(this, getString(R.string.messageT), Toast.LENGTH_SHORT).show();
    }

    private class AsyncLogin extends AsyncTask<Void, Void, Void>{
        private WeakReference<LoginActivity> activityWeakReference;
        AsyncLogin(LoginActivity activity){
            activityWeakReference = new WeakReference<LoginActivity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            LoginActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
        }
        @Override
        protected Void doInBackground(Void... voids) {
            int i =0;
            do {
                if(newMsg){
                    if(msg.equals("1")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loginSuccess();
                            }
                        });
                        break;
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loginFailed();
                            }
                        });
                        break;
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                if(i==49){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loginTimeout();
                        }
                    });
                    break;
                }
            }while(i<50);
            newMsg = false;
            return null;
        }
    }
}


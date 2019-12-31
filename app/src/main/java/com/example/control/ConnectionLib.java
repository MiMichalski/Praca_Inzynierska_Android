package com.example.control;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class ConnectionLib extends AsyncTask<String, Void, Void> {

    public static void sendMsg(String command, String parm, String tag){
        String message = command + "," + parm + "," + tag;
        ConnectionLib connectionLib = new ConnectionLib();
        connectionLib.execute(message);
    }
    //Sends message with given percent
    public static void sendWidth(String perc){
        String message = "set," + perc;
        ConnectionLib connectionLib = new ConnectionLib();
        connectionLib.execute(message);
    }
    //Attempts login
    public static void sendPassword(String pass){
        String message = "login," + pass;
        ConnectionLib connectionLib = new ConnectionLib();
        connectionLib.execute(message);
    }
    //Checks if connected access point is controller
    public static void handshake(){
        String message = "IsItYou?";
        if(MainActivity.connectedToController.get()){
            return;
        }
        ConnectionLib connectionLib = new ConnectionLib();
        connectionLib.execute(message);
    }


    @Override
    protected Void doInBackground(String... strings) {

        String message = strings[0];
        try {
            Socket s = new Socket("192.168.4.1", 8080);
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            pw.write(" " + message);
            pw.flush();
            pw.close();
            s.close();

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }
}


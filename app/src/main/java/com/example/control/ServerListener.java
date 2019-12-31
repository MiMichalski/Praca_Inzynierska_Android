package com.example.control;

import android.content.Context;
import android.os.Handler;
//import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener implements Runnable {

    Socket socket;
    ServerSocket serverSocket ;
    InputStreamReader inputStreamReader;
    BufferedReader bufferedReader;
    String message;
    Handler handler = new Handler();
    public static boolean running = true;

    //protected Context context;

    //public ServerListener(Context context){
    //    this.context = context.getApplicationContext();
    //}

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(8081);
            do {
                socket = serverSocket.accept();
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                message = bufferedReader.readLine();
                if(message != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            MessageProcessor messageProcessor = new MessageProcessor();
                            messageProcessor.processMessage(message);
                            //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //MainActivity.message = message;
            } while (running);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

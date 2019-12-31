package com.example.control;
import java.util.ArrayList;
import java.util.Arrays;

public class MessageProcessor {


    public void processMessage(final String message) {

        final ArrayList aList = new ArrayList(Arrays.asList(message.split(",")));
        String command = aList.get(0).toString().trim();
        switch (command) {
            case "ItsMe!":
                MainActivity.connectedToController.set(true);
                break;
            case "login":
                LoginActivity.newMsg = true;
                LoginActivity.msg = aList.get(1).toString().trim();
                break;
            case "pos":
                break;
            default:
                break;
        }
    }

}

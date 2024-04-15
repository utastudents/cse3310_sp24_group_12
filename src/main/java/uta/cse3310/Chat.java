package uta.cse3310;

import java.util.ArrayList;
import java.util.Scanner;

public class Chat {

    ArrayList<String> messages = new ArrayList<String>();

    Scanner input = new Scanner(System.in);
    // System.out.println("<Type a message. Press enter to send.>");
        

    // public void sendMessage() {
    //     while (true){
    //         String message = input.nextLine();
    //         message = message + "\n";
    //         messages.add(message);

    //         for (String element : arrayList) {
    //             broadcast(game);
    //         }
    //         messages.clear();
    //     }

    public void clearChat() {
        messages.clear();
    }

}

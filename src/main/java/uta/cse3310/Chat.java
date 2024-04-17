package uta.cse3310;

import java.util.ArrayList;
// import java.util.Scanner;

public class Chat {

     
    ArrayList<Message> chatLog;
    Message newMessage;
    public Chat(){
        this.chatLog = new ArrayList<Message>();
        // newMessage = new Message();
        // newMessage.sender = "Test";
        // newMessage.message = "This is a test String";
        // chatLog.add(newMessage);
    }

    public void addToChat(String send){
        newMessage = new Message();
        String[] message = send.split(" ");
        newMessage.sender = message[1];
        newMessage.message = message[2];
        chatLog.add(newMessage);
        
    }
    // Scanner input = new Scanner(System.in);
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

    // public void clearChat() {
    //     messages.clear();
    // }

}

package uta.cse3310;

import java.util.ArrayList;
// import java.util.Scanner;

public class Chat {

     
    ArrayList<Message> chatLog;
    private Message newMessage;
    public Chat(){
        this.chatLog = new ArrayList<Message>();
    }

    public void addToChat(String user, String message){
        newMessage = new Message();
        newMessage.sender = user;
        newMessage.message = message;
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

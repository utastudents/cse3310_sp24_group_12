package uta.cse3310;
import java.util.ArrayList;
import java.util.List;


public class LoginManager{
    public int currentGameSize = 0;
    private ArrayList<String> usernames;
    public Color playerColor;

    public LoginManager() {
        this.usernames = new ArrayList<>();
    }


    public boolean registerUser(String username) {
        if (usernames.contains(username)) {
            return false; // Username already exists
        }

        // Add the username to the list of registered users
        usernames.add(username);
        currentGameSize++;
        return true; // Successful registration
    }

    public List<String> getRegisteredUsers() {
        return new ArrayList<>(usernames); // Return a copy of the list to prevent modification
    }

    public boolean removeUser(String username) {
        // Check if the username exists
        if (!usernames.contains(username)) {
            return false; // Username not found
        }
        
        // Remove the username from the list of registered users
        usernames.remove(username);
        
        
        return true; // Successful removal
    }

}

    

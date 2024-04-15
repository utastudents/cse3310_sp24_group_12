package uta.cse3310;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class LoginManager{
    public int currentGameSize = 0;
    public Map<String, Color> usernames;

    public LoginManager() {
        this.usernames = new HashMap<>();
    }

    public boolean registerUser(String username) {
        if (usernames.keySet().contains(username)) {
            return false; // Username already exists
        }

        // Add the username to the list of registered users
        Color color;
        if (usernames.isEmpty()) {
            color = Color.RED; // Set the initial color to RED
        } else {
            // Get the largest color from the list
            Color largestColor = Color.RED;
            for (Color c : usernames.values()) {
                if (c.ordinal() > largestColor.ordinal()) {
                    largestColor = c;
                }
            }

            // Increment the largest color by 1
            color = Color.values()[largestColor.ordinal() + 1];
        }
        usernames.put(username, color);

        currentGameSize++;
        return true; // Successful registration
    }

    public List<String> getRegisteredUsers() {
        return new ArrayList<>(usernames.keySet()); 
    }

    public boolean removeUser(String username) {
        // Check if the username exists
        if (!usernames.keySet().contains(username)) {
            return false; // Username not found
        }
        
        // Remove the username from the list of registered users
        usernames.remove(username);
        
        
        return true; // Successful removal
    }

}

    

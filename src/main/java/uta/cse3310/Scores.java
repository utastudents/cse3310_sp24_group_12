package uta.cse3310;

import java.util.Map;
import java.util.HashMap;

public class Scores {

    // username, score
    Map<String, Integer> score = new HashMap<>();


    public void addNewPlayer(String username) {
        score.put(username, 0);
    }
    /**
     * Sets a player's score if it's a new entry. Adds to a player's score if they
     * already have an entry.
     * 
     * @param username    The player's username
     * @param playerScore The player's score
     */
    public void updateScore(String username, int playerScore) {
        Integer currentScore = score.get(username);
        if (currentScore != null)
            score.put(username, currentScore + playerScore);
        else
            score.put(username, playerScore);

    }

    /**
     * Fetches a player's score from the hasmap
     * 
     * @param username The player's username
     * @return The player's score
     */
    public int getScore(String username) {
        return score.get(username);
    }
}
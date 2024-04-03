package uta.cse3310;

import java.util.Map;
import java.util.HashMap;

public class Scores {

    // Player id, score
    private Map<Integer, Integer> score = new HashMap<>();

    /**
     * Sets a player's score if it's a new entry. Adds to a player's score if they
     * already have an entry.
     * 
     * @param playerId    The player's ID
     * @param playerScore The player's score
     */
    public void updateScore(int playerId, int playerScore) {
        Integer currentScore = score.get(playerId);
        if (currentScore != null)
            score.put(playerId, currentScore + playerScore);
        else
            score.put(playerId, playerScore);

    }

    /**
     * Fetches a player's score from the hasmap
     * 
     * @param playerId The player's ID
     * @return The player's score
     */
    public int getScore(int playerId) {
        return score.get(playerId);
    }
}
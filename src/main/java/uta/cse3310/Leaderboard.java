package uta.cse3310;

import java.util.HashMap;
import java.util.Map;

public class Leaderboard {
    private Map<String, Integer> score = new HashMap<>();

    Leaderboard(Map<String, Integer> scores) {
        score = scores;
        //code to be implemented later
    }
    

    // THIS METHOD WILL BE REQUIRED IF THE SCORE IS BEING UPDATED IN REALTIME.
    // That is, if we want to update the leaderboard immediately after a user finds a word. 
    public void updateLeaderboard(Map<String, Integer> scores) {
        score = scores;
    }

    public String generateLeaderboard() {
        // Go through each map (players username and their respective scores), sort it, generate a readable string and return it.

        //Define a stringbuilder to fill up
        StringBuilder formatScores = new StringBuilder();

        //Fill up the stringbuilder with the players and their scores
        for(Map.Entry<String, Integer> entry : scores.entrySet()){
            formatScores.append("Player: ").append(entry.getKey()).append("   Score: ").append(entry.getValue()).append("\n");
        }

        //return the formatted stringbuilder
        return formatScores.toString();
    }

    // Reset leaderboard not required since we will initiate a new leaderboard every time a new game is created. 
}

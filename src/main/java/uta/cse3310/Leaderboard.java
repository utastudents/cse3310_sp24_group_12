package uta.cse3310;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leaderboard {
    private Map<String, Integer> score = new HashMap<>();

    // Constructor to initialize the leaderboard with initial scores
    Leaderboard(Map<String, Integer> scores) {
        score = scores;
    }

    // Method to update the leaderboard with real-time scores
    public void updateLeaderboard(Map<String, Integer> scores) {
        score = scores;
    }

    // Method to generate a formatted leaderboard string
    public String generateLeaderboard() {
        StringBuilder formatScores = new StringBuilder();

        // Sort the scores by descending order
        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(score.entrySet());
        sortedScores.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Fill up the string builder with the players and their scores
        for (Map.Entry<String, Integer> entry : sortedScores) {
            formatScores.append("Player: ").append(entry.getKey()).append("   Score: ").append(entry.getValue()).append("\n");
        }

        // Return the formatted string
        return formatScores.toString();
    }
}

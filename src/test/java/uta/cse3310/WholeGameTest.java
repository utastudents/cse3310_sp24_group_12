package uta.cse3310;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/*Testing the whole game */

public class WholeGameTest
        extends TestCase {
    Game game = new Game();

    public WholeGameTest(String testName) {
        super(testName);

    }

    public static Test suite() {
        return new TestSuite(WholeGameTest.class);
    }

    // Here we will test if players navigate through the lobby
    public void testGame() {

        game.loginManager.registerUser("player1");
        assertEquals(1, game.loginManager.currentGameSize);
        game.loginManager.registerUser("player2");
        assertEquals(2, game.loginManager.currentGameSize);
        game.loginManager.registerUser("player3");
        assertEquals(3, game.loginManager.currentGameSize);
        game.loginManager.registerUser("player4");
        assertEquals(4, game.loginManager.currentGameSize);
        assertEquals(4, game.loginManager.getRegisteredUsers().size());


        assertEquals("player1", game.loginManager.getRegisteredUsers().get(0));
        assertEquals("player2", game.loginManager.getRegisteredUsers().get(1));
        assertEquals("player3", game.loginManager.getRegisteredUsers().get(2));
        assertEquals("player4", game.loginManager.getRegisteredUsers().get(3));
    

        game.StartGame();

        assertEquals(1, game.getGameState());

        assertEquals(2, game.playerToId(PlayerType.player_1));
        assertEquals(3, game.playerToId(PlayerType.player_2));
        assertEquals(4, game.playerToId(PlayerType.player_3));
        assertEquals(0, game.playerToId(PlayerType.NoPlayer));
        assertEquals(1, game.playerToId(PlayerType.LobbyPlayer));
    


        for (Word word : game.grid.wordsInGrid) {
            ArrayList<Point> points = word.getPoints();
            for (int i = 0; i < word.length; i++) {
                assertEquals(game.grid.grid[points.get(i).x][points.get(i).y].alphabet, word.word.charAt(i));
            }
        }
    

        Random random = new Random();
        List<String> users = game.loginManager.getRegisteredUsers();
        String[] usernamesArray = users.toArray(new String[users.size()]);
        
        HashMap<String, Integer> colors = new HashMap<>();
        colors.put("RED", 0);
        colors.put("GREEN", 1);
        colors.put("BLUE", 2);
        colors.put("ORANGE", 3);
        colors.put("YELLOW", 4);
        colors.put("PURPLE", 5);
        colors.put("WHITE", 6);

        for (Word word : game.grid.wordsInGrid) {
            ArrayList<Point> points = word.getPoints();
            String randomUser = usernamesArray[random.nextInt(users.size())];
            game.grid.checkWord(points.get(0).x, points.get(0).y, points.get(points.size() - 1).x,
                    points.get(points.size() - 1).y,
                    game.loginManager.usernames.get(randomUser));

            game.scores.updateScore(randomUser, 1);
            assertEquals((Integer) game.scores.getScore(randomUser), (Integer) game.scores.score.get(randomUser));

            for (int i = 0; i < word.length; i++) {
                assertEquals((Integer) colors.get(game.grid.grid[points.get(i).x][points.get(i).y].color),
                        (Integer) (game.loginManager.usernames.get(randomUser)).ordinal());
            }
        }
    

        game.chatLog.addToChat("player1", "Hello");
        game.chatLog.addToChat("player2", "Hi");
        game.chatLog.addToChat("player3", "Hey");
        game.chatLog.addToChat("player4", "Hola");

        assertEquals("Hello", game.chatLog.chatLog.get(0).message);
        assertEquals("Hi", game.chatLog.chatLog.get(1).message);
        assertEquals("Hey", game.chatLog.chatLog.get(2).message);
        assertEquals("Hola", game.chatLog.chatLog.get(3).message);
    

        game.leaderboard.updateLeaderboard(game.scores.score);

        //StringBuilder formatScores = new StringBuilder();
        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(game.scores.score.entrySet());
        sortedScores.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        
        StringBuilder expectedLeaderboardBuilder = new StringBuilder();
        // Fill up the stringbuilder with the players and their scores
        for (Map.Entry<String, Integer> entry : game.scores.score.entrySet()) {
            expectedLeaderboardBuilder.append("Player: ").append(entry.getKey()).append("   Score: ").append(entry.getValue())
                    .append("\n");
        }
        String expectedLeaderboard = expectedLeaderboardBuilder.toString();
        assertEquals(expectedLeaderboard, game.leaderboard.generateLeaderboard());
        

    }
}

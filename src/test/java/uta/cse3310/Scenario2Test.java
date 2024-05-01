package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * Scenario 2: A player creates a game and waits for other players to join. 
 * After two people have joined, they start the game. 
 * 
 * The first player finds an invalid word.
 * The second player finds an invalid word.
 * The first player finds an invalid word.
 * 
 * The first player is quick to find the first word.  
 * The first player finds their second word. 
 * 
 * The second player tries to steal the first player's word but fails.
 * 
 * The second player finds their first word.
 * 
 * The game is nearing its end now so the first player sends a message in chat boasting.
 * The second player responds.
 * 
 * The leaderboard is generated. Player 1 wins with 2 points and player 2 loses with 1 point.
 */
public class Scenario2Test
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     * @return
     */
    public Scenario2Test(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(Scenario2Test.class);
    }

    private String update(Game G, String msg) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        G.Update(msg);
        String jsonString = gson.toJson(G);
        return jsonString;
    }

    ////////////////////////////////////////////////////////////////////////////
    public void testScenario1() {
        Game game = new Game();
        int messageCount = 0;

        game.GameId = 1;
        game.latestPlayer = PlayerType.player_1;
        String msg = new String();
        String result = new String();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        msg = "{\"register\":{\"username\":\"testing1\"},\"gameid\":1}";
        result = update(game, msg);
        assertEquals("testing1", game.loginManager.getRegisteredUsers().get(0));

        msg = "{\"register\":{\"username\":\"testing2\"},\"gameid\":1}";
        result = update(game, msg);
        assertEquals("testing2", game.loginManager.getRegisteredUsers().get(0));

        msg = "startGame";
        result = update(game, msg);
        assertEquals(game.gameState, 1);

        msg = "startCountdown";
        result = update(game, msg);

        List<Word> wordList = new ArrayList<>(game.grid.wordsInGrid);

        int startx = 0;
        int starty = 0;
        int endx = 0;
        int endy = 1;
        String username = "testing1";

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, startx, starty, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[startx][starty].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, endx, endy, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[endx][endy].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format(
                "{\"startClick\":{\"x\":%d,\"y\":%d},\"endClick\":{\"x\":%d,\"y\":%d},\"gameid\":1,\"color\":\"%s\",\"username\":\"%s\"}",
                startx, starty, endx, endy, game.loginManager.usernames.get(username), username);
        result = update(game, msg);

        ArrayList<Point> points = new ArrayList<>(Arrays.asList(new Point(0, 0), new Point(0, 1)));
        for (Point p : points) {
            assertNotSame((String) game.grid.grid[p.x][p.y].color,
                    (String) game.loginManager.usernames.get(username).name());
        }

        startx = 2;
        starty = 2;
        endx = 2;
        endy = 3;
        username = "testing2";

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, startx, starty, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[startx][starty].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, endx, endy, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[endx][endy].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"startClick\":{\"x\":%d,\"y\":%d},\"endClick\":{\"x\":%d,\"y\":%d},\"gameid\":1,\"color\":\"%s\",\"username\":\"%s\"}",
                startx, starty, endx, endy, game.loginManager.usernames.get(username), username);
        result = update(game, msg);

        points = new ArrayList<>(Arrays.asList(new Point(2, 2), new Point(2, 3)));
        for (Point p : points) {
            assertNotSame((String) game.grid.grid[p.x][p.y].color,
                    (String) game.loginManager.usernames.get(username).name());
        }

        startx = 7;
        starty = 8;
        endx = 7;
        endy = 9;
        username = "testing1";

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, startx, starty, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[startx][starty].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, endx, endy, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[endx][endy].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"startClick\":{\"x\":%d,\"y\":%d},\"endClick\":{\"x\":%d,\"y\":%d},\"gameid\":1,\"color\":\"%s\",\"username\":\"%s\"}",
                startx, starty, endx, endy, game.loginManager.usernames.get(username), username);
        result = update(game, msg);

        points = new ArrayList<>(Arrays.asList(new Point(7, 8), new Point(7, 9)));
        for (Point p : points) {
            assertNotSame((String) game.grid.grid[p.x][p.y].color,
                    (String) game.loginManager.usernames.get(username).name());
        }

        Word word = wordList.get(0);
        startx = word.startx;
        starty = word.starty;
        endx = word.endx;
        endy = word.endy;
        username = "testing1";

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, startx, starty, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[startx][starty].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, endx, endy, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[endx][endy].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"startClick\":{\"x\":%d,\"y\":%d},\"endClick\":{\"x\":%d,\"y\":%d},\"gameid\":1,\"color\":\"%s\",\"username\":\"%s\"}",
                startx, starty, endx, endy, game.loginManager.usernames.get(username), username);
        result = update(game, msg);

        points = word.getPoints();
        for (Point p : points) {
            assertEquals((String) game.grid.grid[p.x][p.y].color,
                    (String) game.loginManager.usernames.get(username).name());
        }

        word = wordList.get(1);
        startx = word.startx;
        starty = word.starty;
        endx = word.endx;
        endy = word.endy;
        username = "testing1";

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, startx, starty, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[startx][starty].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, endx, endy, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[endx][endy].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"startClick\":{\"x\":%d,\"y\":%d},\"endClick\":{\"x\":%d,\"y\":%d},\"gameid\":1,\"color\":\"%s\",\"username\":\"%s\"}",
                startx, starty, endx, endy, game.loginManager.usernames.get(username), username);
        result = update(game, msg);

        points = word.getPoints();

        for (Point p : points) {
            assertEquals((String) game.grid.grid[p.x][p.y].color,
                    (String) game.loginManager.usernames.get(username).name());
        }

        List<Word> foundWords = new ArrayList<>(game.grid.foundWords);

        word = foundWords.get(0);
        startx = word.startx;
        starty = word.starty;
        endx = word.endx;
        endy = word.endy;
        username = "testing2";

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, startx, starty, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertNotSame((String) game.grid.grid[startx][starty].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, endx, endy, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertNotSame((String) game.grid.grid[endx][endy].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"startClick\":{\"x\":%d,\"y\":%d},\"endClick\":{\"x\":%d,\"y\":%d},\"gameid\":1,\"color\":\"%s\",\"username\":\"%s\"}",
                startx, starty, endx, endy, game.loginManager.usernames.get(username), username);
        result = update(game, msg);

        points = word.getPoints();

        for (Point p : points) {
            assertNotSame((String) game.grid.grid[p.x][p.y].color,
                    (String) game.loginManager.usernames.get(username).name());
        }

        word = wordList.get(2);
        startx = word.startx;
        starty = word.starty;
        endx = word.endx;
        endy = word.endy;
        username = "testing2";

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, startx, starty, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[startx][starty].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, endx, endy, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[endx][endy].color,
                (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"startClick\":{\"x\":%d,\"y\":%d},\"endClick\":{\"x\":%d,\"y\":%d},\"gameid\":1,\"color\":\"%s\",\"username\":\"%s\"}",
                startx, starty, endx, endy, game.loginManager.usernames.get(username), username);
        result = update(game, msg);

        points = word.getPoints();

        for (Point p : points) {
            assertEquals((String) game.grid.grid[p.x][p.y].color,
                    (String) game.loginManager.usernames.get(username).name());
        }

        msg = "{\"incoming\":{\"from\":\"testing1\"},\"details\":\"Get good loser HAHA\"}";
        result = update(game, msg);
        assertEquals("testing1", game.chatLog.chatLog.get(0).sender);
        assertEquals("Get good loser HAHA", game.chatLog.chatLog.get(0).message);
        messageCount++;

        msg = "{\"incoming\":{\"from\":\"testing2\"},\"details\":\"I'm trying!\"}";
        result = update(game, msg);
        assertEquals("testing2", game.chatLog.chatLog.get(1).sender);
        assertEquals("I'm trying!", game.chatLog.chatLog.get(1).message);
        messageCount++;

        assertEquals(2, messageCount);

        String leaderboard = game.leaderboard.generateLeaderboard();

        assertTrue(leaderboard.contains("Player: testing1   Score: 2"));
        assertTrue(leaderboard.contains("Player: testing2   Score: 1"));


    }
}

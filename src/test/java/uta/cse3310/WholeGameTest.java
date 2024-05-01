package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//import java.lang.annotation.Native;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class WholeGameTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     * @return
     */
    public WholeGameTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(WholeGameTest.class);
    }

    ////////////////////////////////////////////////////////////////////////////
    // This test is very close to a system level test.
    // Well, the system without the UI.
    // Inputs and Outputs are provided by JSON strings.
    //
    //
    // Should be able to test all of the logic in the program
    // with these tests.
    //
    // The challenge is doing it without repeating a lot of code, or making
    // it tightly coupled to the specific implementation.
    // To minimize coupling, the majority of the tests should deal with
    // json strings.
    ////////////////////////////////////////////////////////////////////////////
    // Routines to replace those in App.java
    ///////////////////////////////////////////////////////////////////////////

    private String update(Game G, String msg) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        G.Update(msg);
        String jsonString = gson.toJson(G);
        return jsonString;
    }

    ////////////////////////////////////////////////////////////////////////////
    public void testXWinGame() {
        Game game = new Game();

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
        
        Word word = wordList.get(0);
        int startx = word.startx;
        int starty = word.starty;
        int endx = word.endx;
        int endy = word.endy;
        String username = "testing1";

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, startx, starty, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[startx][starty].color, (String) game.loginManager.usernames.get(username).name());

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, endx, endy, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[endx][endy].color, (String) game.loginManager.usernames.get(username).name());

        msg = String.format(
                "{\"startClick\":{\"x\":%d,\"y\":%d},\"endClick\":{\"x\":%d,\"y\":%d},\"gameid\":1,\"color\":\"%s\",\"username\":\"%s\"}",
                startx, starty, endx, endy, game.loginManager.usernames.get(username), username);
        result = update(game, msg);

        ArrayList<Point> points = word.getPoints();
        for (Point p : points) {
            assertEquals((String) game.grid.grid[p.x][p.y].color,
                    (String) game.loginManager.usernames.get(username).name());
        }
        
        word = wordList.get(1);
        startx = word.startx;
        starty = word.starty;
        endx = word.endx;
        endy = word.endy;
        username = "testing2";

        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, startx, starty, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[startx][starty].color, (String) game.loginManager.usernames.get(username).name());

        // Testing invalid selection
        msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                username, endx, endy + 1, game.loginManager.usernames.get(username));
        result = update(game, msg);
        assertEquals((String) game.grid.grid[endx][endy + 1].color, (String) game.loginManager.usernames.get(username).name());

        msg = String.format(
                "{\"startClick\":{\"x\":%d,\"y\":%d},\"endClick\":{\"x\":%d,\"y\":%d},\"gameid\":1,\"color\":\"%s\",\"username\":\"%s\"}",
                startx, starty, endx, endy + 1, game.loginManager.usernames.get(username), username);
        result = update(game, msg);

        points = word.getPoints();
        for (Point p : points) {
                assertNotSame((String) game.grid.grid[p.x][p.y].color,
                                (String) game.loginManager.usernames.get(username).name());
        }
        
        for (Point p : points) {
                assertEquals((String) game.grid.grid[p.x][p.y].color,
                                (String) "white");
        }
        
        word = wordList.get(2);
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

        Leaderboard leaderboard = game.Leaderboard.getLeaderboard();
        





        // // > 7707 1 {\"YouAre\":\"XPLAYER\",\"GameId\":1}
        // // < 7746 *
        // // {\"Players\":\"XPLAYER\",\"CurrentTurn\":\"NOPLAYER\",\"Button\":[\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Waiting
        // // for other player to join\",\"\"],\"GameId\":1}

        // // > 17987 2 {\"YouAre\":\"OPLAYER\",\"GameId\":1}
        // // game.Players = PlayerType.OPLAYER;
        // game.StartGame();

        // msg = "{\"Players\":\"OPLAYER\",\"CurrentTurn\":\"XPLAYER\",\"Button\":[\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"You are X. Your turn\",\"You are O. Other players turn\"],\"GameId\":1}";
        // assertTrue(msg.indexOf("\"Msg\":[\"You are X. Your turn\"")>-1);
         
        // result = update(game, "{\"Button\":0,\"PlayerIdx\":\"XPLAYER\",\"GameId\":1}");
       
        // // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"OPLAYER\",\"Button\":[\"XPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Other
       
        // // Players Move.\",\"Your Move.\"],\"GameId\":1}
        // result = update(game,"{\"Button\":1,\"PlayerIdx\":\"OPLAYER\",\"GameId\":1}");
        // // > 24067 *
        // // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"XPLAYER\",\"Button\":[\"XPLAYER\",\"OPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Your
        // // Move.\",\"Other Players Move.\"],\"GameId\":1}
        // result = update(game,"{\"Button\":4,\"PlayerIdx\":\"XPLAYER\",\"GameId\":1}");
        // // > 25126 *
        // // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"OPLAYER\",\"Button\":[\"XPLAYER\",\"OPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"XPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Other
        // // Players Move.\",\"Your Move.\"],\"GameId\":1}
        // result = update(game,"{\"Button\":2,\"PlayerIdx\":\"OPLAYER\",\"GameId\":1}");
        // // > 26285 *
        // // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"XPLAYER\",\"Button\":[\"XPLAYER\",\"OPLAYER\",\"OPLAYER\",\"NOPLAYER\",\"XPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Your
        // // Move.\",\"Other Players Move.\"],\"GameId\":1}
        // result = update(game,"{\"Button\":8,\"PlayerIdx\":\"XPLAYER\",\"GameId\":1}");
        
        // assertTrue(result.indexOf("[\"You Win!\",\"You Lose!\"]")>-1);
       
        // // > 27683 *
        // // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"NOPLAYER\",\"Button\":[\"XPLAYER\",\"OPLAYER\",\"OPLAYER\",\"NOPLAYER\",\"XPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"XPLAYER\"],\"Msg\":[\"You
        // // Win!\",\"You Lose!\"],\"GameId\":1}

    }
}

package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

//import java.lang.annotation.Native;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LeaderboardTest
                extends TestCase {
        /**
         * Create the test case
         *
         * @param testName name of the test case
         * @return
         */
        public LeaderboardTest(String testName) {
                super(testName);
        }

        /**
         * @return the suite of tests being tested
         */
        public static Test suite() {
                return new TestSuite(LeaderboardTest.class);
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
        public void testLeaderboard() {
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
                
                assertEquals(game.scores.getScore("testing1"), 0);
                assertEquals(game.scores.getScore("testing2"), 0);

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

                ArrayList<Point> points = word.getPoints();
                for (Point p : points) {
                        assertEquals((String) game.grid.grid[p.x][p.y].color,
                                        (String) game.loginManager.usernames.get(username).name());
                }
                

                String leaderboard = game.leaderboard.generateLeaderboard();

                assertTrue(leaderboard.contains("Player: testing1   Score: 1"));
                assertTrue(leaderboard.contains("Player: testing2   Score: 0"));

                

        }
}

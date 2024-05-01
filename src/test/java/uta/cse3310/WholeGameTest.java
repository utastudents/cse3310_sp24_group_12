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
        public void testGame() {
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

                word = wordList.get(1);
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

                // Testing invalid selection
                msg = String.format("{\"click\":{\"username\":\"%s\",\"x\":%d,\"y\":%d,\"color\":\"%s\"},\"gameid\":1}",
                                username, endx, endy + 1, game.loginManager.usernames.get(username));
                result = update(game, msg);
                assertEquals((String) game.grid.grid[endx][endy + 1].color,
                                (String) game.loginManager.usernames.get(username).name());

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

                msg = String.format(
                                "{\"startClick\":{\"x\":%d,\"y\":%d},\"endClick\":{\"x\":%d,\"y\":%d},\"gameid\":1,\"color\":\"%s\",\"username\":\"%s\"}",
                                startx, starty, endx, endy, game.loginManager.usernames.get(username), username);
                result = update(game, msg);

                points = word.getPoints();
                for (Point p : points) {
                        assertEquals((String) game.grid.grid[p.x][p.y].color,
                                        (String) game.loginManager.usernames.get(username).name());
                }

                String leaderboard = game.leaderboard.generateLeaderboard();

                assertTrue(leaderboard.contains("Player: testing1   Score: 2"));
                assertTrue(leaderboard.contains("Player: testing2   Score: 0"));

                int messageCount = 0;

                msg = "{\"incoming\":{\"from\":\"testing1\"},\"details\":\"Hello\"}";
                result = update(game, msg);
                assertEquals("testing1", game.chatLog.chatLog.get(0).sender);
                assertEquals("Hello", game.chatLog.chatLog.get(0).message);
                messageCount++;

                msg = "{\"incoming\":{\"from\":\"testing2\"},\"details\":\"Hi\"}";
                result = update(game, msg);
                assertEquals("testing2", game.chatLog.chatLog.get(1).sender);
                assertEquals("Hi", game.chatLog.chatLog.get(1).message);
                messageCount++;

                msg = "{\"incoming\":{\"from\":\"testing1\"},\"details\":\"How are you?\"}";
                result = update(game, msg);
                assertEquals("testing1", game.chatLog.chatLog.get(2).sender);
                assertEquals("How are you?", game.chatLog.chatLog.get(2).message);
                messageCount++;

                msg = "{\"incoming\":{\"from\":\"testing2\"},\"details\":\"I'm good\"}";
                result = update(game, msg);
                assertEquals("testing2", game.chatLog.chatLog.get(3).sender);
                assertEquals("I'm good", game.chatLog.chatLog.get(3).message);
                messageCount++;

                msg = "{\"incoming\":{\"from\":\"testing1\"},\"details\":\"That's good\"}";
                result = update(game, msg);
                assertEquals("testing1", game.chatLog.chatLog.get(4).sender);
                assertEquals("That's good", game.chatLog.chatLog.get(4).message);
                messageCount++;

                assertEquals(messageCount, game.chatLog.chatLog.size());

        }
}

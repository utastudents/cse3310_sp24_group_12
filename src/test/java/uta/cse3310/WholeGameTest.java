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

                Word deterministicHorizontalWord = new Word("TESTING", 0, 0, 0, 6, 7);
                game.grid.wordsInGrid.add(deterministicHorizontalWord);

                startx = deterministicHorizontalWord.startx;
                starty = deterministicHorizontalWord.starty;
                endx = deterministicHorizontalWord.endx;
                endy = deterministicHorizontalWord.endy;
                username = "testing2";

                for (int i = starty; i <= endy; i++) {
                        game.grid.grid[0][i].alphabet = deterministicHorizontalWord.word.charAt(i);
                        result = update(game, msg);
                        assertEquals((char) game.grid.grid[startx][i].alphabet,
                                        (char) deterministicHorizontalWord.word.charAt(i));
                }

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

                points = deterministicHorizontalWord.getPoints();
                for (Point p : points) {
                        assertEquals((String) game.grid.grid[p.x][p.y].color,
                                        (String) game.loginManager.usernames.get(username).name());
                }

                game.grid.wordsInGrid.remove(deterministicHorizontalWord);
                game.grid.foundWords.remove(deterministicHorizontalWord);

                Word deterministicVerticalUpwardsWord = new Word("TESTING", 6, 0, 0, 0, 7);
                game.grid.wordsInGrid.add(deterministicVerticalUpwardsWord);
                startx = deterministicVerticalUpwardsWord.startx;
                starty = deterministicVerticalUpwardsWord.starty;
                endx = deterministicVerticalUpwardsWord.endx;
                endy = deterministicVerticalUpwardsWord.endy;
                username = "testing1";

                for (int i = startx; i >= endx; i--) {
                        game.grid.grid[i][starty].alphabet = deterministicVerticalUpwardsWord.word
                                        .charAt(deterministicVerticalUpwardsWord.length - i - 1);
                        result = update(game, msg);
                        assertEquals((char) game.grid.grid[i][starty].alphabet,
                                        (char) deterministicVerticalUpwardsWord.word.charAt(
                                                        deterministicVerticalUpwardsWord.length - i - 1));
                }

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

                points = deterministicVerticalUpwardsWord.getPoints();

                for (Point p : points) {
                        assertEquals((String) game.grid.grid[p.x][p.y].color,
                                        (String) game.loginManager.usernames.get(username).name());
                }

                game.grid.wordsInGrid.remove(deterministicVerticalUpwardsWord);
                game.grid.foundWords.remove(deterministicVerticalUpwardsWord);

                Word deterministicVerticalDownwardsWord = new Word("TESTING", 0, 6, 0, 0, 7);
                game.grid.wordsInGrid.add(deterministicVerticalDownwardsWord);
                startx = deterministicVerticalDownwardsWord.startx;
                starty = deterministicVerticalDownwardsWord.starty;
                endx = deterministicVerticalDownwardsWord.endx;
                endy = deterministicVerticalDownwardsWord.endy;
                username = "testing2";

                for (int i = startx; i <= endx; i++) {
                        game.grid.grid[i][starty].alphabet = deterministicVerticalDownwardsWord.word.charAt(i);
                        result = update(game, msg);
                        assertEquals((char) game.grid.grid[i][starty].alphabet,
                                        (char) deterministicVerticalDownwardsWord.word.charAt(i));
                }

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

                points = deterministicVerticalDownwardsWord.getPoints();

                for (Point p : points) {
                        assertEquals((String) game.grid.grid[p.x][p.y].color,
                                        (String) game.loginManager.usernames.get(username).name());
                }

                game.grid.wordsInGrid.remove(deterministicVerticalDownwardsWord);
                game.grid.foundWords.remove(deterministicVerticalDownwardsWord);

                Word deterministicDiagonalDownwardsWord = new Word("TESTING", 0, 6, 0, 6, 7);
                game.grid.wordsInGrid.add(deterministicDiagonalDownwardsWord);
                startx = deterministicDiagonalDownwardsWord.startx;
                starty = deterministicDiagonalDownwardsWord.starty;
                endx = deterministicDiagonalDownwardsWord.endx;
                endy = deterministicDiagonalDownwardsWord.endy;
                username = "testing1";

                for (int i = startx, j = starty; i <= endx && j <= endy; i++, j++) {
                        game.grid.grid[i][j].alphabet = deterministicDiagonalDownwardsWord.word.charAt(i);
                        result = update(game, msg);
                        assertEquals((char) game.grid.grid[i][j].alphabet,
                                        (char) deterministicDiagonalDownwardsWord.word.charAt(i));
                }

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

                points = deterministicDiagonalDownwardsWord.getPoints();

                for (Point p : points) {
                        assertEquals((String) game.grid.grid[p.x][p.y].color,
                                        (String) game.loginManager.usernames.get(username).name());
                }

                game.grid.wordsInGrid.remove(deterministicDiagonalDownwardsWord);
                game.grid.foundWords.remove(deterministicDiagonalDownwardsWord);

                Word deterministicDiagonalUpwardsWord = new Word("TESTING", 6, 0, 6, 0, 7);
                game.grid.wordsInGrid.add(deterministicDiagonalUpwardsWord);
                startx = deterministicDiagonalUpwardsWord.startx;
                starty = deterministicDiagonalUpwardsWord.starty;
                endx = deterministicDiagonalUpwardsWord.endx;
                endy = deterministicDiagonalUpwardsWord.endy;
                username = "testing2";

                for (int i = startx, j = starty; i >= endx && j >= endy; i--, j--) {
                        game.grid.grid[i][j].alphabet = deterministicDiagonalUpwardsWord.word
                                        .charAt(deterministicDiagonalUpwardsWord.length - i - 1);
                        result = update(game, msg);
                        assertEquals((char) game.grid.grid[i][j].alphabet,
                                        (char) deterministicDiagonalUpwardsWord.word
                                                        .charAt(deterministicDiagonalUpwardsWord.length - i - 1));
                }

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

                points = deterministicDiagonalUpwardsWord.getPoints();

                for (Point p : points) {
                        assertEquals((String) game.grid.grid[p.x][p.y].color,
                                        (String) game.loginManager.usernames.get(username).name());
                }

                String leaderboard = game.leaderboard.generateLeaderboard();
                assertTrue(leaderboard.contains("Player: testing1   Score: 4"));
                assertTrue(leaderboard.contains("Player: testing2   Score: 3"));

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

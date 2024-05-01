package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class ChatTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     * @return
     */
    public ChatTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ChatTest.class);
    }


    private String update(Game G, String msg) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        G.Update(msg);
        String jsonString = gson.toJson(G);
        return jsonString;
    }

    ////////////////////////////////////////////////////////////////////////////
    public void testChat() {
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




        // // > 7707 1 {\"YouAre\":\"XPLAYER\",\"GameId\":1}
        // // < 7746 *
        // //
        // {\"Players\":\"XPLAYER\",\"CurrentTurn\":\"NOPLAYER\",\"Button\":[\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Waiting
        // // for other player to join\",\"\"],\"GameId\":1}

        // // > 17987 2 {\"YouAre\":\"OPLAYER\",\"GameId\":1}
        // // game.Players = PlayerType.OPLAYER;
        // game.StartGame();

        // msg =
        // "{\"Players\":\"OPLAYER\",\"CurrentTurn\":\"XPLAYER\",\"Button\":[\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"You
        // are X. Your turn\",\"You are O. Other players turn\"],\"GameId\":1}";
        // assertTrue(msg.indexOf("\"Msg\":[\"You are X. Your turn\"")>-1);

        // result = update(game,
        // "{\"Button\":0,\"PlayerIdx\":\"XPLAYER\",\"GameId\":1}");

        // //
        // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"OPLAYER\",\"Button\":[\"XPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Other

        // // Players Move.\",\"Your Move.\"],\"GameId\":1}
        // result =
        // update(game,"{\"Button\":1,\"PlayerIdx\":\"OPLAYER\",\"GameId\":1}");
        // // > 24067 *
        // //
        // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"XPLAYER\",\"Button\":[\"XPLAYER\",\"OPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Your
        // // Move.\",\"Other Players Move.\"],\"GameId\":1}
        // result =
        // update(game,"{\"Button\":4,\"PlayerIdx\":\"XPLAYER\",\"GameId\":1}");
        // // > 25126 *
        // //
        // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"OPLAYER\",\"Button\":[\"XPLAYER\",\"OPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"XPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Other
        // // Players Move.\",\"Your Move.\"],\"GameId\":1}
        // result =
        // update(game,"{\"Button\":2,\"PlayerIdx\":\"OPLAYER\",\"GameId\":1}");
        // // > 26285 *
        // //
        // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"XPLAYER\",\"Button\":[\"XPLAYER\",\"OPLAYER\",\"OPLAYER\",\"NOPLAYER\",\"XPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Your
        // // Move.\",\"Other Players Move.\"],\"GameId\":1}
        // result =
        // update(game,"{\"Button\":8,\"PlayerIdx\":\"XPLAYER\",\"GameId\":1}");

        // assertTrue(result.indexOf("[\"You Win!\",\"You Lose!\"]")>-1);

        // // > 27683 *
        // //
        // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"NOPLAYER\",\"Button\":[\"XPLAYER\",\"OPLAYER\",\"OPLAYER\",\"NOPLAYER\",\"XPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"XPLAYER\"],\"Msg\":[\"You
        // // Win!\",\"You Lose!\"],\"GameId\":1}

    }
}

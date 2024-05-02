package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoginManagerTest extends TestCase {
    private LoginManager loginManager;

    public LoginManagerTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(LoginManagerTest.class);
    }

    private String update(Game G, String msg) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        G.Update(msg);
        String jsonString = gson.toJson(G);
        return jsonString;
    }

    public void testRegisterUser() {
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

        msg = "{\"register\":{\"username\":\"testing3\"},\"gameid\":1}";
        result = update(game, msg);
        assertEquals("testing3", game.loginManager.getRegisteredUsers().get(0));

        msg = "{\"register\":{\"username\":\"testing4\"},\"gameid\":1}";
        result = update(game, msg);
        assertEquals("testing4", game.loginManager.getRegisteredUsers().get(0));

        // Testing a username that is shorter than 3 characters
        msg = "{\"register\":{\"username\":\"testing4\"},\"gameid\":1}";
        result = update(game, msg);
        assertNotSame("te", game.loginManager.getRegisteredUsers().get(0));

        // Testing a duplicate username
        msg = "{\"register\":{\"username\":\"testing1\"},\"gameid\":1}";
        result = update(game, msg);
        assertNotSame("testing1", game.loginManager.getRegisteredUsers().get(0));

        assertNotSame(game.loginManager.usernames.get("testing1"), game.loginManager.usernames.get("testing2"));
        assertNotSame(game.loginManager.usernames.get("testing1"), game.loginManager.usernames.get("testing3"));
        assertNotSame(game.loginManager.usernames.get("testing1"), game.loginManager.usernames.get("testing4"));
        assertNotSame(game.loginManager.usernames.get("testing2"), game.loginManager.usernames.get("testing3"));
        assertNotSame(game.loginManager.usernames.get("testing2"), game.loginManager.usernames.get("testing4"));
        assertNotSame(game.loginManager.usernames.get("testing3"), game.loginManager.usernames.get("testing4"));
        

        List<String> registeredUsers = game.loginManager.getRegisteredUsers();
        assertEquals(4, registeredUsers.size());
        assertTrue(registeredUsers.contains("testing1"));
        assertTrue(registeredUsers.contains("testing2"));
        assertTrue(registeredUsers.contains("testing3"));
        assertTrue(registeredUsers.contains("testing4"));

    }
}

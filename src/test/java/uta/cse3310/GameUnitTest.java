package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class GameUnitTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GameUnitTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(GameUnitTest.class);
    }
    
    //////////////////////////////////////////////////////////////////////////
    // These are unit tests, to check methods in the Game class.
    //////////////////////////////////////////////////////////////////////////

    public void testGameFields() {
        Game game = new Game();
        game.loginManager.registerUser("player1");
        game.loginManager.registerUser("player2");
        game.loginManager.registerUser("player3");
        game.loginManager.registerUser("player4");
        assertEquals(4, game.loginManager.currentGameSize);
        assertEquals("player1", game.loginManager.getRegisteredUsers().get(0));
        assertEquals("player2", game.loginManager.getRegisteredUsers().get(1));
        assertEquals("player3", game.loginManager.getRegisteredUsers().get(2));
        assertEquals("player4", game.loginManager.getRegisteredUsers().get(3));
    }
}
// mvn -Dtest=WholeGameTest test
package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IntegrationUnitTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public IntegrationUnitTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(IntegrationUnitTest.class);
    }

    //////////////////////////////////////////////////////////////////////
    // These are integration tests / component tests.
    // Notice how they call methods directly. In the system tests, the
    // data in and out is json strings.
    //
    // (the program is very small, it is hard to differentiate between an
    // integration / component test and a system level test)
    //////////////////////////////////////////////////////////////////////

    public void singleGame(Game G) {
        G.loginManager.registerUser("player1");
        G.loginManager.registerUser("player2");
        G.loginManager.registerUser("player3");
        G.loginManager.registerUser("player4");
        assertEquals(4, G.loginManager.currentGameSize);
        assertEquals("player1", G.loginManager.getRegisteredUsers().get(0));
        assertEquals("player2", G.loginManager.getRegisteredUsers().get(1));
        assertEquals("player3", G.loginManager.getRegisteredUsers().get(2));
        assertEquals("player4", G.loginManager.getRegisteredUsers().get(3));
        G.StartGame();
    }

    public void testOneGame() {
        Game G = new Game();
        G.GameId = 1;
        G.latestPlayer = PlayerType.player_1;
        singleGame(G);
    }

    public void testTwoGames() {
        // this test does not do much,
        // but it seemed like something to
        // write quickly.
        Game G0 = new Game();
        G0.GameId = 1;
        G0.latestPlayer = PlayerType.player_1;

        Game G1 = new Game();
        G1.GameId = 2;
        G1.latestPlayer = PlayerType.player_1;

        singleGame(G0);
        singleGame(G1);
    }

    public void testThreeGames() {
        // this test does not do much,
        // but it seemed like something to
        // write quickly.
        Game G0 = new Game();
        G0.GameId = 1;
        G0.latestPlayer = PlayerType.player_1;

        Game G1 = new Game();
        G1.GameId = 2;
        G1.latestPlayer = PlayerType.player_1;

        Game G2 = new Game();
        G2.GameId = 3;
        G2.latestPlayer = PlayerType.player_1;

        singleGame(G0);
        singleGame(G1);
        singleGame(G2);
    }

    public void testFourGames() {
        // this test does not do much,
        // but it seemed like something to
        // write quickly.
        Game G0 = new Game();
        G0.GameId = 1;
        G0.latestPlayer = PlayerType.player_1;

        Game G1 = new Game();
        G1.GameId = 2;
        G1.latestPlayer = PlayerType.player_1;

        Game G2 = new Game();
        G2.GameId = 3;
        G2.latestPlayer = PlayerType.player_1;

        Game G3 = new Game();
        G3.GameId = 4;
        G3.latestPlayer = PlayerType.player_1;

        singleGame(G0);
        singleGame(G1);
        singleGame(G2);
        singleGame(G3);
    }
}

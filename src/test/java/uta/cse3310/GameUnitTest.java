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

    public void testDrawOrWinner() { // first create a game object

    }

}
// mvn -Dtest=WholeGameTest test
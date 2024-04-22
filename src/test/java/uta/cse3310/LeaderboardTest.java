package uta.cse3310;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LeaderboardTest extends TestCase{
    
    public LeaderboardTest(String testName){
        super(testName);
    }
    
    public static Test suite(){
        return new TestSuite(LeaderboardTest.class);
    }
    
    //test method
    public void testLeaderboard(){
        Map<String, Integer> scores = new HashMap<>();
        //Fill in map with sample players and scores
        scores.put("Jimi",   1);
        scores.put("Amogh",  2);
        scores.put("Alex" ,  3);
        scores.put("Tommy",  4);
        scores.put("Sneha",  5);
        scores.put("Landon", 6);
        
        //Define and call instance of method with test data
        Leaderboard leaderboard = new Leaderboard(scores);
        String generated = leaderboard.generateLeaderboard();
        
        //Check if outputs match
        assertTrue(generated.contains("Player: Jimi   Score: 1"));
        assertTrue(generated.contains("Player: Amogh   Score: 2"));
        assertTrue(generated.contains("Player: Alex   Score: 3"));
        assertTrue(generated.contains("Player: Tommy   Score: 4"));
        assertTrue(generated.contains("Player: Sneha   Score: 5"));
        assertTrue(generated.contains("Player: Landon   Score: 6"));
        
    }
}

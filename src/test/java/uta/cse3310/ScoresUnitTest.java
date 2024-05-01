package uta.cse3310;

import junit.framework.Assert;

public class ScoresUnitTest {

    public void testAddNewPlayer() {
        Scores scores = new Scores();
        scores.addNewPlayer("player1");
        Assert.assertEquals(0, scores.getScore("player1"));
    }

    public void testUpdateScoreExistingPlayer() {
        Scores scores = new Scores();
        scores.addNewPlayer("player1");
        scores.updateScore("player1", 50);
        Assert.assertEquals(50, scores.getScore("player1"));
        
        scores.updateScore("player1", 2);
        Assert.assertEquals(52, scores.getScore("player1"));
    }

    public void testUpdateScoreNewPlayer() {
        Scores scores = new Scores();
        scores.updateScore("player2", 100);
        Assert.assertEquals(100, scores.getScore("player2"));
    }

    public void testGetScore() {
        Scores scores = new Scores();
        scores.addNewPlayer("player1");
        scores.updateScore("player1", 75);
        Assert.assertEquals(75, scores.getScore("player1"));
    }
}

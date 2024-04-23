package uta.cse3310;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.Assert;


public class WordTest {

    public void testEquals() {
        Word word1 = new Word("test", 0, 3, 0, 0, 4);
        Word word2 = new Word("test", 0, 3, 0, 0, 4);
        Word word3 = new Word("hello", 1, 5, 0, 0, 5);

        // Test equality between two identical words
        Assert.assertTrue(word1.equals(word2));
        // Test equality between two different words
        Assert.assertFalse(word1.equals(word3));
    }

    public void testHashCode() {
        Word word1 = new Word("test", 0, 3, 0, 0, 4);
        Word word2 = new Word("test", 0, 3, 0, 0, 4);

        // Test that the hash codes are equal for identical words
        Assert.assertEquals(word1.hashCode(), word2.hashCode());
    }

    public void testPoints() {
        Word horizontalWord = new Word("test", 0, 3, 0, 0, 4);
        Word verticalWord = new Word("test", 0, 0, 0, 3, 4);
        Word diagonalWord = new Word("test", 0, 3, 0, 3, 4);

        // Test that the points are correctly generated for a  horizontal word
        Assert.assertEquals(horizontalWord.getPoints(), new ArrayList<Point>(
                Arrays.asList(new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0))));
        // Test that the points are correctly generated for a vertical word
        Assert.assertEquals(verticalWord.getPoints(), new ArrayList<Point>(
                Arrays.asList(new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3))));
        // Test that the points are correctly generated for a diagonal word
        Assert.assertEquals(diagonalWord.getPoints(), new ArrayList<Point>(
                Arrays.asList(new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3))));
        
    }
}

package uta.cse3310;

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
}

package uta.cse3310;

import junit.framework.Assert;

public class AlphabetUnitTest {
    public void testAlphabetFields() {
        char letter = 'A';
        PlayerType player = PlayerType.player_1;
        String color = "red";

        Alphabet alphabet = new Alphabet();
        alphabet.alphabet = letter;
        alphabet.player = player;
        alphabet.color = color;

        Assert.assertEquals(letter, alphabet.alphabet);
        Assert.assertEquals(player, alphabet.player);
        Assert.assertEquals(color, alphabet.color);
    }
}

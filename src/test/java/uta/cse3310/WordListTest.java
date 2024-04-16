package uta.cse3310;

import java.io.File;
import java.io.IOException;
import junit.framework.Assert;

public class WordListTest {

    public void testLoadWords() {
        WordList wordList = new WordList();
        try {
            wordList.loadWords();
            Assert.assertFalse(wordList.wordList.isEmpty());
        } catch (IOException e) {
            Assert.fail("Exception should not be thrown when loading words");
        }
    }

    public void testGetRandomWord() {
        WordList wordList = new WordList();
        try {
            wordList.loadWords();
            String randomWord1 = wordList.getRandomWord();
            String randomWord2 = wordList.getRandomWord();

            Assert.assertNotNull(randomWord1);
            Assert.assertNotNull(randomWord2);
            Assert.assertFalse(randomWord1.equals(randomWord2));
        } catch (IOException e) {
            Assert.fail("Exception should not be thrown when loading words");
        }
    }

    public void testWordListFileExists() {
        File file = new File("words.txt");
        Assert.assertTrue(file.exists());
    }
}

package uta.cse3310;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class WordList {
    ArrayList<String> wordList = new ArrayList<>();
    ArrayList<String> usedWordList = new ArrayList<>();

    /**
     * Loads all the words from words.txt into WordList
     * 
     * @throws IOException
     */
    public void loadWords() throws IOException {
        String path = "../../../words.txt";
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;

        while (((line = br.readLine()) != null)) {
            wordList.add(line.strip());
        }

    }

    /**
     * Selects a word from the wordList arraylist. Ensures that the word has not
     * been selected before by looking at the usedWordList arraylist.
     * 
     * @return A randomly selected word (string)
     */
    public String getRandomWord() {
        Random random = new Random();

        int randomIndex = random.nextInt(wordList.size());
        String word = wordList.get(randomIndex);

        // Ensuring that there are no duplicate words.
        while (usedWordList.contains(word)) {
            randomIndex = random.nextInt(wordList.size());
            word = wordList.get(randomIndex);
        }
        return word;
    }
}

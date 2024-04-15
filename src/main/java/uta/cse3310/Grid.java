package uta.cse3310;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public class Grid {
  // class atributes
  public Alphabet[][] grid;
  private HashSet<Word> wordsInGrid;
  private HashSet<Word> foundWords;

  // methods
  public void createGrid() {
    WordList wordList = new WordList();
    try {
      wordList.loadWords();
    } catch (IOException e) {
      e.printStackTrace();
    }

    wordsInGrid = new HashSet<>();
    foundWords = new HashSet<>();

    populateGrid(50);
  }
  public void populateGrid(int size) {
    grid = new Alphabet[size][size];
    Random random = new Random();

    // Fill the grid with random characters
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        grid[i][j] = new Alphabet();
        grid[i][j].player = PlayerType.NoPlayer;
        grid[i][j].alphabet = (char) (random.nextInt(26) + 'a');
      }
    }

    // Sprinkle words randomly in the grid
    WordList wordList = new WordList();
    try {
      wordList.loadWords();
    } catch (IOException e) {
      e.printStackTrace();
    }

    wordsInGrid = new HashSet<>();
    foundWords = new HashSet<>();

    for (String word : wordList.wordList) {
      int wordLength = word.length();
      int startX = random.nextInt(size - wordLength + 1);
      int startY = random.nextInt(size - wordLength + 1);
      int endX = startX + wordLength - 1;
      int endY = startY + wordLength - 1;

      // Check if the word overlaps with any existing words
      boolean overlaps = false;
      for (Word existingWord : wordsInGrid) {
        if (startX <= existingWord.endx && endX >= existingWord.startx &&
            startY <= existingWord.endy && endY >= existingWord.starty) {
          overlaps = true;
          break;
        }
      }

      // If the word doesn't overlap, add it to the grid
      if (!overlaps) {
        // Determine the orientation of the word
        int orientation = random.nextInt(5);
        switch (orientation) {
          case 0: // Horizontal
            for (int i = startX; i <= endX; i++) {
              grid[i][startY].alphabet = word.charAt(i - startX);
            }
            break;
          case 1: // Vertical upward
            for (int i = startY; i <= endY; i++) {
              grid[startX][i].alphabet = word.charAt(i - startY);
            }
            break;
          case 2: // Vertical downward
            for (int i = startY; i <= endY; i++) {
              grid[startX][i].alphabet = word.charAt(endY - i);
            }
            break;
          case 3: // Diagonal upward
            for (int i = startX, j = startY; i <= endX && j <= endY; i++, j++) {
              grid[i][j].alphabet = word.charAt(i - startX);
            }
            break;
          case 4: // Diagonal downward
            for (int i = startX, j = startY; i <= endX && j <= endY; i++, j++) {
              grid[i][j].alphabet = word.charAt(endX - i);
            }
            break;
        }

        // Create a Word object and add it to the wordsInGrid set
        Word wordObject = new Word(word, startX, endX, startY, endY, wordLength);
        wordsInGrid.add(wordObject);

        // Check if 80% of the grid is filled with valid words
        if (wordsInGrid.size() >= (size * size) * 0.8) {
          break;
        }
      }
    }
  }

  public void highlightWord(int startx, int starty, int endx, int endy) {
    // Set playertype to player
  }

  public boolean checkWord(int startx, int starty, int endx, int endy) {
    if (horizontal(startx, endx, starty, endy) || verticalUpward(startx, endx, starty, endy) ||
        verticalDownward(startx, endx, starty, endy) || diagonalDownward(startx, starty, endx, endy) ||
        diagonalUpward(startx, starty, endx, endy)) {
      return true;
    }
    return false;
  }

  // In the case of horizontal, y value will remain the same.
  public boolean horizontal(int startx, int endx, int starty, int endy) {
    if (starty != endy) {
      return false;
    }
    int y = starty;
    StringBuilder currentWord = new StringBuilder();

    for (int i = startx; i <= endx; i++) {
      currentWord.append(grid[i][y]);
    }

    Word wordObject = new Word(currentWord.toString(), startx, endx, y, y, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);
      highlightWord(startx, y, endx, y);
      return true;
    }

    return false;
  }

  // Vertical upward, x will remain the same.
  public boolean verticalUpward(int startx, int endx, int starty, int endy) {
    if (startx != endx) {
      return false;
    }
    int x = startx;
    StringBuilder currentWord = new StringBuilder();

    // Used endy as the starting point because it's going from down to up.
    for (int i = endy; i >= starty; i--) {
      currentWord.append(grid[x][i]);
    }

    Word wordObject = new Word(currentWord.toString(), x, x, endy, starty, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);
      highlightWord(x, endy, x, starty);
      return true;
    }

    return false;
  }

  public boolean verticalDownward(int startx, int endx, int starty, int endy) {
    if (startx != endx) {
      return false;
    }
    int x = startx;

    StringBuilder currentWord = new StringBuilder();

    for (int i = starty; i <= endy; i++) {
      currentWord.append(grid[x][i]);
    }

    Word wordObject = new Word(currentWord.toString(), x, x, starty, endy, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);
      highlightWord(x, starty, x, endy);
      return true;
    }

    return false;
  }

  public boolean diagonalDownward(int startx, int starty, int endx, int endy) {
    StringBuilder currentWord = new StringBuilder();

    for (int i = startx, j = starty; i <= endx && j <= endy; i++, j++) {
      currentWord.append(grid[i][j]);
    }

    Word wordObject = new Word(currentWord.toString(), startx, endx, starty, endy, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);
      highlightWord(startx, starty, endx, endy);
      return true;
    }

    return false;
  }

  public boolean diagonalUpward(int startx, int starty, int endx, int endy) {
    StringBuilder currentWord = new StringBuilder();

    for (int i = endx, j = endy; i >= startx && i >= starty; i--, j--) {
      currentWord.append(grid[i][j]);
    }

    Word wordObject = new Word(currentWord.toString(), endx, startx, endy, starty, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);
      highlightWord(endx, endy, startx, starty);
      return true;
    }

    return false;
  }

}

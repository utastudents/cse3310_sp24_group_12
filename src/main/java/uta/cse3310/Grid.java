package uta.cse3310;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.awt.Point;

public class Grid {
  // class atributes
  public Alphabet[][] grid;
  HashSet<Word> wordsInGrid;
  HashSet<Word> foundWords;

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

    populateGrid(20);
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
        grid[i][j].color = "white";
      }
    }

    WordList wordList = new WordList();
    try {
      wordList.loadWords();
    } catch (IOException e) {
      e.printStackTrace();
    }

    wordsInGrid = new HashSet<>();
    foundWords = new HashSet<>();

    int wordCount = 0;

    while (wordCount < (size * size) * 0.67) {
      String word = wordList.getRandomWord(); // Get a random word from the word list
      int wordLength = word.length();

      if (wordLength > size) {
        continue;
      }

      // Determine random start coordinates within the grid bounds
      int startX = random.nextInt(size - wordLength);
      int startY = random.nextInt(size - wordLength);

      // Determine random orientation (0: horizontal, 1: vertical upward, 2: vertical
      // downward, 3: diagonal upward, 4: diagonal downward)
      int orientation = random.nextInt(5);

      int endX = startX, endY = startY;

      switch (orientation) {
        case 0: // Horizontal: X Remains the same, Y increases
          endY = startY + wordLength - 1;
          endX = startX;
          break;
        case 1: // Vertical upward: Y Remains the same, X decreases
          endX = startX - wordLength + 1;
          endY = startY;
          break;
        case 2: // Vertical downward: Y Remains the same, X increases
          endX = startX + wordLength - 1;
          endY = startY;
          break;
        case 3: // Diagonal upward: X and Y decrease
          endX = startX - wordLength + 1;
          endY = startY - wordLength + 1;
          break;
        case 4: // Diagonal downward: X and Y increase
          endX = startX + wordLength - 1;
          endY = startY + wordLength - 1;
          break;

      }
      
      if (endX >= 0 && endX < size && endY >= 0 && endY < size) {

        // Create a Word object and add it to the wordsInGrid set
        Word wordObject = new Word(word, startX, endX, startY, endY, wordLength);

        // Check if the word overlaps with any existing words
        ArrayList<Point> newWordCoordinates = wordObject.getPoints();

        boolean overlaps = false;
        for (Word existingWord : wordsInGrid) {
          ArrayList<Point> wordCoordinates = existingWord.getPoints();

          for (Point point : newWordCoordinates) {
            if (wordCoordinates.contains(point)) {
              overlaps = true;
              break;
            }
          }
        }
        
        // If the word doesn't overlap, add it to the grid
        if (!overlaps) {
          for (int i = 0; i < wordLength; i++) {
            int x = startX, y = startY;
            switch (orientation) {
              case 0: // Horizontal
                y = startY + i;
                break;
              case 1: // Vertical upward
                x = startX - i;
                break;
              case 2: // Vertical downward
                x = startX + i;
                break;
              case 3: // Diagonal upward
                x = startX - i;
                y = startY - i;
                break;
              case 4: // Diagonal downward
                x = startX + i;
                y = startY + i;
                break;
            }
            grid[x][y] = new Alphabet();
            grid[x][y].alphabet = word.charAt(i);
            grid[x][y].player = PlayerType.NoPlayer;
            grid[x][y].color = "white";
          }

          wordsInGrid.add(wordObject);
          wordCount += wordLength;

          // Check if 80% of the grid is filled with valid words
          if (wordCount >= (size * size) * 0.6) {
            break;
          }
        }
      }
    }

  }

  public void colorIn(int x, int y, Color color) {
    grid[x][y].color = color.name();
    return;
  }

  public void resetColor(int x, int y) {
    grid[x][y].color = "white";
  }

  public boolean checkWord(int startx, int starty, int endx, int endy, Color color) {
    if (horizontal(startx, endx, starty, endy, color) || verticalUpward(startx, endx, starty, endy, color) ||
        verticalDownward(startx, endx, starty, endy, color) || diagonalDownward(startx, starty, endx, endy, color) ||
        diagonalUpward(startx, starty, endx, endy, color)) {
      return true;
    }
    return false;
  }

  // In the case of horizontal, x value will remain the same.
  public boolean horizontal(int startx, int endx, int starty, int endy, Color color) {
    if (startx != endx) {
      return false;
    }
    int x = startx;
    StringBuilder currentWord = new StringBuilder();

    for (int i = starty; i <= endy; i++) {
      currentWord.append(grid[x][i].alphabet);
    }

    Word wordObject = new Word(currentWord.toString(), x, x, starty, endy, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);

      for (int i = starty; i <= endy; i++) {
        grid[x][i].color = color.name();
      }

      return true;
    }

    return false;
  }

  // Vertical upward, y will remain the same.
  public boolean verticalUpward(int startx, int endx, int starty, int endy, Color color) {
    if (starty != endy) {
      return false;
    }
    int y = starty;
    StringBuilder currentWord = new StringBuilder();

    // Used endy as the starting point because it's going from down to up.
    for (int i = startx; i >= endx; i--) {
      currentWord.append(grid[i][y].alphabet);
    }

    Word wordObject = new Word(currentWord.toString(), startx, endx, y, y, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);

      for (int i = startx; i >= endx; i--) {
        grid[i][y].color = color.name();
      }

      return true;
    }

    return false;
  }

  public boolean verticalDownward(int startx, int endx, int starty, int endy, Color color) {
    if (starty != endy) {
      return false;
    }
    int y = starty;

    StringBuilder currentWord = new StringBuilder();

    for (int i = startx; i <= endx; i++) {
      currentWord.append(grid[i][y].alphabet);
    }

    Word wordObject = new Word(currentWord.toString(), startx, endx, y, y, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);

      for (int i = startx; i <= endx; i++) {
        grid[i][y].color = color.name();
      }

      return true;
    }

    return false;
  }

  public boolean diagonalDownward(int startx, int starty, int endx, int endy, Color color) {
    StringBuilder currentWord = new StringBuilder();

    for (int i = startx, j = starty; i <= endx && j <= endy; i++, j++) {
      currentWord.append(grid[i][j].alphabet);
    }

    Word wordObject = new Word(currentWord.toString(), startx, endx, starty, endy, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);

      for (int i = startx, j = starty; i <= endx && j <= endy; i++, j++) {
        grid[i][j].color = color.name();
      }

      return true;
    }

    return false;
  }

  public boolean diagonalUpward(int startx, int starty, int endx, int endy, Color color) {
    StringBuilder currentWord = new StringBuilder();
    for (int i = endx, j = endy; i <= startx && j <= starty; i++, j++) {
      currentWord.append(grid[i][j].alphabet);
    }
    currentWord = currentWord.reverse();

    Word wordObject = new Word(currentWord.toString(), startx, endx, starty, endy, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);

      for (int i = endx, j = endy; i <= startx && j <= starty; i++, j++) {
        grid[i][j].color = color.name();
      }

      return true;
    }

    return false;
  }

}

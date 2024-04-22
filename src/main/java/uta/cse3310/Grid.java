package uta.cse3310;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

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

    while (wordCount < (size * size) * 0.8) {
      String word = wordList.getRandomWord(); // Get a random word from the word list
      int wordLength = word.length();

      // Determine random start coordinates within the grid bounds
      int startX = random.nextInt(size - wordLength);
      int startY = random.nextInt(size - wordLength);

      // Determine random orientation (0: horizontal, 1: vertical upward, 2: vertical
      // downward, 3: diagonal upward, 4: diagonal downward)
      int orientation = random.nextInt(5);

      int endX, endY;

      switch (orientation) {
        case 0: // Horizontal
          endX = startX + wordLength - 1;
          endY = startY;
          break;
        case 1: // Vertical upward
          endX = startX;
          endY = startY - wordLength + 1;
          break;
        case 2: // Vertical downward
          endX = startX;
          endY = startY + wordLength - 1;
          break;
        case 3: // Diagonal upward
          endX = startX - wordLength + 1;
          endY = startY - wordLength + 1;
          break;
        case 4: // Diagonal downward
          endX = startX + wordLength - 1;
          endY = startY + wordLength - 1;
          break;
        default:
          endX = startX;
          endY = startY;
          break;
      }

      if (endX >= 0 && endX < size && endY >= 0 && endY < size) {
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
          for (int i = 0; i < wordLength; i++) {
            int x = startX, y = startY;
            switch (orientation) {
              case 0: // Horizontal
                x = startX + i;
                break;
              case 1: // Vertical upward
                y = startY - i;
                break;
              case 2: // Vertical downward
                y = startY + i;
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

          // Create a Word object and add it to the wordsInGrid set
          Word wordObject = new Word(word, startX, endX, startY, endY, wordLength);
          wordsInGrid.add(wordObject);
          wordCount+= wordLength;

          // Check if 80% of the grid is filled with valid words
          if (wordCount >= (size * size) * 0.8) {
            break;
          }
        }
      }
    }
  }

  public void colorIn(int x, int y, String color, Alphabet[][] grid) {
    grid[x][y].color = color;
  }

  public void resetColor(int x, int y, Alphabet[][] grid) {
    grid[x][y].color = "white";
  }

  public void highlightWord(int startx, int starty, int endx, int endy) {
    System.out.println("Word highlighted.");
  }

  public boolean checkWord(int startx, int starty, int endx, int endy) {
    if (horizontal(startx, endx, starty, endy) || verticalUpward(startx, endx, starty, endy) ||
        verticalDownward(startx, endx, starty, endy) || diagonalDownward(startx, starty, endx, endy) ||
        diagonalUpward(startx, starty, endx, endy)) {
      return true;
    }
    return false;
  }

  // In the case of horizontal, x value will remain the same.
  public boolean horizontal(int startx, int endx, int starty, int endy) {
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
      highlightWord(x, starty, x, endy);
      return true;
    }

    return false;
  }

  // Vertical upward, y will remain the same.
  public boolean verticalUpward(int startx, int endx, int starty, int endy) {
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
      highlightWord(startx, y, endx, y);
      return true;
    }

    return false;
  }

  public boolean verticalDownward(int startx, int endx, int starty, int endy) {
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
      highlightWord(startx, y, endx, y);
      return true;
    }

    return false;
  }

  public boolean diagonalDownward(int startx, int starty, int endx, int endy) {
    StringBuilder currentWord = new StringBuilder();

    for (int i = startx, j = starty; i <= endx && j <= endy; i++, j++) {
      currentWord.append(grid[i][j].alphabet);
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

    for (int i = startx, j = starty; i >= endx && i >= endy; i--, j--) {
      currentWord.append(grid[i][j].alphabet);
    }

    Word wordObject = new Word(currentWord.toString(), startx, endx, starty, endy, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);
      highlightWord(startx, starty, endx, endy);
      return true;
    }

    return false;
  }

}

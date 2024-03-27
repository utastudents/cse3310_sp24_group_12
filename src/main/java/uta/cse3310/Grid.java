package uta.cse3310;

import java.util.HashSet;

public class Grid {
  // class atributes
  public char[][] grid;
  private HashSet<Word> wordsInGrid;
  private HashSet<Word> foundWords;

  // methods
  public void createGrid() {
    // code to be implemented later
  }

  public void highlightWord(Word word) {

    // NEED TO FIGURE OUT A WAY TO VISUALLY MARK A WORD AS FOUND.
    // code to be implemented later
  }

  // In the case of horizontal, y value will remain the same.
  public boolean horizontal(int startx, int endx, int y) {
    StringBuilder currentWord = new StringBuilder();

    for (int i = startx; i <= endx; i++) {
      currentWord.append(grid[i][y]);
    }

    Word wordObject = new Word(currentWord.toString(), startx, endx, y, y, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);
      highlightWord(wordObject);
      return true;
    }

    return false;
  }

  // Vertical upward, x will remain the same.
  public boolean verticalUpward(int x, int starty, int endy) {
    StringBuilder currentWord = new StringBuilder();

    // Used endy as the starting point because it's going from down to up.
    for (int i = endy; i >= starty; i--) {
      currentWord.append(grid[x][i]);
    }

    Word wordObject = new Word(currentWord.toString(), x, x, endy, starty, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);
      highlightWord(wordObject);
      return true;
    }

    return false;
  }

  public boolean verticalDownward(int x, int starty, int endy) {
    StringBuilder currentWord = new StringBuilder();

    for (int i = starty; i <= endy; i++) {
      currentWord.append(grid[x][i]);
    }

    Word wordObject = new Word(currentWord.toString(), x, x, starty, endy, currentWord.toString().length());

    if (wordsInGrid.contains(wordObject) && !foundWords.contains(wordObject)) {
      foundWords.add(wordObject);
      highlightWord(wordObject);
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
      highlightWord(wordObject);
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
      highlightWord(wordObject);
      return true;
    }

    return false;
  }

}

package uta.cse3310;

import junit.framework.Assert;

public class GridUnitTest {

    public void testCreateGrid() {
        Grid grid = new Grid();
        grid.createGrid();
        Assert.assertNotNull(grid.grid);
    }

    public void testCheckWordHorizontal() {
        Grid grid = new Grid();
        grid.createGrid();
        grid.grid[0][0] = new Alphabet();
        grid.grid[0][1] = new Alphabet();
        grid.grid[0][2] = new Alphabet();
        grid.grid[0][3] = new Alphabet();

        grid.grid[0][0].alphabet = 't';
        grid.grid[0][1].alphabet = 'e';
        grid.grid[0][2].alphabet = 's';
        grid.grid[0][3].alphabet = 't';
        Word word = new Word("test", 0, 0, 0, 3, 4);
        grid.wordsInGrid.add(word);
        // Assuming a word "test" is present horizontally at positions (0,0) to (0,3)
        Assert.assertTrue(grid.checkWord(0, 0, 0, 3, Color.RED));
    }

    public void testCheckWordVerticalUpward() {
        Grid grid = new Grid();
        grid.createGrid();
        grid.grid[3][3] = new Alphabet();
        grid.grid[2][3] = new Alphabet();
        grid.grid[1][3] = new Alphabet();
        grid.grid[0][3] = new Alphabet();

        grid.grid[3][3].alphabet = 't';
        grid.grid[2][3].alphabet = 'e';
        grid.grid[1][3].alphabet = 's';
        grid.grid[0][3].alphabet = 't';
        Word word = new Word("test", 3, 0, 3, 3, 4);
        grid.wordsInGrid.add(word);
        // Assuming a word "test" is present vertically upward at positions (3,3) to
        // (0,3)
        Assert.assertTrue(grid.checkWord(3, 3, 0, 3, Color.RED));
    }

    public void testCheckWordVerticalDownward() {
        Grid grid = new Grid();
        grid.createGrid();
        grid.grid[0][0] = new Alphabet();
        grid.grid[1][0] = new Alphabet();
        grid.grid[2][0] = new Alphabet();
        grid.grid[3][0] = new Alphabet();
        
        grid.grid[0][0].alphabet = 't';
        grid.grid[1][0].alphabet = 'e';
        grid.grid[2][0].alphabet = 's';
        grid.grid[3][0].alphabet = 't';
        Word word = new Word("test", 0, 3, 0, 0, 4);
        grid.wordsInGrid.add(word);
        // Assuming a word "test" is present vertically downward at positions (0,0) to
        // (3,0)
        Assert.assertTrue(grid.checkWord(0, 0, 3, 0, Color.RED));
    }

    public void testCheckWordDiagonalDownward() {
        Grid grid = new Grid();
        grid.createGrid();

        grid.grid[0][0] = new Alphabet();
        grid.grid[1][1] = new Alphabet();
        grid.grid[2][2] = new Alphabet();
        grid.grid[3][3] = new Alphabet();

        grid.grid[0][0].alphabet = 't';
        grid.grid[1][1].alphabet = 'e';
        grid.grid[2][2].alphabet = 's';
        grid.grid[3][3].alphabet = 't';
        Word word = new Word("test", 0, 3, 0, 3, 4);
        grid.wordsInGrid.add(word);
        // Assuming a word "test" is present diagonally downward at positions (0,0) to
        // (3,3)
        Assert.assertTrue(grid.checkWord(0, 0, 3, 3, Color.RED));
    }

    public void testCheckWordDiagonalUpward() {
        Grid grid = new Grid();
        grid.createGrid();
        grid.grid[3][3] = new Alphabet();
        grid.grid[2][2] = new Alphabet();
        grid.grid[1][1] = new Alphabet();
        grid.grid[0][0] = new Alphabet();

        grid.grid[3][3].alphabet = 't';
        grid.grid[2][2].alphabet = 'e';
        grid.grid[1][1].alphabet = 's';
        grid.grid[0][0].alphabet = 't';
        Word word = new Word("test", 3, 0, 3, 0, 4);
        grid.wordsInGrid.add(word);
        // Assuming a word "test" is present diagonally upward at positions (3,3) to
        // (0,0)
        Assert.assertTrue(grid.checkWord(3, 3, 0, 0, Color.RED));
    }

}

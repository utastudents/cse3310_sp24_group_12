package uta.cse3310;

import junit.framework.Assert;


public class GameTest {

    public void testStartGame() {
        Game game = new Game();
        game.StartGame();
        Assert.assertEquals(1, game.getGameState());
    }

    public void testPlayer() {
        Game game = new Game();
        game.StartGame();
        UserEvent userEvent = new UserEvent(); // Assuming player 1 makes a legal move at index 0
        userEvent.PlayerId = PlayerType.player_1;
        userEvent.button = 0;
        userEvent.GameId = 1;
        game.grid.grid[0][userEvent.button] = new Alphabet();
        game.grid.grid[0][userEvent.button].player = userEvent.PlayerId;

        
        game.Update(userEvent);
        Assert.assertEquals(PlayerType.player_2, game.currentTurn);
        Assert.assertEquals("Player 1's move", game.msg[1]);
        Assert.assertEquals("Your move", game.msg[0]);
    }

    public void testUpdateIllegalMove() {
        Game game = new Game();
        game.StartGame();
        // Assuming player 1 tries to make an illegal move at index 0
        UserEvent userEvent = new UserEvent();
        userEvent.PlayerId = PlayerType.player_1;
        userEvent.button = 0;
        userEvent.GameId = 1;
        game.Update(userEvent);
        // Assert that the message reflects that it's not a legal move
        Assert.assertEquals("Not a legal move", game.msg[0]); // Assuming player 1's index is 2
    }

    public void testAddPlayer() {
        Game game = new Game();

        game.loginManager.registerUser("player1");
        Assert.assertEquals(1, game.loginManager.currentGameSize);
        game.loginManager.registerUser("player2");
        Assert.assertEquals(2, game.loginManager.currentGameSize);
        game.loginManager.registerUser("player3");
        Assert.assertEquals(3, game.loginManager.currentGameSize);
        game.loginManager.registerUser("player4");
        Assert.assertEquals(4, game.loginManager.currentGameSize);

        Assert.assertEquals(4, game.loginManager.getRegisteredUsers().size());

    }
    
    public void testPlayerToId() {
        Game game = new Game();
        game.loginManager.registerUser("player1");
        game.loginManager.registerUser("player2");
        game.loginManager.registerUser("player3");
        game.loginManager.registerUser("player4");

        Assert.assertEquals(2, game.playerToId(PlayerType.player_1));
        Assert.assertEquals(3, game.playerToId(PlayerType.player_2));
        Assert.assertEquals(4, game.playerToId(PlayerType.player_3));
        Assert.assertEquals(0, game.playerToId(PlayerType.NoPlayer));
    }

}

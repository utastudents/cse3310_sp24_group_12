package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GameUpdateTest extends TestCase{

    public void testUpdate() {
        Game game = new Game();

        //Create UserEvent as input for Update method
        UserEvent userEvent = new UserEvent();
        userEvent.setPlayerId(PlayerType.Player_1);
        userEvent.setButton(0); 

        //Call Update method with UserEvent
        game.Update(userEvent);

        //Check if game state was updated as expected
        assertEquals(PlayerType.Player_2, game.getCurrentTurn()); 
        assertEquals("Player 1's move", game.getmsg()[1]);
        assertEquals("Your move", game.getmsg()[0]);
    }
}
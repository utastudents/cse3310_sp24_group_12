package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UserEventTest extends TestCase{

    public void testAttributes(){
        //Check if attributes are initialized correctly
        UserEvent userEvent = new UserEvent();

        assertNotNull(userEvent);
        assertNull(userEvent.getYouAre());
        assertEquals(0, userEvent.getGameId());
    }

    public void testSettersAndGetters(){
        //Check if setters and getters work
        UserEvent userEvent = new UserEvent();
        
        userEvent.setYouAre(PlayerType.player_1);
        userEvent.setGameId(88);

        assertEquals(PlayerType.player_1, userEvent.getYouAre());
        assertEquals(88, userEvent.getGameId());
    }
}

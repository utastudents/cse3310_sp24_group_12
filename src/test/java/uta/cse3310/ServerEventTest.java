package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ServerEventTest extends TestCase{

    public void testAttributes(){
        //Check if attributes are initialized correctly
        ServerEvent serverEvent = new ServerEvent();

        assertNotNull(serverEvent);
        assertNull(serverEvent.getYouAre());
        assertEquals(0, serverEvent.getGameId());
    }

    public void testSettersAndGetters(){
        //Check if setters and getters work
        ServerEvent serverEvent = new ServerEvent();
        
        serverEvent.setYouAre(PlayerType.Player_1);
        serverEvent.setGameId(2);

        assertEquals(PlayerType.Player_1, serverEvent.getYouAre());
        assertEquals(2, serverEvent.getGameId());
    }
}
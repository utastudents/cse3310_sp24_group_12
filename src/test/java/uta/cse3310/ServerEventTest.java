package uta.cse3310;

import junit.framework.TestCase;

public class ServerEventTest extends TestCase{

    public void testAttributes(){
        //Check if attributes are initialized correctly
        ServerEvent serverEvent = new ServerEvent();

        assertNotNull(serverEvent);
        assertNull(serverEvent.YouAre);
    }

    public void testSettersAndGetters(){
        //Check if setters and getters work
        ServerEvent serverEvent = new ServerEvent();
        
        serverEvent.YouAre = PlayerType.player_1;

        assertEquals(PlayerType.player_1, serverEvent.YouAre);
    }
}
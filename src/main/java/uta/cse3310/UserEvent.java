package uta.cse3310;
// User events are sent from the webpage to the server

public class UserEvent {
    int GameId; 
    PlayerType PlayerId; 
    int button; 

    UserEvent() {

    }
    //class constructor
    UserEvent(int _GameId, PlayerType _PlayerId, int _button) {
        GameId = _GameId;
        PlayerIdx = _PlayerIdx;
        Button = _Button;
    }
}

package uta.cse3310;

// User events are sent from the webpage to the server

public class UserEvent {
    int GameId;
    PlayerType PlayerId;
    int button;

    public PlayerType getYouAre() {
        return PlayerId;
    }
    
    public int getGameId() {
        return GameId;
    }

    public void setYouAre(PlayerType player1) {
        this.PlayerId = player1;
    }

    public void setGameId(int i) {
        this.GameId = i;
    }
}

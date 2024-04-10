package uta.cse3310;


public class Game {
    // class atributes
    public int requiredGameSize;
    public int currentGameSize;
    private int gameState;
    public int GameId;
    public String[] msg;
    public PlayerType lastestPlayer;
    public PlayerType currentTurn;
    public Color playerColor;
    public PlayerType[][] buttons;

    // class constructor
    Game(int gameSize) {
        this.requiredGameSize = gameSize;
        buttons = new PlayerType[50][50];

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                buttons[i][j] = PlayerType.NoPlayer;
            }
        }

        msg = new String[2];
        lastestPlayer = PlayerType.player_1;
        currentGameSize++;
        currentTurn = PlayerType.NoPlayer;
        msg[0] = "Waiting for other player to join";
        msg[1] = "";
    }

    // StartGame methodw
    public void StartGame() {
        msg[0] = "It is your turn Player  1";
        msg[1] = "It is not your turn at the moment. It is player 1's turn.";
        currentTurn = PlayerType.player_1;
    }

    /**
     * Returns an index for each player based on the enum.
     * 
     * @param player
     * @return
     */
    public int playerToID(PlayerType player) {
        int returnValue = -1;
        switch (player) {
            case NoPlayer:
                returnValue = 0;
                break;
            case LobbyPlayer:
                returnValue = 1;
                break;
            case player_1:
                returnValue = 2;
                break;
            case player_2:
                returnValue = 3;
                break;
            case player_3:
                returnValue = 4;
                break;
            case player_4:
                returnValue = 5;
                break;
        }
        return returnValue;
    }

    // Update method
    public void Update(UserEvent user) {
        // Update players and messages based on tictactoe's Game.Update()
        if((currentTurn == user.PlayerId) &&
           (currentTurn == PlayerType.Player_1 ||
            currentTurn == PlayerType.Player_2 || 
            currentTurn == PlayerType.Player_3 ||
            currentTurn == PlayerType.Player_4))
        {
            if((buttons[0][user.buttons] == user.PlayerID) &&
                buttons[1][user.buttons] == user.PlayerID))
            {
                if(user.PlayerId == PlayerType.Player_1){
                    currentTurn = PlayerType.Player_2;
                    msg[1] = "Player 1's move";
                    msg[0] = "Your move";
                    
                }
                else if(user.PlayerId == PlayerType.Player_2){
                    currentTurn = PlayerType.Player_3;
                    msg[1] = "Player 2's move";
                    msg[0] = "Your move";
                }
                else if(user.PlayerId == PlayerType.Player_3){
                    currentTurn = PlayerType.Player_4;
                    msg[1] = "Player 3's move";
                    msg[0] = "Your move";
                }
                else if(user.PlayerId == PlayerType.Player_4){
                    currentTurn = PlayerType.Player_1;
                    msg[1] = "Player 4's move";
                    msg[0] = "Your move";
                }
            }
            else{
                msg[PlayerToID(user.PlayerID)] = "Not a legal move";
            }

            //Possibly implement way to check if a player has won
            //assuming we want our Game.Update() to work like
            //tictactoe's Game.Update()
        }
    }

    // tick method
    public void tick() {
        // code to be implemented later
    }
}

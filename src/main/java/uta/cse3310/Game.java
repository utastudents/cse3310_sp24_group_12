package uta.cse3310;


public class Game {
    // class atributes
    public int requiredGameSize;
    private int gameState; // 0 = waiting for players, 1 = game in progress
    public int GameId;
    public String[] msg;
    public PlayerType latestPlayer;
    public PlayerType currentTurn;
    public Alphabet[][] buttons;
    public LoginManager loginManager = new LoginManager();

    // class constructor
    Game() {
        buttons = new Alphabet[50][50];

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                buttons[i][j] = new Alphabet(); // Add this line to initialize each element of the buttons array
                buttons[i][j].player = PlayerType.NoPlayer;
                buttons[i][j].alphabet = '?';
            }
        }

        msg = new String[2];
        latestPlayer = PlayerType.player_1;
        currentTurn = PlayerType.NoPlayer;
        msg[0] = "Waiting for other player to join";
        msg[1] = "";
    }

    // StartGame method
    public void StartGame() {
        gameState = 1;
        currentTurn = PlayerType.player_1;
    }

    /**
     * Returns an index for each player based on the enum.
     * 
     * @param player
     * @return
     */
    public int playerToId(PlayerType player) {
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
           (currentTurn == PlayerType.player_1 ||
            currentTurn == PlayerType.player_2 || 
            currentTurn == PlayerType.player_3 ||
            currentTurn == PlayerType.player_4))
        {
            if((buttons[0][user.button].player == user.PlayerId) &&
                (buttons[1][user.button].player == user.PlayerId))
            {
                if(user.PlayerId == PlayerType.player_1){
                    currentTurn = PlayerType.player_2;
                    msg[1] = "Player 1's move";
                    msg[0] = "Your move";
                    
                }
                else if(user.PlayerId == PlayerType.player_2){
                    currentTurn = PlayerType.player_3;
                    msg[1] = "Player 2's move";
                    msg[0] = "Your move";
                }
                else if(user.PlayerId == PlayerType.player_3){
                    currentTurn = PlayerType.player_4;
                    msg[1] = "Player 3's move";
                    msg[0] = "Your move";
                }
                else if(user.PlayerId == PlayerType.player_4){
                    currentTurn = PlayerType.player_1;
                    msg[1] = "Player 4's move";
                    msg[0] = "Your move";
                }
            }
            else{
                msg[playerToId(user.PlayerId)] = "Not a legal move";
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

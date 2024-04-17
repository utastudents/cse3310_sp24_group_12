package uta.cse3310;


public class Game {
    // class atributes
    public int requiredGameSize;
    public int gameState; // 0 = waiting for players, 1 = game in progress
    public int GameId;
    public String[] msg;
    public PlayerType latestPlayer;
    public PlayerType currentTurn;
    public LoginManager loginManager = new LoginManager();
    public Scores scores = new Scores();
    public Grid grid;
    public Chat chatLog = new Chat();

    // class constructor
    Game() {
        msg = new String[2];
        latestPlayer = PlayerType.player_1;
        currentTurn = PlayerType.NoPlayer;
        msg[0] = "Waiting for other player to join";
        msg[1] = "";
    }

    // StartGame method
    public void StartGame() {
        grid = new Grid();
        grid.createGrid();
        gameState = 1;
        currentTurn = PlayerType.player_1;
    }
    public int getGameState(){
        return gameState;
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
            if((grid.grid[0][user.button].player == user.PlayerId) ||
                (grid.grid[1][user.button].player == user.PlayerId))
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
                msg[0] = "Not a legal move";
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

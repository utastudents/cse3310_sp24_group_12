package uta.cse3310;

import java.awt.Point;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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
    public Leaderboard leaderboard = new Leaderboard(scores.score);

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

    public int getGameState() {
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
        if ((currentTurn == user.PlayerId) &&
                (currentTurn == PlayerType.player_1 ||
                        currentTurn == PlayerType.player_2 ||
                        currentTurn == PlayerType.player_3 ||
                        currentTurn == PlayerType.player_4)) {
            if ((grid.grid[0][user.button].player == user.PlayerId) ||
                    (grid.grid[1][user.button].player == user.PlayerId)) {
                if (user.PlayerId == PlayerType.player_1) {
                    currentTurn = PlayerType.player_2;
                    msg[1] = "Player 1's move";
                    msg[0] = "Your move";

                } else if (user.PlayerId == PlayerType.player_2) {
                    currentTurn = PlayerType.player_3;
                    msg[1] = "Player 2's move";
                    msg[0] = "Your move";
                } else if (user.PlayerId == PlayerType.player_3) {
                    currentTurn = PlayerType.player_4;
                    msg[1] = "Player 3's move";
                    msg[0] = "Your move";
                } else if (user.PlayerId == PlayerType.player_4) {
                    currentTurn = PlayerType.player_1;
                    msg[1] = "Player 4's move";
                    msg[0] = "Your move";
                }
            } else {
                msg[0] = "Not a legal move";
            }

            // Possibly implement way to check if a player has won
            // assuming we want our Game.Update() to work like
            // tictactoe's Game.Update()
        }
    }

    public void Update(String message) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        if (message.contains("startGame")) {
            if (gameState == 1) {
                return;
            }
            StartGame();
            return;
        }
        if (message.contains("startCountdown")) {
            return;
        }
        if (message.contains("register")) {
            JsonObject json = gson.fromJson(message, JsonObject.class);
            JsonObject registree = json.get("register").getAsJsonObject();
            String username = registree.get("username").getAsString();
            int connectionGameID = json.get("gameid").getAsInt();
            if (GameId != connectionGameID) {
                return;
            }

            if (username.length() < 3) {
                JsonObject error = new JsonObject();
                error.addProperty("error", "Username must be at least 3 characters long");
                return;
            }
            if (!loginManager.registerUser(username)) {
                JsonObject error = new JsonObject();
                error.addProperty("error", "Username already taken");
                return;
            }

            scores.addNewPlayer(username);
            String jsonString;
            jsonString = gson.toJson(this);

        } else if (message.contains("startClick")) {
            JsonObject json = gson.fromJson(message, JsonObject.class);
            int connectionGameID = json.get("gameid").getAsInt();

            if (GameId != connectionGameID) {
                return;
            }

            JsonObject startClick = json.get("startClick").getAsJsonObject();
            JsonObject endClick = json.get("endClick").getAsJsonObject();

            String name = json.get("username").getAsString();
            int startx = startClick.get("x").getAsInt();
            int starty = startClick.get("y").getAsInt();
            int endx = endClick.get("x").getAsInt();
            int endy = endClick.get("y").getAsInt();

            if (grid.checkWord(startx, starty, endx, endy, loginManager.usernames.get(name))) {
                scores.updateScore(name, 1);
            } else {
                grid.resetColor(endx, endy);
                grid.resetColor(startx, starty);
            }

        } else if (message.contains("click")) {
            JsonObject json = gson.fromJson(message, JsonObject.class);
            int connectionGameID = json.get("gameid").getAsInt();

            if (GameId != connectionGameID) {
                return;
            }

            JsonObject click = json.get("click").getAsJsonObject();
            String name = click.get("username").getAsString();
            int x = click.get("x").getAsInt();
            int y = click.get("y").getAsInt();
            String color = click.get("color").getAsString();
            if (grid.grid[x][y].color.equals("white")) {
                grid.colorIn(x, y, Color.valueOf(color));
            }


        } else if (message.contains("incoming")) {
            JsonObject json = gson.fromJson(message, JsonObject.class);
            JsonObject incoming = json.get("incoming").getAsJsonObject();

            String username = incoming.get("from").getAsString();
            String messageDetails = json.get("details").getAsString();
            chatLog.addToChat(username, messageDetails);
        }

    }

    // tick method
    public void tick() {
        // code to be implemented later
    }
}

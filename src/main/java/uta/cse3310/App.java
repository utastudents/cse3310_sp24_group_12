
// This is example code provided to CSE3310 Fall 2022
// You are free to use as is, or changed, any of the code provided

// Please comply with the licensing requirements for the
// open source packages being used.

// This code is based upon, and derived from the this repository
//            https:/thub.com/TooTallNate/Java-WebSocket/tree/master/src/main/example

// http server include is a GPL licensed package from
//            http://www.freeutils.net/source/jlhttp/

/*
 * Copyright (c) 2010-2020 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

package uta.cse3310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.time.Instant;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class App extends WebSocketServer {

  // All games currently underway on this server are stored in
  // the vector ActiveGames
  private Vector<Game> ActiveGames = new Vector<Game>();

  private int GameId = 1;

  private int connectionId = 0;

  private Instant startTime;

  public App(int port) {
    super(new InetSocketAddress(port));
  }

  public App(InetSocketAddress address) {
    super(address);
  }

  public App(int port, Draft_6455 draft) {
    super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    String version = System.getenv("VERSION");
    JsonObject versionObj = new JsonObject();
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    versionObj.addProperty("version", version);
    conn.send(versionObj.toString()); 
    connectionId++;

    System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");

    ServerEvent E = new ServerEvent();

    // search for a game needing a player
    Game G = null;
    for (Game i : ActiveGames) {

      System.out.println(i.latestPlayer);
      if (((i.latestPlayer == uta.cse3310.PlayerType.player_1) ||
          (i.latestPlayer == uta.cse3310.PlayerType.player_2) ||
          (i.latestPlayer == uta.cse3310.PlayerType.player_3) ||
          (i.latestPlayer == uta.cse3310.PlayerType.player_4)) &&
          ((i.loginManager.currentGameSize < 4) &&
          (i.gameState != 1))) {
        G = i;
        System.out.println("found a match");
      }
    }

    // No matches ? Create a new Game.
    if (G == null) {
      G = new Game();
      G.GameId = GameId;
      GameId++;
      // Add the first player
      G.latestPlayer = PlayerType.player_1;
      ActiveGames.add(G);
      System.out.println("Creating a new Game");
    } else {
      // join an existing game
      System.out.println(" not a new game");
      G.latestPlayer = PlayerType.values()[G.latestPlayer.ordinal() + 1];
    }

    // create an event to go to only the new player
    E.YouAre = G.latestPlayer;
    E.GameId = G.GameId;

    // allows the websocket to give us the Game when a message arrives..
    // it stores a pointer to G, and will give that pointer back to us
    // when we ask for it
    conn.setAttachment(G);


    // Note only send to the single connection
    String jsonString = gson.toJson(E);
    conn.send(jsonString);
    System.out
        .println("> " + Duration.between(startTime, Instant.now()).toMillis() + " " + connectionId + " "
            + escape(jsonString));

    // The state of the game has changed, so lets send it to everyone
    jsonString = gson.toJson(G);
    System.out
        .println("< " + Duration.between(startTime, Instant.now()).toMillis() + " " + "*" + " " + escape(jsonString));
    broadcast(jsonString);

  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    System.out.println(conn + " has closed");
    // Retrieve the game tied to the websocket connection
    Game G = conn.getAttachment();
    G = null;
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    System.out.println(message);
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    
    Game G = conn.getAttachment();
    if(G == null){
      return;
    }
    if (message.contains("startGame")) {
      if (G.gameState == 1) {
        return;
      }
      G.StartGame();
      broadcast(gson.toJson(G));
      return;
    }
    if(message.contains("startCountdown")){
      broadcast("countdown");
      return;
    }
    if (message.contains("register")) {
      JsonObject json = gson.fromJson(message, JsonObject.class);
      JsonObject registree = json.get("register").getAsJsonObject();

      String username = registree.get("username").getAsString();
      int connectionGameID = json.get("gameid").getAsInt();

      if (G.GameId != connectionGameID) {
        return;
      }

      if(username.length() < 3){
        JsonObject error = new JsonObject();
        error.addProperty("error", "Username must be at least 3 characters long");
        conn.send(gson.toJson(error));
        return;
      }
      if (!G.loginManager.registerUser(username)) {
        JsonObject error = new JsonObject();
        error.addProperty("error", "Username already taken");
        conn.send(gson.toJson(error));
        return;
      }

      G.scores.addNewPlayer(username);
      String jsonString;
      jsonString = gson.toJson(G);
      broadcast(jsonString);

    } else if (message.contains("startClick")) {
      JsonObject json = gson.fromJson(message, JsonObject.class);
      int connectionGameID = json.get("gameid").getAsInt();

      if (G.GameId != connectionGameID) {
        return;
      }

      JsonObject startClick = json.get("startClick").getAsJsonObject();
      JsonObject endClick = json.get("endClick").getAsJsonObject();

      String name = json.get("username").getAsString();
      int startx = startClick.get("x").getAsInt();
      int starty = startClick.get("y").getAsInt();
      int endx = endClick.get("x").getAsInt();
      int endy = endClick.get("y").getAsInt();

      if (G.grid.checkWord(startx, starty, endx, endy, G.loginManager.usernames.get(name))) {
        G.scores.updateScore(name, 1);
        broadcast(gson.toJson(G));
      } else {
        G.grid.resetColor(endx, endy);
        G.grid.resetColor(startx, starty);
        broadcast(gson.toJson(G));// to reset color on grid
      } 
      
    } else if (message.contains("click")) {
      JsonObject json = gson.fromJson(message, JsonObject.class);
      int connectionGameID = json.get("gameid").getAsInt();

      if (G.GameId != connectionGameID) {
        return;
      }

      JsonObject click = json.get("click").getAsJsonObject();
      String name = click.get("username").getAsString();
      int x = click.get("x").getAsInt();
      int y = click.get("y").getAsInt();
      String color = click.get("color").getAsString();

      if (G.grid.grid[x][y].color.equals("white")) {
        G.grid.colorIn(x, y, Color.valueOf(color));
      }

      broadcast(gson.toJson(G));
    }else if(message.contains("incoming")){
      JsonObject json = gson.fromJson(message, JsonObject.class);
      JsonObject incoming = json.get("incoming").getAsJsonObject();

      String username = incoming.get("from").getAsString();
      String messageDetails = json.get("details").getAsString();
      G.chatLog.addToChat(username,messageDetails);
      broadcast(gson.toJson(G));
    }
    
    else {
      // System.out
      // .println("< " + Duration.between(startTime, Instant.now()).toMillis() + " " +
      // "-" + " " + escape(message));

      // Bring in the data from the webpage
      // A UserEvent is all that is allowed at this point

      UserEvent U = gson.fromJson(message, UserEvent.class);

      // Get our Game Object
      G.Update(U);

      // send out the game state every time
      // to everyone
      String jsonString;
      jsonString = gson.toJson(G);

      // System.out
      // .println("> " + Duration.between(startTime, Instant.now()).toMillis() + " " +
      // "*" + " " + escape(jsonString));
      broadcast(jsonString);
    }
  }

  @Override
  public void onMessage(WebSocket conn, ByteBuffer message) {
    System.out.println(conn + ": " + message);
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
    if (conn != null) {
      // some errors like port binding failed may not be assignable to a specific
      // websocket
    }
  }

  @Override
  public void onStart() {
    setConnectionLostTimeout(0);
    startTime = Instant.now();
  }

  private String escape(String S) {
    // turns " into \"
    String retval = new String();
    // this routine is very slow.
    // but it is not called very often
    for (int i = 0; i < S.length(); i++) {
      Character ch = S.charAt(i);
      if (ch == '\"') {
        retval = retval + '\\';
      }
      retval = retval + ch;
    }
    return retval;
  }

  public static void main(String[] args) {
    int groupNumber =12;

    // Set up the http server
    int port = 9000 +groupNumber;
    HttpServer H = new HttpServer(port, "./html");
    H.start();
    System.out.println("http Server started on port: " + port);

    // create and start the websocket server

    port = 9100 + groupNumber;
    App A = new App(port);
    A.setReuseAddr(true);
    A.start();
    System.out.println("websocket Server started on port: " + port);


  }
}

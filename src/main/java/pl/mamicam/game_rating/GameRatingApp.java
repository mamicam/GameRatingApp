package pl.mamicam.game_rating;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;

public class GameRatingApp extends NanoHTTPD {
    public GameRatingApp(int port) throws IOException {
        super(port);
        start(5000, false);
        System.out.println("Server has been started.");
    }

    public static void main(String[] args) {
        try {
            new GameRatingApp(7777);
        } catch (IOException e) {
            System.err.println("Server can't be started because of error: \n" + e);
        }
    }

}

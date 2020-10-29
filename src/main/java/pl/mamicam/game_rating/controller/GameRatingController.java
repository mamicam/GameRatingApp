package pl.mamicam.game_rating.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.iki.elonen.NanoHTTPD;
import pl.mamicam.game_rating.game.Game;
import pl.mamicam.game_rating.storage.InfoGameStorage;
import pl.mamicam.game_rating.storage.impl.DbGameStorageImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.Response.Status.*;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

public class GameRatingController {
    private final static String GAME_ID_PARAM_NAME = "gameId";
    private final static String RATING_ID_PARAM_NAME = "requestId";

    private InfoGameStorage infoGameStorage = new DbGameStorageImpl();

    public NanoHTTPD.Response serveGetGameRequest(NanoHTTPD.IHTTPSession session) {
        Map<String, String> requestParameters = session.getParms();
        if (requestParameters.containsKey(GAME_ID_PARAM_NAME)) {
            List<String> gameIdParams = Collections.singletonList(requestParameters.get(GAME_ID_PARAM_NAME));
            String gameIdParam = gameIdParams.get(0);
            long gameId;

            try {
                gameId = Long.parseLong(gameIdParam);
            } catch (NumberFormatException e) {
                System.err.println("Error during convert request param: \n" + e);
                return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Request param 'gameId' have to be a number");
            }

            Game game = infoGameStorage.getGame(gameId);
            if (game != null) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String response = objectMapper.writeValueAsString(game);
                    return newFixedLengthResponse(OK, "application/json", response);
                } catch (JsonProcessingException e) {
                    System.err.println("Error during process request: \n" + e);
                    return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error can't read game");
                }
            }

            return newFixedLengthResponse(NOT_FOUND, "application/json", "");
        }

        return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Uncorrected request params");
    }

    public NanoHTTPD.Response serveGetAllGamesRequest(NanoHTTPD.IHTTPSession session) {
        ObjectMapper objectMapper = new ObjectMapper();
        String response;

        try {
            response = objectMapper.writeValueAsString(infoGameStorage.getAllGame());
        } catch (JsonProcessingException e) {
            System.err.println("Error during process request: \n" + e.getMessage());
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error can't read all games.");
        }
        return newFixedLengthResponse(OK, "application/json", response);
    }

    public NanoHTTPD.Response serveAddGameRequest(NanoHTTPD.IHTTPSession session) {
        ObjectMapper objectMapper = new ObjectMapper();
        long randomGameId = System.currentTimeMillis();

        String lengthHeader = session.getHeaders().get("content-length");
        int contentLength = Integer.parseInt(lengthHeader);
        byte[] buffer = new byte[contentLength];

        try {
            session.getInputStream().read(buffer, 0, contentLength);
            String requestBody = new String(buffer).trim();
            Game requestGame = objectMapper.readValue(requestBody, Game.class);
            requestGame.setGameId(randomGameId);

            infoGameStorage.addGame(requestGame);
        } catch (Exception e) {
            System.err.println("Error during process request: " + e);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error game hasn't be added.");
        }
        return newFixedLengthResponse(OK, "text/plain", "Game has been successfully added, id = " + randomGameId);
    }

    public NanoHTTPD.Response serveAddRatingReviewRequest(NanoHTTPD.IHTTPSession session) {
        ObjectMapper objectMapper = new ObjectMapper();
        long randomRatingId = System.currentTimeMillis();

        String lengthHeader = session.getHeaders().get("content-length");
        int contentLength = Integer.parseInt(lengthHeader);
        byte[] buffer = new byte[contentLength];

        try {
            session.getInputStream().read(buffer, 0, contentLength);
            String requestBody = new String(buffer).trim();
            Game requestGame = objectMapper.readValue(requestBody, Game.class);
            requestGame.setRatingId(randomRatingId);

            infoGameStorage.addRatingAndReview(requestGame);
        } catch (Exception e) {
            System.err.println("Error during process request: \n" + e);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error rating or review or nick " +
                    "hasn't be added");
        }
        return newFixedLengthResponse(OK, "text/plain", "Your information about game has been successfully added");
    }
}
package pl.mamicam.game_rating;

import fi.iki.elonen.NanoHTTPD;
import pl.mamicam.game_rating.controller.GameRatingController;

import static fi.iki.elonen.NanoHTTPD.Method.*;
import static fi.iki.elonen.NanoHTTPD.Response.Status.NOT_FOUND;

public class RequestUrlMapper {
    private final static String ADD_GAME_URL = "/game/add";
    private final static String ADD_RATING_REVIEW = "/game/addRatingReview";
    private final static String GET_GAME_URL = "/game/getGame";
    private final static String GET_ALL_GAME_URL = "/game/getAll";

    private GameRatingController gameRatingController = new GameRatingController();

    public NanoHTTPD.Response delegateRequest(NanoHTTPD.IHTTPSession session) {
        if (GET.equals(session.getMethod()) && GET_GAME_URL.equals(session.getUri())) {
            return gameRatingController.serveGetGameRequest(session);
        }
        else if (GET.equals(session.getMethod()) && GET_ALL_GAME_URL.equals(session.getUri())) {
            return gameRatingController.serveGetAllGamesRequest(session);
        }
        else if (POST.equals(session.getMethod()) && ADD_GAME_URL.equals(session.getUri())) {
            return gameRatingController.serveAddGameRequest(session);
        }
        else if (POST.equals(session.getMethod()) && ADD_RATING_REVIEW.equals(session.getUri())) {
            return gameRatingController.serveAddRatingReviewRequest(session );
        }

        return NanoHTTPD.newFixedLengthResponse(NOT_FOUND, "text/plain", "Not Found");
    }
}
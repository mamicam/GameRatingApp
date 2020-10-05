package pl.mamicam.game_rating.storage;

import pl.mamicam.game_rating.game.Game;

import java.util.List;

public interface InfoGameStorage {
    Game getGame(long gameId);

    List<Game> getAllGame();

    void addGame(Game game);

    void addRatingAndReview(Game game);
}

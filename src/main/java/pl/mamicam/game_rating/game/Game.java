package pl.mamicam.game_rating.game;

public class Game {
    private long gameId;
    private long ratingId;
    private String title;
    private String producer;
    private long premiereDate;
    private String availablePlatforms;
    private int rating;
    private String review;
    private String nick;
    private double avg_game_rating;

    public long getGameId() {
        return gameId;
    }

    public long getRatingId() {
        return ratingId;
    }

    public void setRatingId(long ratingId) {
        this.ratingId = ratingId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public long getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(long premiereDate) {
        this.premiereDate = premiereDate;
    }

    public String getAvailablePlatforms() {
        return availablePlatforms;
    }

    public void setAvailablePlatforms(String availablePlatforms) {
        this.availablePlatforms = availablePlatforms;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setAvg_game_rating(double avg_game_rating) {
        this.avg_game_rating = avg_game_rating;
    }
}
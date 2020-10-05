package pl.mamicam.game_rating.game;

import java.util.Date;

public class Game {
    private long gameId;
    private String title;
    private String producer;
    private java.sql.Date premiereDate;
    private String availablePlatforms;
    private int rating;
    private String review;
    private String nick;

    public long getGameId() {
        return gameId;
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

    public Date getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(java.sql.Date premiereDate) {
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
}

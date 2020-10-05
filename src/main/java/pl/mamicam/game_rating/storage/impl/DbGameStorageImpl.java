package pl.mamicam.game_rating.storage.impl;

import pl.mamicam.game_rating.game.Game;
import pl.mamicam.game_rating.storage.InfoGameStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbGameStorageImpl implements InfoGameStorage {
    private static String JDBC_URL = "jdbc:postgresql://localhost5432/game_rating_db";
    private static String USER_NAME = "postgres";
    private static String USER_PASS = "postgres";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Server can't find postgresql Driver class: \n" + e.getMessage());
        }
    }

    private Connection initializeDataBaseConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, USER_NAME, USER_PASS);
        } catch (SQLException e) {
            System.err.println("Server can't initialize database connection: \n" + e.getMessage());
            throw new RuntimeException("Server can't initialize database connection!");
        }
    }

    @Override
    public Game getGame(long gameId) {
        final String sqlSelectGame = "SELECT * FROM games WHERE game_id = ?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlSelectGame);
            preparedStatement.setLong(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Game game = new Game();
                game.setGameId(resultSet.getLong("game_id"));
                game.setTitle(resultSet.getString("game_title"));
                game.setProducer(resultSet.getString("game_producer"));
                game.setPremiereDate(resultSet.getDate("premiere_date"));
                game.setAvailablePlatforms(resultSet.getString("available_platforms"));
                game.setRating(resultSet.getInt("game_rating"));
                game.setReview(resultSet.getString("game_review"));
                game.setNick(resultSet.getString("nick"));

                return game;
            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        } finally {
            closeDataBaseResources(connection, preparedStatement);
        }
        return null;
    }

    @Override
    public List<Game> getAllGame() {
        final String sqlSelectAllGames = "SELECT * FROM games;";

        Connection connection = initializeDataBaseConnection();
        Statement statement = null;

        List<Game> games = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectAllGames);

            while (resultSet.next()) {
                Game game = new Game();
                game.setGameId(resultSet.getLong("game_id"));
                game.setTitle(resultSet.getString("game_title"));
                game.setProducer(resultSet.getString("game_producer"));
                game.setPremiereDate(resultSet.getDate("premiere_date"));
                game.setAvailablePlatforms(resultSet.getString("available_platforms"));
                game.setRating(resultSet.getInt("game_rating"));
                game.setReview(resultSet.getString("game_review"));
                game.setNick(resultSet.getString("nick"));

                games.add(game);
            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        } finally {
            closeDataBaseResources(connection, statement);
        }
        return games;
    }

    @Override
    public void addGame(Game game) {
        final String sqlInsertGame = "INSERT INTO games " +
                "(game_id, game_title, game_producer, premiere_date, available_platforms, game_rating, game_review) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        final String deleteGame = "DELETE FROM games WHERE game_id = ?";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlInsertGame);

            preparedStatement.setLong(1, game.getGameId());
            preparedStatement.setString(2, game.getTitle());
            preparedStatement.setString(3, game.getProducer());
            preparedStatement.setDate(4, (Date) game.getPremiereDate());
            preparedStatement.setString(5, game.getAvailablePlatforms());
            if (game.getRating() >= 0 && game.getRating() <= 10) {
                preparedStatement.setInt(6, game.getRating());
            } else {
                System.out.println("Min. rating is 0 and max. rating is 10!");
                preparedStatement.execute(deleteGame);
            }
            preparedStatement.setString(7, game.getReview());
            preparedStatement.setString(8, game.getNick());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query!");
        } finally {
            closeDataBaseResources(connection, preparedStatement);
        }
    }

    @Override
    public void addRatingAndReview(Game game) {
        final String sqlInsRatingAndReview = "INSERT INTO games (game_rating, game_review, nick) VALUES (?,?) WHERE game_id = ?;";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlInsRatingAndReview);

            if (game.getRating() >= 0 && game.getRating() <= 10) {
                preparedStatement.setInt(6, game.getRating());
            } else {
                System.out.println("Min. rating is 0 and max. rating is 10!");
            }
            preparedStatement.setString(7, game.getReview());
            preparedStatement.setString(8, game.getNick());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke query!");
        } finally {
            closeDataBaseResources(connection, preparedStatement);
        }

    }

    private void closeDataBaseResources(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error during closing database resources: \n" + e.getMessage());
            throw new RuntimeException("Error during closing database resources!");
        }
    }
}

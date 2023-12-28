package org.example.repository;

import org.example.model.Game;
import org.example.repository.dao.GameRepository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GameRepositoryImpl implements GameRepository {
    private static final String SELECT_ALL_GAMES =
            """
                            SELECT * FROM public.games                   
                    """;
    private final Connection connection;

    public GameRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Map<Integer, Game> getAll() {
        Map<Integer, Game> games = new HashMap<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ALL_GAMES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                games.put(resultSet.getInt("id"), gameMapper(resultSet));
            }
            return games;
        } catch (SQLException e) {
            try {
                connection.close();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);

        }
    }

    private Game gameMapper(ResultSet resultSet) throws SQLException {
        return Game.builder()
                .name(resultSet.getString("name"))
                .rating(resultSet.getInt("rating"))
                .releaseDate(resultSet.getDate("release_date"))
                .cost(resultSet.getInt("cost"))
                .description(resultSet.getString("description"))
                .id(resultSet.getInt("id"))
                .build();
    }
}

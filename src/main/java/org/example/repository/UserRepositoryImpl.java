package org.example.repository;

import org.example.model.User;
import org.example.repository.dao.UserRepository;

import java.sql.*;

public class UserRepositoryImpl implements UserRepository {
    private static final String INSERT_USER =
            """
                            INSERT INTO public.users(
                            name, nickname, birthday, password, amount)
                            VALUES (?,?,?,?,?)
                    """;
    private static final String SELECT_USER =
            """
                    SELECT * FROM public.users
                    WHERE nickname =?
                    AND password =?
                    """;
    private static final String UPDATE_USER_AMOUNT =
            """
                    UPDATE public.users
                    SET amount =?
                    WHERE id =?                    
                    """;

    private static final String SELECT_USER_NICK =
            """
                    SELECT nickname FROM public.users
                    WHERE nickname =?
                    """;

    private static final String INSERT_USER_GAME =
            """
                            INSERT INTO public.users_games(
                            user_id, game_id)
                            VALUES (?,?)
                    """;
    private final Connection connection;

    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User save(User user) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getNickname());
            preparedStatement.setDate(3, new java.sql.Date(user.getBirthday().getTime()));
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, (int) user.getAmount());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            user.setId(generatedKeys.getInt(1));
            return user;
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public User get(String nickname, String password) {
        try (PreparedStatement prStatement = this.connection.prepareStatement(SELECT_USER)) {
            prStatement.setString(1, nickname);
            prStatement.setString(2, password);
            ResultSet resultSet = prStatement.executeQuery();

            if (resultSet.next()) {
                return userMapper(resultSet);
            }

        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

        return User.builder().build();
    }

    public User userMapper(ResultSet resultSet) throws SQLException {
        return User.builder()
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday"))
                .nickname(resultSet.getString("nickname"))
                .password(resultSet.getString("password"))
                .amount(resultSet.getInt("amount"))
                .id(resultSet.getInt("id"))
                .build();
    }

    @Override
    public boolean nicknameIsPresentInDB(String nickname) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_USER_NICK)) {
            preparedStatement.setString(1, nickname);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updateAmount(int id, int sum) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_USER_AMOUNT)) {
            preparedStatement.setInt(1, sum);
            preparedStatement.setInt(2, id);
            int rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addGameToAccount(int userId, int gameId) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_USER_GAME)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, gameId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Congratulations, a game is in your collection!");
            }

        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}


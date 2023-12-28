package org.example.repository.dao;

import org.example.model.User;

public interface UserRepository {

    User save(User user);

    User get(String nickname, String password);

    boolean nicknameIsPresentInDB(String nickname);

    void updateAmount(int id, int sum);

    void addGameToAccount(int userId, int gameId);

}

package org.example.service;

import org.example.model.User;
import org.example.repository.dao.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(String nickname, String password) {
        return this.userRepository.get(nickname, password);
    }

    public void updateAccount(int id, int sum) {
        this.userRepository.updateAmount(id, sum);
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public boolean nicknameIsPresent(String nickname) {
        return this.userRepository.nicknameIsPresentInDB(nickname);
    }

    public void addGameToUserAccount(int userId, int gameId) {
        userRepository.addGameToAccount(userId, gameId);
    }
}

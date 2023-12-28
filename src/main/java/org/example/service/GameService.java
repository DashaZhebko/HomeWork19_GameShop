package org.example.service;

import org.example.model.Game;
import org.example.repository.dao.GameRepository;

import java.util.Map;

public class GameService {
    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Map<Integer, Game> getAllGames() {
        return this.gameRepository.getAll();

    }
}

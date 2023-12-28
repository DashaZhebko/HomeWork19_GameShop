package org.example.repository.dao;

import org.example.model.Game;

import java.util.Map;

public interface GameRepository {
    Map<Integer, Game> getAll();
}

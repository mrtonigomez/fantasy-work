package com.example.scrapping.service;

import com.example.scrapping.models.Game;
import com.example.scrapping.models.Player;
import com.example.scrapping.models.PlayerStats;
import com.example.scrapping.repository.GameRepository;
import com.example.scrapping.repository.PlayerStatsRepository;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    protected final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public Game insertGame(Game game) {
        return repository.save(game);
    }
}

package com.example.scrapping.service;

import com.example.scrapping.models.Game;
import com.example.scrapping.models.Player;
import com.example.scrapping.models.PlayerStats;
import com.example.scrapping.models.Team;
import com.example.scrapping.repository.GameRepository;
import com.example.scrapping.repository.PlayerStatsRepository;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;

@Service
public class GameService {

    protected final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public Game insertGame(Game game) {
        return repository.save(game);
    }

    public Game getGameByDate(Timestamp date){
        return repository.findByDate(date);
    }

    public Game getGameByVisitant(Timestamp date){
        return repository.findByDate(date);
    }

    /*public Game findByVisitant_team(Team team) {
        return repository.findByVisitant_team(team);
    }*/
}
